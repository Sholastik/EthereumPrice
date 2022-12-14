package com.vyacheslavivanov.ethereumprice.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vyacheslavivanov.ethereumprice.BuildConfig
import com.vyacheslavivanov.ethereumprice.api.service.price.HistoricalPriceService
import com.vyacheslavivanov.ethereumprice.api.service.price.LivePriceService
import com.vyacheslavivanov.ethereumprice.api.source.price.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
abstract class PriceApiModule {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    internal annotation class PriceApi

    @PriceApi
    @Binds
    @Reusable
    abstract fun bindLivePriceSource(
        livePriceSourceImpl: LivePriceSourceImpl
    ): LivePriceSource

    @PriceApi
    @Binds
    @Reusable
    abstract fun bindHistoricalPriceRemoteSource(
        historicalPriceRemoteSourceImpl: HistoricalPriceRemoteSourceImpl
    ): HistoricalPriceRemoteSource

    @PriceApi
    @Binds
    @Reusable
    abstract fun bindHistoricalPriceLocalSource(
        historicalPriceLocalSourceImpl: HistoricalPriceLocalSourceImpl
    ): HistoricalPriceLocalSource

    companion object {
        @PriceApi
        @Provides
        @Reusable
        fun provideOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                .apply {
                    if (BuildConfig.DEBUG) {
                        addInterceptor(
                            HttpLoggingInterceptor()
                                .apply { level = HttpLoggingInterceptor.Level.BODY }
                        )
                    }
                }.build()

        @PriceApi
        @Provides
        @Reusable
        fun provideRetrofit(
            @PriceApi okHttpClient: OkHttpClient
        ): Retrofit =
            Retrofit.Builder()
                .baseUrl("https://localhost/") // Empty baseUrl
                .client(okHttpClient)
                .addConverterFactory(
                    MoshiConverterFactory.create(
                        Moshi.Builder()
                            .add(KotlinJsonAdapterFactory())
                            .build()
                    )
                ).build()

        @PriceApi
        @Provides
        @Reusable
        fun provideLivePriceService(
            @PriceApi retrofit: Retrofit
        ): LivePriceService =
            retrofit.create()

        @PriceApi
        @Provides
        @Reusable
        fun provideHistoricalPriceService(
            @PriceApi retrofit: Retrofit
        ): HistoricalPriceService =
            retrofit.create()

        @PriceApi
        @Provides
        @Reusable
        fun provideDataStore(
            @ApplicationContext context: Context
        ): DataStore<Preferences> {
            return context.dataStore
        }

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "historical.price")
    }
}

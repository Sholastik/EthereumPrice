package com.vyacheslavivanov.ethereumprice.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vyacheslavivanov.ethereumprice.BuildConfig
import com.vyacheslavivanov.ethereumprice.api.service.price.HistoricalPriceService
import com.vyacheslavivanov.ethereumprice.api.service.price.LivePriceService
import com.vyacheslavivanov.ethereumprice.api.source.price.HistoricalPriceSource
import com.vyacheslavivanov.ethereumprice.api.source.price.HistoricalPriceSourceImpl
import com.vyacheslavivanov.ethereumprice.api.source.price.LivePriceSource
import com.vyacheslavivanov.ethereumprice.api.source.price.LivePriceSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
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
    abstract fun bindHistoricalPriceSource(
        historicalPriceSourceImpl: HistoricalPriceSourceImpl
    ): HistoricalPriceSource

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
    }
}

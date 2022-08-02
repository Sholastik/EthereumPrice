package com.vyacheslavivanov.ethereumprice.api.source.price

import android.content.res.Resources
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.vyacheslavivanov.ethereumprice.data.price.Price
import com.vyacheslavivanov.ethereumprice.di.PriceApiModule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class HistoricalPriceLocalSourceImpl @Inject constructor(
    @PriceApiModule.PriceApi private val dataStore: DataStore<Preferences>
) : HistoricalPriceLocalSource() {
    override fun getHistoricalPriceFlow(): Flow<Result<Price.Historical>> = dataStore.data.map {
        val price = it[priceKey]
        val date = it[dateKey]

        if (price != null && date != null) {
            return@map Result.success(
                Price.Historical(
                    price = price,
                    date = Date(date)
                )
            )
        } else {
            return@map Result.failure(
                Resources.NotFoundException()
            )
        }
    }

    override suspend fun saveHistoricalPrice(price: Price.Historical): Result<Unit> {
        dataStore.edit {
            it[priceKey] = price.price
            it[dateKey] = price.date.time
        }

        return Result.success(Unit)
    }

    override suspend fun clearHistoricalPrice(): Result<Unit> {
        dataStore.edit {
            it.remove(priceKey)
            it.remove(dateKey)
        }

        return Result.success(Unit)
    }

    companion object {
        private val dateKey = longPreferencesKey("historicalPrice.date")
        private val priceKey = doublePreferencesKey("historicalPrice.price")
    }
}

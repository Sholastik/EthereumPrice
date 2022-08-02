package com.vyacheslavivanov.ethereumprice.api.source.price

import com.vyacheslavivanov.ethereumprice.data.price.Price
import kotlinx.coroutines.flow.Flow

abstract class HistoricalPriceLocalSource {
    abstract fun getHistoricalPriceFlow(): Flow<Result<Price.Historical>>
    abstract suspend fun saveHistoricalPrice(price: Price.Historical): Result<Unit>
    abstract suspend fun clearHistoricalPrice(): Result<Unit>
}

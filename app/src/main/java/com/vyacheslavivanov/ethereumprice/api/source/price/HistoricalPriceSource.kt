package com.vyacheslavivanov.ethereumprice.api.source.price

import com.vyacheslavivanov.ethereumprice.data.price.Price
import java.util.*

abstract class HistoricalPriceSource {
    abstract suspend fun fetchHistoricalPrice(date: Date): Result<Price.Historical>
}

package com.vyacheslavivanov.ethereumprice.api.source.price

import com.vyacheslavivanov.ethereumprice.data.price.Price
import kotlinx.coroutines.flow.Flow
import java.util.*

abstract class HistoricalPriceRemoteSource {
    abstract fun getHistoricalPriceFlow(date: Date): Flow<Result<Price.Historical>>
}

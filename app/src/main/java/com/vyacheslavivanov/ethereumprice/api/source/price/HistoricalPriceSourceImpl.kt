package com.vyacheslavivanov.ethereumprice.api.source.price

import com.vyacheslavivanov.ethereumprice.api.mappers.toDomain
import com.vyacheslavivanov.ethereumprice.api.service.price.HistoricalPriceService
import com.vyacheslavivanov.ethereumprice.api.util.fold
import com.vyacheslavivanov.ethereumprice.api.util.log
import com.vyacheslavivanov.ethereumprice.data.price.Price
import com.vyacheslavivanov.ethereumprice.di.PriceApiModule
import java.util.*
import javax.inject.Inject

class HistoricalPriceSourceImpl @Inject constructor(
    @PriceApiModule.PriceApi private val historicalPriceService: HistoricalPriceService
) : HistoricalPriceSource() {
    override suspend fun fetchHistoricalPrice(date: Date): Result<Price.Historical> =
        historicalPriceService.fetchHistoricalPrice(date = date)
            .fold()
            .log()
            .map { it.toDomain() }
}

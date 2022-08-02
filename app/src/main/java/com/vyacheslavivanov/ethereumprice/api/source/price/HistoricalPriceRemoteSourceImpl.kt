package com.vyacheslavivanov.ethereumprice.api.source.price

import com.vyacheslavivanov.ethereumprice.api.mappers.toDomain
import com.vyacheslavivanov.ethereumprice.api.service.price.HistoricalPriceService
import com.vyacheslavivanov.ethereumprice.api.util.fold
import com.vyacheslavivanov.ethereumprice.api.util.log
import com.vyacheslavivanov.ethereumprice.data.price.Price
import com.vyacheslavivanov.ethereumprice.di.PriceApiModule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class HistoricalPriceRemoteSourceImpl @Inject constructor(
    @PriceApiModule.PriceApi private val historicalPriceService: HistoricalPriceService
) : HistoricalPriceRemoteSource() {
    override fun getHistoricalPriceFlow(date: Date): Flow<Result<Price.Historical>> = flow {
        val result = historicalPriceService.fetchHistoricalPrice(date.time / 1000L)
            .fold()
            .log()
            .map { it.toDomain() }

        emit(result)
    }
}

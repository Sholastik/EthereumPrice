package com.vyacheslavivanov.ethereumprice.data.price

import com.vyacheslavivanov.ethereumprice.api.source.price.LivePriceSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PriceRepository @Inject constructor(
    private val livePriceSource: LivePriceSource
) {
    val priceFlow by lazy {
        livePriceFlow
    }

    private val livePriceFlow: Flow<Result<Price>> = flow {
        while (true) {
            emit(livePriceSource.fetchPrice())
            delay(1000)
        }
    }
}

package com.vyacheslavivanov.ethereumprice.data.price

import com.vyacheslavivanov.ethereumprice.api.source.price.LivePriceSource
import com.vyacheslavivanov.ethereumprice.data.Resource
import com.vyacheslavivanov.ethereumprice.data.Resource.Companion.toResource
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

    private val livePriceFlow: Flow<Resource<Price.Live>> = flow {
        while (true) {
            val resource = livePriceSource.fetchPrice().toResource()
            emit(resource)
            delay(1000)
        }
    }
}

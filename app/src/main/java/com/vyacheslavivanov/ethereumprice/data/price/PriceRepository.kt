package com.vyacheslavivanov.ethereumprice.data.price

import com.vyacheslavivanov.ethereumprice.api.source.price.LivePriceSource
import com.vyacheslavivanov.ethereumprice.data.Resource
import com.vyacheslavivanov.ethereumprice.data.Resource.Companion.toResource
import com.vyacheslavivanov.ethereumprice.di.PriceApiModule
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PriceRepository @Inject constructor(
    @PriceApiModule.PriceApi private val livePriceSource: LivePriceSource
) {
    val priceFlow by lazy {
        livePriceFlow
    }

    private val livePriceFlow: Flow<Resource<Price.Live>> = flow {
        while (true) {
            val resource = livePriceSource.fetchLivePrice().toResource()
            emit(resource)
            delay(1000)
        }
    }
}

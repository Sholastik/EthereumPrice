package com.vyacheslavivanov.ethereumprice.data.price

import com.vyacheslavivanov.ethereumprice.api.source.price.LivePriceSource
import com.vyacheslavivanov.ethereumprice.data.Resource
import com.vyacheslavivanov.ethereumprice.data.Resource.Companion.toResource
import com.vyacheslavivanov.ethereumprice.di.PriceApiModule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PriceRepository @Inject constructor(
    @PriceApiModule.PriceApi private val livePriceSource: LivePriceSource
) {
    val priceFlow: Flow<Resource<Price>> = livePriceSource.fetchLivePrice().map {
        it.toResource()
    }
}

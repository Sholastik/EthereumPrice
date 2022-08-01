package com.vyacheslavivanov.ethereumprice.api.source.price

import com.vyacheslavivanov.ethereumprice.api.mappers.toDomain
import com.vyacheslavivanov.ethereumprice.api.service.price.LivePriceService
import com.vyacheslavivanov.ethereumprice.api.util.fold
import com.vyacheslavivanov.ethereumprice.api.util.log
import com.vyacheslavivanov.ethereumprice.data.price.Price
import com.vyacheslavivanov.ethereumprice.di.PriceApiModule
import javax.inject.Inject

class LivePriceSourceImpl @Inject constructor(
    @PriceApiModule.PriceApi private val livePriceService: LivePriceService
) : LivePriceSource() {
    override suspend fun fetchLivePrice(): Result<Price.Live> =
        livePriceService.fetchLivePrice()
            .fold()
            .log()
            .map { it.toDomain() }
}

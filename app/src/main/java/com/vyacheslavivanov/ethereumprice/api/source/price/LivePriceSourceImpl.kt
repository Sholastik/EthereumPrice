package com.vyacheslavivanov.ethereumprice.api.source.price

import com.vyacheslavivanov.ethereumprice.api.mappers.toDomain
import com.vyacheslavivanov.ethereumprice.api.service.price.LivePriceService
import com.vyacheslavivanov.ethereumprice.api.util.fold
import com.vyacheslavivanov.ethereumprice.api.util.log
import com.vyacheslavivanov.ethereumprice.data.price.Price
import javax.inject.Inject

class LivePriceSourceImpl @Inject constructor(
    private val livePriceService: LivePriceService
) : LivePriceSource() {
    override suspend fun fetchPrice(): Result<Price.Live> =
        livePriceService.fetchLivePrice()
            .fold()
            .log()
            .map { it.toDomain() }
}

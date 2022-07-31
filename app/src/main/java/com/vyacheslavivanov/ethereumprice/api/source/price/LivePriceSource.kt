package com.vyacheslavivanov.ethereumprice.api.source.price

import com.vyacheslavivanov.ethereumprice.data.price.Price

abstract class LivePriceSource {
    abstract suspend fun fetchPrice(): Result<Price>
}

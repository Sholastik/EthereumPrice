package com.vyacheslavivanov.ethereumprice.api.mappers

import com.vyacheslavivanov.ethereumprice.api.dto.price.LivePriceResponse
import com.vyacheslavivanov.ethereumprice.data.price.Price

fun LivePriceResponse.toDomain(): Price =
    Price(
        price = price,
        isLive = true
    )

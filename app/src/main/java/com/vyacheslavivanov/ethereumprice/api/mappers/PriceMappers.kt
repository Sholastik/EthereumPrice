package com.vyacheslavivanov.ethereumprice.api.mappers

import com.vyacheslavivanov.ethereumprice.api.dto.price.HistoricalPriceResponse
import com.vyacheslavivanov.ethereumprice.api.dto.price.LivePriceResponse
import com.vyacheslavivanov.ethereumprice.data.price.Price

fun LivePriceResponse.toDomain(): Price.Live =
    Price.Live(price)

fun HistoricalPriceResponse.toDomain(): Price.Historical =
    data.data.last().run {
        Price.Historical(
            price = price,
            date = date
        )
    }

package com.vyacheslavivanov.ethereumprice.api.dto.price

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LivePriceResponse(
    @Json(name = "USD") val price: Double
)

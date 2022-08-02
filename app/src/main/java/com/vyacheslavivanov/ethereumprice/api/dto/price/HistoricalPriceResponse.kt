package com.vyacheslavivanov.ethereumprice.api.dto.price

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HistoricalPriceResponse(
    @Json(name = "Data") val data: Data
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "Data") val data: List<PriceResponse>
    ) {
        @JsonClass(generateAdapter = true)
        data class PriceResponse(
            @Json(name = "close") val price: Double,
            @Json(name = "time") val date: Long
        )
    }
}

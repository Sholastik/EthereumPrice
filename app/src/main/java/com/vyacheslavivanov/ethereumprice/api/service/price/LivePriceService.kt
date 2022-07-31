package com.vyacheslavivanov.ethereumprice.api.service.price

import com.vyacheslavivanov.ethereumprice.api.dto.price.LivePriceResponse
import retrofit2.Response
import retrofit2.http.GET

interface LivePriceService {
    @GET("https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=USD")
    fun fetchLivePrice(): Response<LivePriceResponse>
}

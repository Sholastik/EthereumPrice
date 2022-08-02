package com.vyacheslavivanov.ethereumprice.api.service.price

import com.vyacheslavivanov.ethereumprice.api.dto.price.HistoricalPriceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HistoricalPriceService {
    @GET("https://min-api.cryptocompare.com/data/v2/histominute?fsym=ETH&tsym=USD&limit=1")
    suspend fun fetchHistoricalPrice(@Query(value = "toTs") date: Long): Response<HistoricalPriceResponse>
}
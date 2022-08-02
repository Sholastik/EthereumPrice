package com.vyacheslavivanov.ethereumprice.api.service.price

import com.vyacheslavivanov.ethereumprice.api.dto.price.HistoricalPriceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HistoricalPriceService {
    @GET("https://min-api.cryptocompare.com/data/v2/histominute?fsym=ETH&tsym=USD&limit=1&toTs={timestamp}")
    suspend fun fetchHistoricalPrice(@Path(value = "timestamp") date: Long): Response<HistoricalPriceResponse>
}
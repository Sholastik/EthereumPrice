package com.vyacheslavivanov.ethereumprice.data.price

import com.vyacheslavivanov.ethereumprice.api.source.price.HistoricalPriceLocalSource
import com.vyacheslavivanov.ethereumprice.api.source.price.HistoricalPriceRemoteSource
import com.vyacheslavivanov.ethereumprice.api.source.price.LivePriceSource
import com.vyacheslavivanov.ethereumprice.data.Resource
import com.vyacheslavivanov.ethereumprice.data.Resource.Companion.toResource
import com.vyacheslavivanov.ethereumprice.di.PriceApiModule
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

class PriceRepository @Inject constructor(
    @PriceApiModule.PriceApi private val livePriceSource: LivePriceSource,
    @PriceApiModule.PriceApi private val historicalPriceLocalSource: HistoricalPriceLocalSource,
    @PriceApiModule.PriceApi private val historicalPriceRemoteSource: HistoricalPriceRemoteSource
) {
    val priceFlow: Flow<Resource<Price>> = channelFlow {
        historicalPriceLocalSource.getHistoricalPriceFlow().collectLatest { result ->
            result.fold(
                onSuccess = {
                    send(Resource.Success(it))
                },
                onFailure = {
                    livePriceSource.fetchLivePrice().map {
                        it.toResource()
                    }.collectLatest {
                        send(it)
                    }
                }
            )
        }
    }

    suspend fun fetchHistoricalPrice(date: Date): Result<Unit> {
        val result = historicalPriceRemoteSource.getHistoricalPriceFlow(date).first()

        result.onSuccess {
            historicalPriceLocalSource.saveHistoricalPrice(it)
        }

        return result.map { }
    }

    suspend fun clearHistoricalPrice(): Result<Unit> {
        return historicalPriceLocalSource.clearHistoricalPrice()
    }
}

package com.vyacheslavivanov.ethereumprice.api.source.price

import com.vyacheslavivanov.ethereumprice.data.price.Price
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HistoricalPriceLocalSourceImpl @Inject constructor(

) : HistoricalPriceLocalSource() {
    override fun getHistoricalPriceFlow(): Flow<Result<Price.Historical>> = flow {

    }
}
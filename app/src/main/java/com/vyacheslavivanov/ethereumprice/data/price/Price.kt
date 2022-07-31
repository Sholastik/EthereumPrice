package com.vyacheslavivanov.ethereumprice.data.price

import java.util.*

sealed class Price(
    open val price: Double,
) {
    data class Live(override val price: Double) : Price(price)
    data class Historical(
        override val price: Double,
        val date: Date
    ) : Price(price)
}

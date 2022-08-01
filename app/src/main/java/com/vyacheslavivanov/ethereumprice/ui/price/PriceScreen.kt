package com.vyacheslavivanov.ethereumprice.ui.price

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vyacheslavivanov.ethereumprice.data.price.Price

@Composable
fun PriceScreen() {
//    Scaffold {
//        PriceBody(
//            modifier = Modifier.padding(it),
//            price = price
//        )
//    }
}

@Composable
fun PriceBody(modifier: Modifier = Modifier, price: Price) {
    Column(
        modifier = modifier.padding(16.dp),
    ) {
        PriceScreenTitle()
        Spacer(modifier = Modifier.height(16.dp))
        PriceDateCard(price = price, onClick = {

        })
        Spacer(modifier = Modifier.height(24.dp))
        PriceDateSelectButton()
        Spacer(modifier = Modifier.height(48.dp))
        PriceText(price = price)
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun PriceScreenBodyPreview() {
    MaterialTheme {
        PriceBody(
            price = Price.Live(
                price = 100.0
            )
        )
    }
}

package com.vyacheslavivanov.ethereumprice.ui.price

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vyacheslavivanov.ethereumprice.data.Resource
import com.vyacheslavivanov.ethereumprice.data.price.Price
import com.vyacheslavivanov.ethereumprice.viewmodel.price.PriceViewModel

@Composable
fun PriceScreen(viewModel: PriceViewModel) {
    val price by viewModel.priceStateFlow.collectAsState()

    Scaffold {
        PriceBody(
            modifier = Modifier.padding(it),
            price = price
        )
    }
}

@Composable
fun PriceBody(modifier: Modifier = Modifier, price: Resource<Price>) {
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
fun PriceScreenBodySuccessPreview() {
    MaterialTheme {
        PriceBody(
            price = Resource.Success(
                Price.Live(
                    price = 100.0
                )
            )
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun PriceScreenBodyErrorPreview() {
    MaterialTheme {
        PriceBody(
            price = Resource.Error(
                RuntimeException("Example exception")
            )
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun PriceScreenBodyLoadingPreview() {
    MaterialTheme {
        PriceBody(
            price = Resource.Loading
        )
    }
}

package com.vyacheslavivanov.ethereumprice.ui.price

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vyacheslavivanov.ethereumprice.R
import com.vyacheslavivanov.ethereumprice.data.Resource
import com.vyacheslavivanov.ethereumprice.data.price.Price
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PriceDateCard(
    modifier: Modifier = Modifier,
    price: Resource<Price>,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        backgroundColor = Color(0xFFF2F3F5),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick
    ) {
        val text = when (price) {
            is Resource.Success -> when (price.data) {
                is Price.Live -> stringResource(id = R.string.price_card_date_now)
                is Price.Historical -> SimpleDateFormat(
                    stringResource(id = R.string.price_card_date_format),
                    Locale.getDefault()
                ).format(price.data.date)
            }
            else -> ""
        }

        Text(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun PriceDateCardPreview() {
    Column {
        PriceDateCard(
            modifier = Modifier.padding(16.dp),
            price = Resource.Success(
                Price.Live(
                    100.0
                )
            ),
            onClick = {}
        )
    }
}

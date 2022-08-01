package com.vyacheslavivanov.ethereumprice.ui.price

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vyacheslavivanov.ethereumprice.R
import com.vyacheslavivanov.ethereumprice.data.Resource
import com.vyacheslavivanov.ethereumprice.data.price.Price

@Composable
fun PriceText(
    modifier: Modifier = Modifier,
    price: Resource<Price>
) {
    val style = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        when (price) {
            is Resource.Success -> {
                Text(
                    text = "1",
                    style = style
                )
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_ethereum_logo),
                    contentDescription = null,
                )
                Text(
                    text = "= ${String.format("%.2f", price.data.price)} $",
                    style = style
                )
            }
            is Resource.Loading -> {
                CircularProgressIndicator()
            }
            is Resource.Error -> {
                Text(
                    stringResource(id = R.string.price_screen_exception_title),
                    style = style,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun PriceTextPreview() {
    Column {
        PriceText(
            modifier = Modifier.padding(16.dp),
            price = Resource.Success(
                Price.Live(
                    price = 100.0
                )
            )
        )
    }
}

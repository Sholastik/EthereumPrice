package com.vyacheslavivanov.ethereumprice.ui.price

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vyacheslavivanov.ethereumprice.R
import com.vyacheslavivanov.ethereumprice.data.price.Price
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PriceScreen() {
    Scaffold {
        Column(modifier = Modifier.padding(it)) {

        }
    }
}

@Composable
fun PriceScreenTitle(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        text = stringResource(R.string.price_screen_title),
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        textAlign = TextAlign.Center
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun PriceScreenTitlePreview() {
    Column {
        PriceScreenTitle()
    }
}

@Composable
fun PriceDateCard(modifier: Modifier = Modifier, price: Price) {
    Card(
        modifier = modifier,
        backgroundColor = Color(0xFFF2F3F5),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            text = when (price) {
                is Price.Live -> stringResource(id = R.string.price_card_date_now)
                is Price.Historical -> SimpleDateFormat(
                    stringResource(id = R.string.price_card_date_format),
                    Locale.getDefault()
                ).format(price.date)
            },
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
            price = Price.Live(
                100.0
            )
        )
    }
}

@Composable
fun PriceDateSelectButton(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()

    val offset by infiniteTransition.animateFloat(
        initialValue = -0.3f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 7500
            },
            repeatMode = RepeatMode.Reverse
        )
    )

    GradientButton(
        onClick = {

        },
        modifier = modifier,
        colors = listOf(
            Color(0xFF628FDC),
            Color(0xFF8256BD),
            Color(0xFFD87556)
        ),
        gradientOffset = offset,
        cornerRadius = 8.dp
    ) {
        Text(
            text = "Hello",
            color = Color.White
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun PriceDateSelectButtonPreview() {
    Column {
        PriceDateSelectButton(
            modifier = Modifier.padding(16.dp)
        )
    }
}

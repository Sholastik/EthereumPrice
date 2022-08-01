package com.vyacheslavivanov.ethereumprice.ui.price

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vyacheslavivanov.ethereumprice.R
import com.vyacheslavivanov.ethereumprice.ui.GradientButton

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
            text = stringResource(id = R.string.price_date_button_title),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = (-0.4).sp
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

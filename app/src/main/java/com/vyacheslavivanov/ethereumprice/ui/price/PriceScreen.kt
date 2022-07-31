package com.vyacheslavivanov.ethereumprice.ui.price

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vyacheslavivanov.ethereumprice.R

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
        fontWeight = FontWeight.Bold,
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


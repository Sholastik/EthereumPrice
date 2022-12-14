package com.vyacheslavivanov.ethereumprice.ui.price

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vyacheslavivanov.ethereumprice.R
import com.vyacheslavivanov.ethereumprice.data.Resource
import com.vyacheslavivanov.ethereumprice.data.price.Price
import com.vyacheslavivanov.ethereumprice.ui.price.bottomsheet.PriceDateSheetLayout
import com.vyacheslavivanov.ethereumprice.util.saveTextToClipboard
import com.vyacheslavivanov.ethereumprice.viewmodel.price.PriceViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PriceScreen(viewModel: PriceViewModel) {
    val price by viewModel.priceStateFlow.collectAsState()

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    val coroutineScope = rememberCoroutineScope()

    PriceDateSheetLayout(
        bottomSheetState = bottomSheetState,
        onDateSelected = {
            viewModel.setHistoricalPriceDate(it)
        },
        onDateCleared = {
            viewModel.clearHistoricalPriceDate()
        },
        onDismiss = {
            coroutineScope.launch {
                bottomSheetState.hide()
            }
        }
    ) {
        Scaffold {
            PriceBody(
                modifier = Modifier.padding(it),
                price = price,
                onSelectDateClick = {
                    coroutineScope.launch {
                        bottomSheetState.show()
                    }
                }
            )
        }
    }
}

@Composable
fun PriceBody(
    modifier: Modifier = Modifier,
    price: Resource<Price>,
    onSelectDateClick: () -> Unit
) {
    val context = LocalContext.current
    val dateFormat = stringResource(id = R.string.price_card_date_format)

    Column(
        modifier = modifier.padding(16.dp),
    ) {
        PriceScreenTitle()
        Spacer(modifier = Modifier.height(16.dp))

        PriceDateCard(
            price = price,
            onClick = {
                if (price is Resource.Success && price.data is Price.Historical) {
                    val date = SimpleDateFormat(
                        dateFormat,
                        Locale.getDefault()
                    ).format(price.data.date)

                    saveTextToClipboard(
                        context,
                        "date",
                        date
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        PriceDateSelectButton(onClick = onSelectDateClick)
        Spacer(modifier = Modifier.height(32.dp))
        PriceText(
            price = price,
            onClick = {
                if (price is Resource.Success) {
                    saveTextToClipboard(
                        context,
                        "ethereumPrice",
                        price.data.price.toString()
                    )
                }
            }
        )
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
            ),
            onSelectDateClick = {}
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
            ),
            onSelectDateClick = {}
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
            price = Resource.Loading,
            onSelectDateClick = {}
        )
    }
}

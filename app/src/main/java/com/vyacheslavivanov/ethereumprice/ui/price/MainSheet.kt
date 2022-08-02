package com.vyacheslavivanov.ethereumprice.ui.price

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vyacheslavivanov.ethereumprice.R
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MainSheet(
    modifier: Modifier = Modifier,
    date: Date?,
    time: Date?,
    onSetDate: (Date) -> Unit,
    onSetTime: (Date) -> Unit,
    onSelectDate: () -> Unit,
    onSelectTime: () -> Unit,
    isApplyButtonActive: Boolean,
    onApply: () -> Unit,
    onDismiss: () -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DismissButton(onClick = onDismiss)

            SheetTitle(text = stringResource(id = R.string.price_date_sheet_title))

            StateButton(
                text = stringResource(id = R.string.price_date_sheet_apply_title),
                color = Color(0xFF0050CF),
                isActive = isApplyButtonActive,
                onClick = onApply
            )
        }

        MainSheetDateTimeCards(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth(),
            date = date,
            time = time,
            onSelectDate = onSelectDate,
            onSelectTime = onSelectTime
        )
    }
}

@Composable
fun MainSheetDateTimeCards(
    modifier: Modifier = Modifier,
    date: Date?,
    time: Date?,
    onSelectDate: () -> Unit,
    onSelectTime: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val shouldShowDateHint by remember {
            derivedStateOf {
                date == null
            }
        }

        val shouldShowTimeHint by remember {
            derivedStateOf {
                date == null
            }
        }

        ClickableTextCard(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = if (shouldShowDateHint) {
                stringResource(id = R.string.price_date_sheet_date_hint)
            } else {
                SimpleDateFormat(
                    stringResource(id = R.string.price_date_sheet_date_format),
                    Locale.getDefault()
                ).format(date!!)
            },
            isHint = shouldShowDateHint,
            onClick = onSelectDate
        )

        ClickableTextCard(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = if (shouldShowTimeHint) {
                stringResource(id = R.string.price_date_sheet_time_hint)
            } else {
                SimpleDateFormat(
                    stringResource(id = R.string.price_date_sheet_time_format),
                    Locale.getDefault()
                ).format(time!!)
            },
            isHint = shouldShowTimeHint,
            onClick = onSelectTime
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClickableTextCard(
    modifier: Modifier = Modifier,
    text: String,
    isHint: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        backgroundColor = Color(0xFFF2F3F5),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick,
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = text,
            textAlign = TextAlign.Center,
            color = if (isHint) {
                Color(0xFF7C89A3)
            } else {
                Color(0xFF000000)
            }
        )
    }
}

package com.vyacheslavivanov.ethereumprice.ui.price

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SettingsBackupRestore
import androidx.compose.material.icons.filled.Today
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vyacheslavivanov.ethereumprice.R
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MainSheet(
    modifier: Modifier = Modifier,
    date: Date?,
    time: Date?,
    onSetDate: (Date) -> Unit,
    onSelectDate: () -> Unit,
    onSelectTime: () -> Unit,
    isApplyButtonActive: Boolean,
    onApply: () -> Unit,
    onDateCleared: () -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 4.dp
                )
                .padding(top = 4.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DismissButton(onClick = onDismiss)

            SheetTitle(text = stringResource(id = R.string.price_date_main_sheet_title))

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

        MainSheetQuickButtons(
            modifier = Modifier.fillMaxWidth(),
            onSetDate = onSetDate,
            onDateCleared = onDateCleared,
            time = time
        )
    }
}

@Composable
fun MainSheetDateTimeCards(
    modifier: Modifier = Modifier,
    date: Date?,
    time: Date?,
    onSelectDate: () -> Unit,
    onSelectTime: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val shouldShowDateHint = date == null
        val shouldShowTimeHint = time == null

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

@Composable
fun MainSheetQuickButtons(
    modifier: Modifier = Modifier,
    time: Date?,
    onSetDate: (Date) -> Unit,
    onDateCleared: () -> Unit
) {
    Column(modifier = modifier) {
        val stringIds = listOf(
            R.string.price_date_sheet_quick_button_today_title,
            R.string.price_date_sheet_quick_button_yesterday_title,
            R.string.price_date_sheet_quick_button_last_week_title
        )

        val dates = listOf(
            Calendar.getInstance().time,
            Calendar.getInstance().apply {
                add(Calendar.DAY_OF_MONTH, -1)
            }.time,
            Calendar.getInstance().apply {
                add(Calendar.DAY_OF_MONTH, -7)
            }.time
        )

        val imageVectors = listOf(
            Icons.Default.Today,
            Icons.Default.Today,
            Icons.Default.SettingsBackupRestore
        )

        val colors = listOf(
            0xFF4BB34B,
            0xFFFFA000,
            0xFF735CE6
        )

        val timeTitlePart = time?.let {
            SimpleDateFormat(
                stringResource(id = R.string.price_date_sheet_quick_button_time_format),
                Locale.getDefault()
            ).format(it)
        } ?: ""

        for (index in stringIds.indices) {
            MainSheetQuickButton(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(id = stringIds[index]) + timeTitlePart,
                dayOfWeekDate = dates[index],
                imageVector = imageVectors[index],
                tint = Color(colors[index]),
                onClick = {
                    onSetDate(dates[index])
                }
            )
        }

        MainSheetQuickButton(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = R.string.price_date_sheet_quick_button_current_date_time_title),
            dayOfWeekDate = null,
            imageVector = Icons.Default.Today,
            tint = Color(0xFFFF3347),
            onClick = onDateCleared
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainSheetQuickButton(
    modifier: Modifier = Modifier,
    title: String,
    dayOfWeekDate: Date?,
    imageVector: ImageVector,
    tint: Color,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        elevation = 0.dp,
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = imageVector,
                tint = tint,
                contentDescription = title
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = title,
                fontSize = 16.sp,
                letterSpacing = (-0.4).sp
            )

            dayOfWeekDate?.let {
                Text(
                    text = SimpleDateFormat(
                        stringResource(id = R.string.price_date_sheet_quick_button_date_format),
                        Locale.getDefault()
                    ).format(it),
                    color = Color(0xFF7C89A3),
                    fontSize = 16.sp,
                    letterSpacing = (-0.4).sp
                )
            }
        }
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
        elevation = 0.dp,
        onClick = onClick,
    ) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = text,
            textAlign = TextAlign.Center,
            letterSpacing = (-0.4).sp,
            color = if (isHint) {
                Color(0xFF7C89A3)
            } else {
                Color(0xFF000000)
            }
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun MainSheetPreview() {
    MainSheet(
        date = null,
        time = null,
        onSetDate = {},
        onSelectDate = { },
        onSelectTime = { },
        isApplyButtonActive = true,
        onApply = { },
        onDateCleared = {},
        onDismiss = {}
    )
}

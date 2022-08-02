package com.vyacheslavivanov.ethereumprice.ui.price

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vyacheslavivanov.ethereumprice.R
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PriceDateSheetLayout(
    modifier: Modifier = Modifier,
    bottomSheetState: ModalBottomSheetState,
    onDateSelected: (Date) -> Unit,
    onDateCleared: () -> Unit,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        modifier = modifier,
        sheetState = bottomSheetState,
        sheetContent = {
            PriceDateSheetContent(
                onDateSelected = {
                    onDateSelected(it)
                    onDismiss()
                },
                onDateCleared = {
                    onDateCleared()
                    onDismiss()
                },
                onDismiss = onDismiss
            )
        },
        sheetShape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp
        ),
        content = content
    )
}

enum class PriceSheets {
    Main,
    Date,
    Time,
}

@Composable
fun PriceDateSheetContent(
    modifier: Modifier = Modifier,
    onDateSelected: (Date) -> Unit,
    onDateCleared: () -> Unit,
    onDismiss: () -> Unit
) {
    var currentSheet by remember { mutableStateOf(PriceSheets.Main) }

    var date by remember { mutableStateOf<Date?>(null) }
    var time by remember { mutableStateOf<Date?>(null) }

    when (currentSheet) {
        PriceSheets.Main -> {
            val isApplyButtonActive by remember {
                derivedStateOf {
                    (date != null) == (time != null)
                }
            }

            MainSheet(
                modifier = modifier,
                date = date,
                time = time,
                onSetDate = {
                    date = it
                },
                onSetTime = {
                    time = it
                },
                onSelectDate = {
                    currentSheet = PriceSheets.Date
                },
                onSelectTime = {
                    currentSheet = PriceSheets.Time
                },
                isApplyButtonActive = isApplyButtonActive,
                onApply = {
                    if (date != null && time != null) {
                        val dateCalendar = Calendar.getInstance().apply {
                            setTime(date!!)
                        }

                        val timeCalendar = Calendar.getInstance().apply {
                            setTime(time!!)
                        }

                        val calendar = Calendar.getInstance().apply {
                            clear()
                            set(
                                dateCalendar[Calendar.YEAR],
                                dateCalendar[Calendar.MONTH],
                                dateCalendar[Calendar.DATE],
                                timeCalendar[Calendar.HOUR_OF_DAY],
                                timeCalendar[Calendar.MINUTE]
                            )
                        }

                        onDateSelected(calendar.time)
                    } else if (date == null && time == null) {
                        onDateCleared()
                    }
                },
                onDismiss = onDismiss
            )
        }

        PriceSheets.Date -> TODO()
        PriceSheets.Time -> TODO()
    }
}

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

@Composable
fun DismissButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            text = stringResource(id = R.string.price_date_sheet_dismiss_title),
            color = Color(0xFF0050CF),
            letterSpacing = (-0.4).sp,
            fontSize = 16.sp
        )
    }
}

@Composable
fun SheetTitle(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 16.sp,
        letterSpacing = (-0.4).sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun StateButton(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    isActive: Boolean,
    onClick: () -> Unit,
) {
    TextButton(
        modifier = modifier,
        enabled = isActive,
        onClick = onClick
    ) {
        Text(
            text = text,
            color = color.copy(
                alpha = if (isActive) {
                    1.0f
                } else {
                    0.4f
                }
            ),
            letterSpacing = (-0.4).sp,
            fontSize = 16.sp
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun PriceDateSheetContentPreview() {
    MaterialTheme {
        PriceDateSheetContent(
            onDateSelected = {},
            onDateCleared = {},
            onDismiss = {}
        )
    }
}

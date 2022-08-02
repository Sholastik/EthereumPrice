package com.vyacheslavivanov.ethereumprice.ui.price

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vyacheslavivanov.ethereumprice.R
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
                modifier = Modifier.padding(bottom = 48.dp),
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
                    date != null && time != null
                }
            }

            MainSheet(
                modifier = modifier,
                date = date,
                time = time,
                onSetDate = {
                    date = it
                },
                onSelectDate = {
                    currentSheet = PriceSheets.Date
                },
                onSelectTime = {
                    currentSheet = PriceSheets.Time
                },
                isApplyButtonActive = isApplyButtonActive,
                onApply = {
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
                },
                onDateCleared = onDateCleared,
                onDismiss = {
                    time = null
                    date = null
                    onDismiss()
                }
            )
        }
        PriceSheets.Date -> {
            val isClearButtonActive by remember {
                derivedStateOf {
                    date != null
                }
            }

            DateSheet(date = date,
                onSetDate = {
                    date = it
                    currentSheet = PriceSheets.Main
                },
                isClearButtonActive = isClearButtonActive,
                onDismiss = {
                    currentSheet = PriceSheets.Main
                }
            )
        }
        PriceSheets.Time -> {
            val isClearButtonActive by remember {
                derivedStateOf {
                    time != null
                }
            }

            TimeSheet(
                time = time,
                onSetTime = {
                    time = it
                    currentSheet = PriceSheets.Main
                },
                isClearButtonActive = isClearButtonActive,
                onDismiss = {
                    currentSheet = PriceSheets.Main
                }
            )
        }
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

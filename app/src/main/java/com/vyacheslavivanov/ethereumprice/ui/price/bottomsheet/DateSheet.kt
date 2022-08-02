package com.vyacheslavivanov.ethereumprice.ui.price.bottomsheet

import android.widget.CalendarView
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.vyacheslavivanov.ethereumprice.R
import java.util.*

@Composable
fun DateSheet(
    modifier: Modifier = Modifier,
    date: Date?,
    onSetDate: (Date?) -> Unit,
    isClearButtonActive: Boolean,
    onDismiss: () -> Unit
) {
    Column(modifier = modifier.padding(bottom = 16.dp)) {
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

            SheetTitle(text = stringResource(id = R.string.price_date_date_sheet_title))

            StateButton(
                text = stringResource(id = R.string.price_date_sheet_remove_title),
                color = Color(0xFFE64646),
                isActive = isClearButtonActive,
                onClick = {
                    onSetDate(null)
                }
            )
        }

        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                CalendarView(context)
            },
            update = { calendarView ->
                calendarView.setDate(
                    (date ?: Date()).time,
                    true,
                    true
                )

                calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
                    val newCalendar = Calendar.getInstance().apply {
                        clear()
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, month)
                        set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    }

                    onSetDate(newCalendar.time)
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
fun DateSheetPreview() {
    DateSheet(
        date = null,
        onSetDate = {},
        isClearButtonActive = true,
        onDismiss = {}
    )
}

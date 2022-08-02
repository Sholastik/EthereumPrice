package com.vyacheslavivanov.ethereumprice.ui.price

import android.widget.TimePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.vyacheslavivanov.ethereumprice.R
import java.util.*

@Composable
fun TimeSheet(
    modifier: Modifier = Modifier,
    time: Date?,
    onSetTime: (Date?) -> Unit,
    isClearButtonActive: Boolean,
    onDismiss: () -> Unit
) {
    var newTime by remember { mutableStateOf(time ?: Date()) }

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

            SheetTitle(text = stringResource(id = R.string.price_date_time_sheet_title))

            StateButton(
                text = stringResource(id = R.string.price_date_sheet_remove_title),
                color = Color(0xFFE64646),
                isActive = isClearButtonActive,
                onClick = {
                    onSetTime(null)
                }
            )
        }

        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                TimePicker(
                    context,
                    null,
                    com.google.android.material.R.style.Theme_MaterialComponents_Dialog
                ).apply {
                    setIs24HourView(true)
                }
            },
            update = { timePicker ->
                val calendar = Calendar.getInstance().apply {
                    setTime(time ?: Date())
                }

                timePicker.hour = calendar[Calendar.HOUR_OF_DAY]
                timePicker.minute = calendar[Calendar.MINUTE]

                timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
                    val newCalendar = Calendar.getInstance().apply {
                        clear()
                        set(Calendar.HOUR_OF_DAY, hourOfDay)
                        set(Calendar.MINUTE, minute)
                    }

                    newTime = newCalendar.time
                }
            }
        )

        Button(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0050CF)),
            onClick = {
                onSetTime(newTime)
            }
        ) {
            Text(
                text = stringResource(id = R.string.price_date_sheet_time_button_title),
                color = Color.White,
                fontSize = 16.sp,
                letterSpacing = (-0.4).sp
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun TimeSheetPreview() {
    TimeSheet(
        time = null,
        onSetTime = {},
        isClearButtonActive = true,
        onDismiss = {}
    )
}

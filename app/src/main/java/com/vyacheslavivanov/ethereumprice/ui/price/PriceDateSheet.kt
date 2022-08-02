package com.vyacheslavivanov.ethereumprice.ui.price

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PriceDateSheetLayout(
    bottomSheetState: ModalBottomSheetState,
    onDateSelected: (Date) -> Unit,
    onDateCleared: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            PriceDateSheetContent(
                onDateSelected = onDateSelected,
                onDateCleared = onDateCleared
            )
        },
        sheetShape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp
        ),
        content = content
    )
}

@Composable
fun PriceDateSheetContent(
    onDateSelected: (Date) -> Unit,
    onDateCleared: () -> Unit
) {
    Box(modifier = Modifier.height(256.dp))
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
            onDateCleared = {}
        )
    }
}

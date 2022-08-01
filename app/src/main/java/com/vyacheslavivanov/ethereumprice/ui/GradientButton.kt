package com.vyacheslavivanov.ethereumprice.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max

@Composable
fun GradientButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: List<Color>,
    gradientOffset: Float = 0f,
    cornerRadius: Dp = 0.dp,
    content: @Composable RowScope.() -> Unit
) {
    TextButton(
        onClick = onClick,
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
            .gradientBackground(
                colors = colors,
                cornerRadius = cornerRadius,
                gradientOffset = gradientOffset
            )
            .fillMaxWidth(),
        content = content
    )
}

private fun Modifier.gradientBackground(
    colors: List<Color>,
    cornerRadius: Dp,
    gradientOffset: Float
) = drawBehind {
    val fraction = 1.0f / max(1, colors.size - 1)

    drawRoundRect(
        brush = Brush.linearGradient(
            colorStops = colors.mapIndexed { index, color ->
                index * fraction + gradientOffset to color
            }.toTypedArray(),
        ),
        cornerRadius = CornerRadius(
            x = cornerRadius.toPx(),
            y = cornerRadius.toPx()
        ),
        size = size
    )
}

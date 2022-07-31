package com.vyacheslavivanov.ethereumprice.ui.price

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.*

@Composable
fun GradientButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: List<Color>,
    angle: Double = 0.0,
    cornerRadius: Dp = 0.dp,
    content: @Composable RowScope.() -> Unit
) {
    TextButton(
        onClick = onClick,
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
            .gradientBackground(
                colors = colors,
                angle = angle,
                cornerRadius = cornerRadius
            )
            .fillMaxWidth(),
        content = content
    )
}

private fun Modifier.gradientBackground(
    colors: List<Color>,
    angle: Double,
    cornerRadius: Dp
) = drawBehind {
    val angleRad = angle / 180.0 * PI
    val x = cos(angleRad)
    val y = StrictMath.sin(angleRad)

    val radius = sqrt(size.width.pow(2) + size.height.pow(2)) / 2f
    val offset = center + Offset((x * radius).toFloat(), (y * radius).toFloat())

    val exactOffset = Offset(
        x = min(offset.x.coerceAtLeast(0f), size.width),
        y = size.height - min(offset.y.coerceAtLeast(0f), size.height)
    )

    drawRoundRect(
        brush = Brush.linearGradient(
            colors = colors,
            start = Offset(size.width, size.height) - exactOffset,
            end = exactOffset
        ),
        cornerRadius = CornerRadius(
            x = cornerRadius.toPx(),
            y = cornerRadius.toPx()
        ),
        size = size
    )
}

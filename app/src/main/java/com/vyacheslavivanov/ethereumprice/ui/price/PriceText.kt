package com.vyacheslavivanov.ethereumprice.ui.price

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.vyacheslavivanov.ethereumprice.R
import com.vyacheslavivanov.ethereumprice.data.Resource
import com.vyacheslavivanov.ethereumprice.data.price.Price

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PriceText(
    modifier: Modifier = Modifier,
    price: Resource<Price>,
    onClick: () -> Unit
) {
    val style = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    )

    when (price) {
        is Resource.Success -> {
            Card(
                modifier = modifier,
                elevation = 0.dp,
                shape = RoundedCornerShape(8.dp),
                onClick = onClick
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    val (label, badge) = createRefs()

                    if (price.data is Price.Live) {
                        Text(
                            text = stringResource(id = R.string.price_text_live_badge_title),
                            modifier = Modifier
                                .constrainAs(badge) {
                                    baseline.linkTo(label.baseline, margin = 8.dp)
                                    start.linkTo(label.end, margin = 4.dp)
                                    top.linkTo(parent.top)
                                }
                                .drawBehind {
                                    drawRoundRect(
                                        brush = Brush.linearGradient(
                                            colors = listOf(
                                                Color(0xFFFC1E7B),
                                                Color(0xFFFA3349)
                                            )
                                        ),
                                        cornerRadius = CornerRadius(
                                            x = 4.dp.toPx(),
                                            y = 4.dp.toPx()
                                        )
                                    )
                                }
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }

                    Row(
                        modifier = Modifier.constrainAs(label) {
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "1",
                            style = style
                        )
                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_ethereum_logo),
                            contentDescription = null,
                        )
                        Text(
                            text = "= ${String.format("%.2f", price.data.price)} $",
                            style = style
                        )
                    }
                }
            }
        }
        is Resource.Loading -> {
            Row(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is Resource.Error -> {
            Text(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.price_screen_exception_title),
                style = style,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun PriceTextPreview() {
    Column {
        PriceText(
            modifier = Modifier.padding(16.dp),
            price = Resource.Success(
                Price.Live(
                    price = 100.0
                )
            ),
            onClick = {}
        )
    }
}

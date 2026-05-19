package com.tilda.crypto.presentation.coin_detail.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tilda.feature.crypto.domain.model.CoinPrice
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours

private val ChartHeight = 300.dp
private val PlotStartPadding = 16.dp
private val PlotTopPadding = 28.dp
private val PlotEndPadding = 16.dp
private val PlotBottomPadding = 28.dp
private const val Y_LABEL_COUNT = 5

@Composable
internal fun PriceLineChart(
    history: List<CoinPrice>,
    modifier: Modifier = Modifier
) {
    if (history.isEmpty()) return

    val range = remember(history) { history.priceRange() }
    val lineColor = if (history.last().closingPrice >= history.first().closingPrice) {
        Color(0xFF10B981)
    } else {
        Color(0xFFEF3358)
    }
    val gridColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.72f)
    val axisColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.42f)
    val markerSurface = MaterialTheme.colorScheme.surface

    var selectedIndex by remember(history) { mutableStateOf<Int?>(null) }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(ChartHeight)
            .padding(vertical = 8.dp)
            .pointerInput(history) {
                fun selectNearestPoint(position: Offset) {
                    if (history.isEmpty() || size.width == 0) return
                    val progress = (position.x / size.width).coerceIn(0f, 1f)
                    selectedIndex = (progress * (history.size - 1))
                        .roundToInt()
                        .coerceIn(0, history.lastIndex)
                }

                awaitEachGesture {
                    val down = awaitFirstDown(requireUnconsumed = false)
                    val pointerId = down.id
                    selectNearestPoint(down.position)

                    do {
                        val event = awaitPointerEvent()
                        val change = event.changes.firstOrNull { it.id == pointerId }
                        if (change != null) {
                            selectNearestPoint(change.position)
                        }
                    } while (event.changes.any { it.id == pointerId && it.pressed })

                    selectedIndex = null
                }
            }
    ) {
        val plot = PlotBounds(
            left = PlotStartPadding.toPx(),
            top = PlotTopPadding.toPx(),
            right = size.width - PlotEndPadding.toPx(),
            bottom = size.height - PlotBottomPadding.toPx(),
        )

        if (plot.width <= 0f || plot.height <= 0f) return@Canvas

        val points = history.mapIndexed { index, price ->
            Offset(
                x = plot.xFor(index, history.size),
                y = plot.yFor(price.closingPrice, range),
            )
        }

        drawChartGrid(
            plot = plot,
            gridColor = gridColor,
            axisColor = axisColor,
        )
        drawLineChart(
            points = points,
            lineColor = lineColor,
        )

        selectedIndex?.let { index ->
            drawSelectedPoint(
                index = index,
                plot = plot,
                points = points,
                lineColor = lineColor,
                markerSurface = markerSurface,
            )
        }
    }
}

private data class PriceRange(
    val min: Double,
    val max: Double
) {
    val span: Double = max - min
}

private data class PlotBounds(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float
) {
    val width: Float = right - left
    val height: Float = bottom - top

    fun xFor(index: Int, pointCount: Int): Float {
        if (pointCount <= 1) return left + width / 2f
        return left + width * index / (pointCount - 1)
    }

    fun yFor(value: Double, range: PriceRange): Float {
        if (range.span == 0.0) return top + height / 2f
        val progress = ((value - range.min) / range.span).toFloat()
        return bottom - height * progress
    }
}

private fun List<CoinPrice>.priceRange(): PriceRange {
    val minPrice = minOf { it.closingPrice }
    val maxPrice = maxOf { it.closingPrice }
    val padding = if (minPrice == maxPrice) {
        max(abs(maxPrice) * 0.02, 1.0)
    } else {
        (maxPrice - minPrice) * 0.08
    }

    return PriceRange(
        min = minPrice - padding,
        max = maxPrice + padding,
    )
}

private fun DrawScope.drawChartGrid(
    plot: PlotBounds,
    gridColor: Color,
    axisColor: Color
) {
    val dash = PathEffect.dashPathEffect(
        intervals = floatArrayOf(12.dp.toPx(), 8.dp.toPx())
    )

    repeat(Y_LABEL_COUNT) { index ->
        val progress = index / (Y_LABEL_COUNT - 1).toFloat()
        val y = plot.top + plot.height * progress

        drawLine(
            color = gridColor,
            start = Offset(plot.left, y),
            end = Offset(plot.right, y),
            strokeWidth = 1.dp.toPx(),
            pathEffect = dash,
        )
    }

    drawLine(
        color = axisColor,
        start = Offset(plot.left, plot.top),
        end = Offset(plot.left, plot.bottom),
        strokeWidth = 1.dp.toPx(),
    )
    drawLine(
        color = axisColor,
        start = Offset(plot.left, plot.bottom),
        end = Offset(plot.right, plot.bottom),
        strokeWidth = 1.dp.toPx(),
    )
}

private fun DrawScope.drawLineChart(
    points: List<Offset>,
    lineColor: Color
) {
    if (points.size == 1) {
        drawCircle(
            color = lineColor,
            radius = 5.dp.toPx(),
            center = points.first(),
        )
        return
    }

    val path = Path().apply {
        points.forEachIndexed { index, point ->
            if (index == 0) {
                moveTo(point.x, point.y)
            } else {
                lineTo(point.x, point.y)
            }
        }
    }

    drawPath(
        path = path,
        color = lineColor.copy(alpha = 0.18f),
        style = Stroke(
            width = 10.dp.toPx(),
            cap = StrokeCap.Round,
            join = StrokeJoin.Round,
        ),
    )
    drawPath(
        path = path,
        color = lineColor,
        style = Stroke(
            width = 3.dp.toPx(),
            cap = StrokeCap.Round,
            join = StrokeJoin.Round,
        ),
    )
}

private fun DrawScope.drawSelectedPoint(
    index: Int,
    plot: PlotBounds,
    points: List<Offset>,
    lineColor: Color,
    markerSurface: Color
) {
    val point = points.getOrNull(index) ?: return
    val dash = PathEffect.dashPathEffect(
        intervals = floatArrayOf(8.dp.toPx(), 8.dp.toPx())
    )

    drawLine(
        color = lineColor.copy(alpha = 0.4f),
        start = Offset(point.x, plot.top),
        end = Offset(point.x, plot.bottom),
        strokeWidth = 1.dp.toPx(),
        pathEffect = dash,
    )
    drawCircle(
        color = lineColor.copy(alpha = 0.12f),
        radius = 32.dp.toPx(),
        center = point,
    )
    drawCircle(
        color = lineColor.copy(alpha = 0.22f),
        radius = 20.dp.toPx(),
        center = point,
    )
    drawCircle(
        color = lineColor,
        radius = 7.dp.toPx(),
        center = point,
    )
    drawCircle(
        color = markerSurface,
        radius = 3.dp.toPx(),
        center = point,
    )
}

internal val previewLineChartHistory: List<CoinPrice> = listOf(
    120210.0,
    120620.0,
    120160.0,
    120280.0,
    120070.0,
    119980.0,
    119760.0,
    120120.0,
    120390.0,
    120520.0,
    120430.0,
    120480.0,
    120470.0,
    120990.0,
    120180.0,
    119820.0,
    119990.0,
    120610.0,
    120900.0,
    120705.0,
    120210.0,
    120330.0,
    120610.0,
).mapIndexed { index, closingPrice ->
    CoinPrice(
        openingPrice = closingPrice - 40.0,
        highestPrice = closingPrice + 120.0,
        lowestPrice = closingPrice - 120.0,
        closingPrice = closingPrice,
        dateTime = Clock.System.now().minus((22 - index).hours),
        volume = 10_000.0 + index * 120.0,
    )
}

@Preview
@Composable
private fun PriceLineChartPreview() {
    MaterialTheme {
        PriceLineChart(
            history = previewLineChartHistory
        )
    }
}

package com.codestart.listed.ui.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import com.codestart.listed.data.DataPoint
@Composable
fun SimpleGraph(data: List<DataPoint>) {
    val maxValue = data.maxByOrNull { it.value }?.value ?: 1f
    val steps = 25
    val graphPadding = 30.dp

    val maxSteps = (Math.ceil((maxValue / steps).toDouble()) * steps).toInt()
    val noOfGrids = maxSteps / steps

    val scrollState = rememberScrollState()

    Box(Modifier.fillMaxHeight().horizontalScroll(scrollState)) {
        Canvas(modifier = Modifier.fillMaxHeight().width((data.size * 100).dp)) {
            val width = size.width - graphPadding.toPx()
            val height = size.height - graphPadding.toPx()
            val pointWidth = width / (data.size - 1)

            // draw grid
            drawContext.canvas.nativeCanvas.apply {
                for (i in 0 until data.size) {
                    val xPos = graphPadding.toPx() + i * pointWidth
                    drawLine(xPos, height, xPos, 0f, Paint().apply { color = android.graphics.Color.LTGRAY })
                }
                for (i in 0..noOfGrids) {
                    val yPos = height - (i.toFloat() / noOfGrids) * height
                    drawLine(graphPadding.toPx(), yPos, width, yPos, Paint().apply { color = android.graphics.Color.LTGRAY })
                }
            }

            // draw X and Y axis
            drawLine(Color.Black, Offset(graphPadding.toPx(), height), Offset(width, height))
            drawLine(Color.Black, Offset(graphPadding.toPx(), height), Offset(graphPadding.toPx(), 0f))

            // draw X and Y axis labels
            drawContext.canvas.nativeCanvas.apply {
                for (i in data.indices) {
                    val xPos = graphPadding.toPx() + i * pointWidth
                    // Adjusting the y-coordinate for labels.
                    // You can change the 10f to whatever value gives you the right amount of space
                    drawText(data[i].month, xPos, height + 60f, Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 30f
                    })
                }
                for (i in 0..noOfGrids) {
                    val yPos = height - (i.toFloat() / noOfGrids) * height
                    drawText((i * steps).toString(), graphPadding.toPx() - 50f, yPos, Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 30f
                    })
                }
            }

            // draw graph
            val graphPath = Path()
            graphPath.moveTo(graphPadding.toPx(), height - (data[0].value / maxValue) * height)

            for (i in 0 until data.size - 1) {
                val xStart = graphPadding.toPx() + i * pointWidth
                val yStart = height - (data[i].value / maxValue) * height
                val xEnd = graphPadding.toPx() + (i + 1) * pointWidth
                val yEnd = height - (data[i + 1].value / maxValue) * height

                drawLine(Color.Blue, Offset(xStart, yStart), Offset(xEnd, yEnd), strokeWidth = 4f) // Use Jetpack Compose's Stroke
                graphPath.lineTo(xEnd, yEnd)
            }

            // close the path at the end of the x axis
            graphPath.lineTo(size.width - graphPadding.toPx(), size.height - graphPadding.toPx())
            graphPath.lineTo(graphPadding.toPx(), size.height - graphPadding.toPx())

            // draw gradient
            drawPath(
                graphPath,
                brush = Brush.verticalGradient(
                    0f to Color(0xCA208FF1),
                    1f to Color.Transparent
                )
            )


        }
    }
}


package com.example.digifyerp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digifyerp.model.MonthlySalePoint
import com.example.digifyerp.theme.DigifyPrimaryBlue
import com.example.digifyerp.theme.TextMuted

@Composable
fun SalesLineChart(
    dataPoints: List<MonthlySalePoint>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(vertical = 12.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxWidth().height(180.dp)) {
                if (dataPoints.isEmpty()) return@Canvas

                val width = size.width
                val height = size.height
                val maxVal = (dataPoints.maxOfOrNull { it.amount } ?: 2500.0).coerceAtLeast(100.0)

                // Draw dashed bottom baseline
                val dashEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                drawLine(
                    color = Color(0xFFE2E8F0),
                    start = Offset(0f, height - 10f),
                    end = Offset(width, height - 10f),
                    strokeWidth = 2f,
                    pathEffect = dashEffect
                )

                // Calculate points
                val stepX = width / (dataPoints.size - 1).coerceAtLeast(1)
                val points = dataPoints.mapIndexed { index, point ->
                    val x = index * stepX
                    val normalizedY = (point.amount / maxVal).toFloat()
                    val y = (height - 10f) - (normalizedY * (height - 40f))
                    Offset(x, y)
                }

                // Create smooth line path
                val linePath = Path().apply {
                    if (points.isNotEmpty()) {
                        moveTo(points.first().x, points.first().y)
                        for (i in 1 until points.size) {
                            val pPrev = points[i - 1]
                            val pCurr = points[i]
                            val ctrlX1 = (pPrev.x + pCurr.x) / 2f
                            val ctrlY1 = pPrev.y
                            val ctrlX2 = (pPrev.x + pCurr.x) / 2f
                            val ctrlY2 = pCurr.y
                            cubicTo(ctrlX1, ctrlY1, ctrlX2, ctrlY2, pCurr.x, pCurr.y)
                        }
                    }
                }

                // Fill gradient underneath
                val fillPath = Path().apply {
                    addPath(linePath)
                    lineTo(points.last().x, height - 10f)
                    lineTo(points.first().x, height - 10f)
                    close()
                }

                drawPath(
                    path = fillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            DigifyPrimaryBlue.copy(alpha = 0.20f),
                            DigifyPrimaryBlue.copy(alpha = 0.01f)
                        ),
                        startY = 0f,
                        endY = height
                    )
                )

                // Draw line stroke
                drawPath(
                    path = linePath,
                    color = DigifyPrimaryBlue,
                    style = Stroke(width = 5.dp.toPx(), cap = StrokeCap.Round)
                )

                // Draw circular markers at each point
                points.forEach { pt ->
                    // Outer white circle
                    drawCircle(
                        color = Color.White,
                        radius = 6.dp.toPx(),
                        center = pt
                    )
                    // Inner blue circle
                    drawCircle(
                        color = DigifyPrimaryBlue,
                        radius = 4.dp.toPx(),
                        center = pt
                    )
                }
            }
        }

        // X-Axis Month Labels
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            dataPoints.forEach { point ->
                Text(
                    text = point.monthName,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextMuted
                )
            }
        }
    }
}

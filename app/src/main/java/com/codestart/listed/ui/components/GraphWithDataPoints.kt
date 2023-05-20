package com.codestart.listed.ui.components

import androidx.compose.runtime.Composable
import com.codestart.listed.data.DataPoint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GraphWithDataPoints(convertedChartData: List<DataPoint>) {
    val dataPoints = listOf(
        DataPoint("Jan", 10f),
        DataPoint("Feb", 20f),
        DataPoint("Mar", 10f),
        DataPoint("Apr", 40f),
        DataPoint("May", 30f),
        DataPoint("Jun", 60f),
        DataPoint("Jul", 50f),
        DataPoint("Aug", 80f),
        DataPoint("Sep", 40f),
        DataPoint("Oct", 100f),
        DataPoint("Nov", 80f),
        DataPoint("Dec", 70f)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        SimpleGraph(convertedChartData)
    }
}

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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        SimpleGraph(convertedChartData)
    }
}

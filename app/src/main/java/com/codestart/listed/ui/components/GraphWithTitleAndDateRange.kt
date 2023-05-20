package com.codestart.listed.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.codestart.listed.R
import com.codestart.listed.data.DataPoint

@Composable
fun GraphWithTitleAndDateRange(
    title: String,
    dateRange: String,
    convertedChartData: List<DataPoint>?
) {
    Column (
        modifier = Modifier
            .padding(20.dp) // Padding outside
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)) // Round corners
            .background(Color.White)
            .padding(18.dp), // Padding inside
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF999CA0),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(4.dp)
                    .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                )
                    .padding(12.dp)
            ) {
                Text(
                    text = dateRange,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xAA000000),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Image(
                    painter = painterResource(R.drawable.ic_clock),
                    contentDescription = "Clock Icon",
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(24.dp)
        ) {
            convertedChartData?.let { GraphWithDataPoints(it) }
        }

    }
}

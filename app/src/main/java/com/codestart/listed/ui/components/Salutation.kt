package com.codestart.listed.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

@Composable
fun Salutation( greeting: String , name: String) {
    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 20.dp)
    ) {
        Text (
            fontSize = 16.sp,
            style = TextStyle(
                fontWeight = FontWeight.Light,
                color = Color(0xFF999CA0)
            ),
            modifier = Modifier.padding(bottom=8.dp),
            text = localizedGreeting()
        )
        Text (
            fontSize = 24.sp,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                color = Color.Black
            ),
            modifier = Modifier.padding(bottom=25.dp),
            text = "$name 👋"
        )
    }

}

fun localizedGreeting(): String {
    val currentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

    return when (currentTime) {
        in 6..11 -> "Good Morning"
        in 12..17 -> "Good Afternoon"
        in 18..20 -> "Good Evening"
        else -> "Good Night"
    }
}
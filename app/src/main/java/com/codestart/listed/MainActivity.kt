package com.codestart.listed

import TabTile
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codestart.listed.data.api.ApiResponse
import com.codestart.listed.ui.components.GraphWithTitleAndDateRange
import com.codestart.listed.ui.components.RoundCornerCard
import com.codestart.listed.ui.components.Salutation
import com.codestart.listed.ui.components.displayLinks
import com.codestart.listed.ui.theme.ListedTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ListedTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main()
                }
            }
        }
    }
}


@Composable
fun Main() {
    val token =  BuildConfig.TOKEN_KEY;

    val dashboardData = produceState<ApiResponse?>(initialValue = null) {
        value = fetchDashboardData(token)
    }

    val topLinks = dashboardData.value?.data?.top_links
    val recentLinks = dashboardData.value?.data?.recent_links
    val overallUrlChartData = dashboardData.value?.data?.overall_url_chart
    val convertedChartData = overallUrlChartData?.let { convertChartData(it) }

    val userName = "Mayur"

    val tabs = listOf<String>("Top Links", "Recent Links")
    Box(modifier = Modifier.fillMaxSize()) {
        Column (
            modifier = Modifier
                .background(Color(0xFF0E6FFF))

                .padding(top = 24.dp)
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()

                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text (
                    fontSize = 24.sp,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    text = "Dashboard"
                )
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(48.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.1f), // Slightly transparent
                            shape = RoundedCornerShape(4.dp) // Rounded corners
                        ),

                ) {
                    Image(
                        modifier = Modifier
                            .padding(10.dp)
                            .size(24.dp)
                            .align(Alignment.Center),
                        painter = painterResource(id = R.drawable.ic_settings),
                        contentDescription = stringResource(id = R.string.settings_image_description)
                    )
                }

            }

            RoundCornerCard {

                Salutation("Good Morning", userName)


                GraphWithTitleAndDateRange(title = "Overview", dateRange = "22 Aug - 23 Sep ", convertedChartData)
                var selectedTab by remember { mutableStateOf(tabs[0]) }

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(start = 20.dp, bottom = 25.dp)
                ) {
                    items(tabs) { tab ->
                        TabTile(tab, isSelected = tab == selectedTab) {
                            selectedTab = tab
                        }
                    }
                }

                when (selectedTab) {
                    "Top Links" -> topLinks?.let { displayLinks(it) }
                    "Recent Links" -> recentLinks?.let { displayLinks(it) }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ListedTheme {
        Main()
    }
}
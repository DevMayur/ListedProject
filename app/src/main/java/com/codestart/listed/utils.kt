package com.codestart.listed

import android.os.Build
import com.codestart.listed.data.DataPoint
import com.codestart.listed.data.api.ApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import java.text.SimpleDateFormat
import java.util.Locale


import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*


const val baseUrl = "https://api.inopenapp.com/api/v1"
suspend fun fetchDashboardData(token: String): ApiResponse {
    val endpoint = "/dashboardNew"
    val client = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }
    val response: ApiResponse = client.get("$baseUrl$endpoint") {
        headers {
            append(HttpHeaders.Authorization, "Bearer $token")
        }
    }
    client.close()

    return response
}


fun convertDate(date: String): String {
    val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val targetFormat = SimpleDateFormat("MMM-yyyy", Locale.ENGLISH)
    val dateObj = originalFormat.parse(date)
    return targetFormat.format(dateObj)
}

fun convertChartData(chartData: Map<String, Int>): List<DataPoint> {
    // Map to store our monthly data
    val monthlyData = mutableMapOf<String, Float>()

    for ((key, value) in chartData) {
        val monthYear = convertDate(key) // This will get the month-year string

        // Add the current value to the existing value for this month/year,
        // or just add it as a new entry if it doesn't exist yet
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            monthlyData[monthYear] = monthlyData.getOrDefault(monthYear, 0f) + value
        } else {
            // If the monthYear key is not in the map, Map.get() will return null,
            // and the ?: operator will replace that with 0f
            monthlyData[monthYear] = (monthlyData[monthYear] ?: 0f) + value
        }

    }

    // Convert our map to a list of DataPoints
    return monthlyData.map { (date, value) ->
        DataPoint(date, value)
    }
}

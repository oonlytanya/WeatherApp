package com.tanya.weatherapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tanya.weatherapp.WeatherViewModel

@Composable
fun WeatherDetailScreen(viewModel: WeatherViewModel) {

    val state = viewModel.weatherState.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF74ABE2), Color(0xFF5563DE))
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        if (state.loading) {
            CircularProgressIndicator(color = Color.White)
            return
        }

        state.error?.let {
            Text(text = it, color = Color.White, style = MaterialTheme.typography.bodyLarge)
            return
        }
        val weather = state.weather.firstOrNull()

        Card(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.9f)
            ),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {

            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = state.cityName ?: "City",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(Modifier.height(8.dp))

                val temp = state.temperature?.toInt() ?: 0
                Text(
                    text = "$temp°",
                    fontSize = 60.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color= Color.Black
                )
            }

            Spacer(Modifier.height(14.dp))

            val desc = weather?.description
                ?.replaceFirstChar { it.uppercase() }
                ?: "No description"

            Text(
                text = desc,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

        }
    }
}

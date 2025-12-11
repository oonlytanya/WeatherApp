package com.tanya.weatherapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.tanya.weatherapp.WeatherViewModel
import com.google.android.gms.location.Priority

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherSearchScreen(
    navController: NavController,
    viewModel: WeatherViewModel
) {
    var city by remember { mutableStateOf(TextFieldValue("")) }

    val context = LocalContext.current
    val fusedLocation = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val locationPermission = rememberPermissionState(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF4A90E2), Color(0xFF50C9C3))
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Weather App",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Enter city name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (city.text.isNotEmpty()) {
                        viewModel.getWeatherByCity(city.text)
                        navController.navigate("details_screen")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Search", style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = {
                    if (!locationPermission.status.isGranted) {
                        locationPermission.launchPermissionRequest()
                    } else {
                        fusedLocation.getCurrentLocation(
                            Priority.PRIORITY_HIGH_ACCURACY,
                            null).addOnSuccessListener { location ->
                                if (location != null) {
                                    viewModel.getWeatherByCoords(
                                        lat = location.latitude,
                                        lon = location.longitude
                                    )
                                } else {
                                    viewModel.setError("Unable to get location. Move outside or enable GPS.")
                                }
                                navController.navigate("details_screen")
                            }

                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Use My Current Location", color = Color.White)
            }
        }
    }
}

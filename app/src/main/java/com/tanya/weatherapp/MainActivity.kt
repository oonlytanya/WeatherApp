package com.tanya.weatherapp

import android.R.attr.apiKey
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.tanya.weatherapp.ui.theme.WeatherAppTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tanya.weatherapp.ui.theme.WeatherDetailScreen
import com.tanya.weatherapp.ui.theme.WeatherSearchScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            val navController = rememberNavController()
            val viewModel: WeatherViewModel = viewModel()

            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "search_screen"
                    ) {

                        // SEARCH SCREEN (user enters city or uses location)
                        composable("search_screen") {
                            WeatherSearchScreen(navController, viewModel)
                        }

                        // DETAILS SCREEN (shows weather info)
                        composable("details_screen") {
                            WeatherDetailScreen(viewModel)

                        }
                    }
                }
            }
        }
    }
}

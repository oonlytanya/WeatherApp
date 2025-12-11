package com.tanya.weatherapp

import android.R.attr.apiKey
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WeatherViewModel(): ViewModel() {

    private val _weatherState = mutableStateOf(WeatherState())
    val weatherState: State<WeatherState> = _weatherState

    private val apiKey = "a6268002033f2af5517fef146dbf188f"

    fun getWeatherByCity(city:String){
        viewModelScope.launch {
            _weatherState.value= WeatherState(loading=true)
            try{
                val response=apiService.getWeatherByCity(city, apiKey)

                _weatherState.value= WeatherState(
                    loading = false,
                    weather= response.weather,
                    temperature = response.main.temp.toInt(),
                    cityName= response.name

                )
            }catch(e: Exception){
                _weatherState.value = WeatherState(
                    loading = false,
                    error = "Failed to load weather for $city")
            }
        }
    }
    fun getWeatherByCoords(lat: Double, lon: Double){
        viewModelScope.launch {
            _weatherState.value=WeatherState(loading = true)
            try{
                val response=apiService.getWeatherByCoords(lat, lon, apiKey)
                _weatherState.value= WeatherState(weather=response.weather,
                    loading=false,
                    temperature = response.main.temp.toInt(),
                    cityName= response.name)
            }catch(e: Exception){
                _weatherState.value= WeatherState(
                    error="Failed to load weather by location",
                    loading=false
                )
            }
        }
    }
    fun setError(message: String) {
        _weatherState.value = WeatherState(
            loading = false,
            error = message
        )
    }
    fun getWeatherByLocation(){

    }
    data class WeatherState(
        val loading: Boolean = false,
        val weather: List<Weather> = emptyList(),
        val temperature: Int? = null,
        val cityName: String? = null,
        val error: String? = null
    )
}
# WeatherApp

## About
Shows current weather for current device location or entered city name. Uses OpenWeatherMap API.

## How to run
1. Clone repo
2. Add API key: open `local.properties` and add `OPENWEATHER_API_KEY=your_key` OR set inside `MainActivity.kt`
3. Open in Android Studio -> Build -> Run

## Permissions
App requests `ACCESS_FINE_LOCATION` to use device location.

## Libraries
- Retrofit 2
- Gson converter
- Coil for images
- Play Services Location
- Jetpack Compose
- Lifecycle ViewModel

## Assets
Images are in `app/src/main/res/drawable/`

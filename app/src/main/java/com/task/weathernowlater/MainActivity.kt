package com.task.weathernowlater

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.task.features.cityinput.presentation.CityInputScreen
import com.task.weathernowlater.cityweather.CityWeatherScreen
import com.task.data.model.CityWeather
import com.task.features.presentation.SplashScreen
import com.task.features.presentation.weatherdetails.CityWeatherViewModel
import com.task.features.presentation.forecast.ForecastScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface {
                    AppNavHost()
                }
            }
        }
    }
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val cityWeatherViewModel: CityWeatherViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(onNavigateNext = {
                navController.navigate("city_input") {
                    popUpTo("splash") { inclusive = true }
                }
            })
        }
        composable("city_input") {
            CityInputScreen(
                onNavigateToDetails = { cityWeather: CityWeather ->
                    cityWeatherViewModel.cityWeather = cityWeather
                    navController.navigate("city_weather")
                }
            )
        }
        composable("city_weather") {
            cityWeatherViewModel.cityWeather?.let { cityWeather ->
                CityWeatherScreen(
                    cityWeather,
                    onBack = { navController.popBackStack() },
                    onForecastClick = { navController.navigate("forecast/${cityWeather.name}") }
                )
            }
        }
        composable("forecast/{city}") { backStackEntry ->
            val city = backStackEntry.arguments?.getString("city") ?: ""
            ForecastScreen(city = city, onBack = { navController.popBackStack() })
        }
    }
}
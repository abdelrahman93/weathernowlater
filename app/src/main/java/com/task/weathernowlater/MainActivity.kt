package com.task.weathernowlater

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.task.weathernowlater.cityinput.presentation.CityInputScreen
import com.task.weathernowlater.cityweather.CityWeatherScreen
import com.task.weathernowlater.splash.presentation.SplashScreen
import com.task.weathernowlater.ui.theme.WeathernowlaterTheme
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
                onNavigateToDetails = { city ->
                    navController.navigate("city_weather/$city")
                }
            )
        }
        composable("city_weather/{city}") { backStackEntry ->
            val city = backStackEntry.arguments?.getString("city") ?: ""
            CityWeatherScreen(city = city)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeathernowlaterTheme {
        Greeting("Android")
    }
}
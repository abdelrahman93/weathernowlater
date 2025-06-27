package com.task.weathernowlater.cityinput.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.task.weathernowlater.ui.theme.DarkBlue
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityInputScreen(
    viewModel: CityInputViewModel = hiltViewModel(),
    onNavigateToDetails: (String) -> Unit
) {
    var city by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is CityInputViewModel.UiEvent.NavigateToDetails -> onNavigateToDetails(event.city)
                is CityInputViewModel.UiEvent.ShowError -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "My Cities",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            value = city,
            onValueChange = {
                city = it
            },
            placeholder = { Text("Search for cities", color = Color(0xFFB0B8C1)) },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFF232B3A),
                focusedTextColor = Color.White,
                cursorColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.fetchWeather(city) },
            enabled = city.isNotBlank(),
            modifier = Modifier.fillMaxWidth(),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0099FF)),
        ) {
            Text("Submit")
        }
        Spacer(modifier = Modifier.height(24.dp))
        val cities = listOf(
            WeatherCardData("Barcelona", "10:23", "29", "\uFE0F\u200D☁\uFE0F"),
            WeatherCardData("Bilbao", "10:23", "27", "\uFE0F\u200D☁\uFE0F"),
            WeatherCardData("Madrid", "10:23", "31", "\uFE0F\u200D☁\uFE0F"),
            WeatherCardData("Malaga", "10:23", "33", "\u2600\uFE0F\u200D\u2601\uFE0F")
        )
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            cities.forEach { city ->
                WeatherCard(city)
            }
        }
    }
}

data class WeatherCardData(
    val name: String,
    val time: String,
    val temp: String,
    val icon: String
)

@Composable
fun WeatherCard(city: WeatherCardData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFF232B3A))
            .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = city.icon,
            fontSize = 36.sp,
            modifier = Modifier.padding(end = 16.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = city.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = city.time,
                fontSize = 14.sp,
                color = Color(0xFFB0B8C1),
                fontWeight = FontWeight.SemiBold
            )
        }
        Text(
            text = city.temp + "°",
            fontSize = 32.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
} 
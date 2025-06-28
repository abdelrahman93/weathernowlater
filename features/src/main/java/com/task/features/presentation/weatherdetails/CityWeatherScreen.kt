package com.task.features.presentation.weatherdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.task.data.model.CityWeather
import com.task.core.common.CardBackgroundColor
import com.task.core.common.LightBlue
import com.task.core.common.PlaceholderTextColor
import com.task.core.common.PrimaryDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityWeatherScreen(cityWeather: CityWeather, onBack: (() -> Unit)? = null, onForecastClick: (() -> Unit)? = null) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primary,
        topBar = {
            TopAppBar(
                title = { Text("Air Conditions", color = MaterialTheme.colorScheme.tertiary) },
                navigationIcon = {
                    IconButton(onClick = { onBack?.invoke() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // City Name
            Text(
                text = cityWeather.name,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary
            )

            // Weather Icon
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.Transparent, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = com.task.core.common.util.mapIconToEmoji(cityWeather.icon),
                    fontSize = 80.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Temperature - Fixed property access
            Text(
                text = "${cityWeather.temperature.toInt()}°",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onForecastClick?.invoke() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LightBlue)
            ) {
                Text("6-day forecast", color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(24.dp))
            // Air Condition Grid
            AirConditionGrid(cityWeather)
        }
    }
}

@Composable
fun AirConditionGrid(cityWeather: CityWeather) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            AirConditionCard("UV INDEX", "3", modifier = Modifier.weight(1f))
            AirConditionCard("WIND", "${cityWeather.windSpeed} km/h", modifier = Modifier.weight(1f))
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            AirConditionCard("HUMIDITY", "${cityWeather.humidity}%", modifier = Modifier.weight(1f))
            AirConditionCard("FEELS LIKE", "${cityWeather.feelsLike.toInt()}°", modifier = Modifier.weight(1f))
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            AirConditionCard("CHANCE OF RAIN", "0%", modifier = Modifier.weight(1f))
            AirConditionCard("PRESSURE", "${cityWeather.pressure} hPa", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun AirConditionCard(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(CardBackgroundColor)
            .padding(vertical = 20.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = PlaceholderTextColor,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = value,
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

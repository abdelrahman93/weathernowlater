package com.task.features.presentation.forecast

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.task.core.common.DarkBlue
import com.task.core.common.PlaceholderTextColor
import com.task.core.common.PrimaryDark
import com.task.core.common.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastScreen(
    city: String,
    viewModel: ForecastViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ForecastEffect.ShowError -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(city) {
        viewModel.processIntent(ForecastIntent.LoadForecast(city))
    }

    Scaffold(
        containerColor = PrimaryDark,
        topBar = {
            TopAppBar(
                title = { Text("6-DAY FORECAST", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryDark
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryDark)
                .padding(padding)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = White
                    )
                }
                state.error != null -> {
                    Text("Error: ${state.error}", modifier = Modifier.align(Alignment.Center), color = Color.White)
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(PrimaryDark),
                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 0.dp),
                        verticalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        items(state.forecast) { day ->
                            ForecastDayRow(day)
                            if (day != state.forecast.last()) {
                                Divider(
                                    color = DarkBlue,
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ForecastDayRow(day: ForecastDay) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = day.date,
            color = PlaceholderTextColor,
            fontSize = 18.sp,
            modifier = Modifier.weight(1.2f)
        )
        Text(
            text = day.icon,
            fontSize = 28.sp,
            modifier = Modifier.weight(0.8f)
        )
        Text(
            text = day.description,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.weight(1.5f)
        )
        Text(
            text = day.maxTemp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "/${day.minTemp}",
            color = PlaceholderTextColor,
            fontSize = 16.sp,
            modifier = Modifier.weight(0.8f)
        )
    }
}

// Update ForecastDay model to match the UI
data class ForecastDay(
    val date: String,
    val icon: String,
    val description: String,
    val maxTemp: String,
    val minTemp: String
)

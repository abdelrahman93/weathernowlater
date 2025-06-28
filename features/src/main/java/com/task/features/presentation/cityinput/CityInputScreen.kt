package com.task.features.cityinput.presentation

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.task.core.common.CardBackgroundColor
import com.task.core.common.LightBlue
import com.task.core.common.PlaceholderTextColor
import com.task.core.common.PrimaryDark
import com.task.core.common.Red
import com.task.core.common.White
import com.task.core.common.util.showSnackbar
import com.task.data.model.CityWeather
import com.task.features.presentation.cityinput.CityInputState
import com.task.features.R
import com.task.features.presentation.cityinput.CityInputViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun CityInputScreen(
    viewModel: CityInputViewModel = hiltViewModel(),
    onNavigateToDetails: (CityWeather) -> Unit
) {
    var city by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isLoading = uiState.isLoading
    val cities by viewModel.cities.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is CityInputState.NavigateToDetails -> onNavigateToDetails(event.cityWeather)
                is CityInputState.ShowError -> showSnackbar(
                    coroutineScope,
                    snackbarHostState,
                    event.message
                )
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = PrimaryDark
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(PrimaryDark)
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "My Cities",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = White
            )
            Spacer(modifier = Modifier.height(24.dp))
            TextField(
                value = city,
                onValueChange = {
                    city = it
                },
                placeholder = { Text("Search for cities", color = PlaceholderTextColor) },
                singleLine = true,
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = CardBackgroundColor,
                    focusedTextColor = White,
                    cursorColor = White,
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
                enabled = city.isNotBlank() && !isLoading,
                modifier = Modifier.fillMaxWidth(),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = LightBlue),
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Submit")
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            if (cities.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 64.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_sunny),
                            contentDescription = "City Icon",
                            modifier = Modifier.size(100.dp)
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = "No cities found.",
                            color = White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Add your first city!",
                            color = PlaceholderTextColor,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(cities, key = { it.name }) { city ->
                        val dismissState = rememberDismissState()
                        if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                            LaunchedEffect(city.name) {
                                viewModel.deleteCity(city.name)
                                com.task.core.common.util.showSnackbar(
                                    coroutineScope,
                                    snackbarHostState,
                                    "City deleted"
                                )
                            }
                        }
                        SwipeToDismiss(
                            state = dismissState,
                            directions = setOf(DismissDirection.EndToStart),
                            background = {
                                val progress = dismissState.progress.fraction
                                if (progress > 0f) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(
                                                color = Red.copy(alpha = 0.7f * progress),
                                                shape = RoundedCornerShape(24.dp)
                                            )
                                            .padding(horizontal = 24.dp),
                                        contentAlignment = Alignment.CenterEnd
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = White.copy(alpha = 0.7f + 0.3f * progress),
                                            modifier = Modifier.size(32.dp)
                                        )
                                    }
                                }
                            },
                            dismissContent = {
                                WeatherCard(
                                    name = city.name,
                                    time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(
                                        Date(city.timestamp * 1000L)
                                    ), // Convert seconds to milliseconds
                                    temp = city.temp.toInt().toString(),
                                    icon = com.task.core.common.util.mapIconToEmoji(
                                        city.icon ?: ""
                                    ), // Handle nullable icon
                                    onClick = { viewModel.fetchWeather(city.name) }
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherCard(name: String, time: String, temp: String, icon: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(CardBackgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = icon,
            fontSize = 36.sp,
            modifier = Modifier.padding(end = 16.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = White
            )
            Text(
                text = time,
                fontSize = 14.sp,
                color = PlaceholderTextColor,
                fontWeight = FontWeight.SemiBold
            )
        }
        Text(
            text = "$temp°",
            fontSize = 32.sp,
            color = White,
            fontWeight = FontWeight.Bold
        )
    }
} 
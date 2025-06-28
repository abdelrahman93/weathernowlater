package com.task.features.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.task.features.R

@Composable
fun SplashScreen(onNavigateNext: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.ic_umbrella),
                contentDescription = "Umbrella",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = "Weather",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Weather App",
                fontSize = 20.sp,
                color = Color(0xFFB0B8C1),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(2f))
            Button(
                onClick = onNavigateNext,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0099FF)),
                modifier = Modifier
                    .size(72.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_forward),
                    contentDescription = "Next",
                    tint = Color.White,
                    modifier = Modifier.size(80.dp)
                )
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
} 
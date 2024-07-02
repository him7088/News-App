package com.example.news_app.presentation.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressIndicator() {

    val progress by animateFloatAsState(
        targetValue = 0.75f,
        animationSpec = tween(durationMillis = 1000), label = " "
    )
    Box(
        modifier = Modifier.fillMaxSize()

            .wrapContentSize()
            .background(Color.Gray, shape = CircleShape),
        contentAlignment = Alignment.Center

    ) {
       androidx.compose.material3.CircularProgressIndicator(
           progress = { progress},
           color = Color(0xFF5ABCE6),
           strokeWidth = 4.dp,
           modifier = Modifier.size(64.dp)
       )
    }
}
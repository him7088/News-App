package com.example.news_app.ui.onboarding.common

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.news_app.R
import com.example.news_app.ui.theme.NewsAppTheme

@Composable
fun OnBoardingPageButton(
    text : String,
    imageVector: ImageVector,
    onClick : () -> Unit,
    modifier: Modifier
) {


    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = Color(0xFF5ABCE6),
            containerColor = Color.White),
        modifier = modifier.size(36.dp)

        ) {

        Icon(imageVector = imageVector, contentDescription = null )
    }
}

@Preview
@Composable
fun ButtonPreview() {
    NewsAppTheme {
        //OnBoardingPageButton(text = "Hello") {}
    }
}
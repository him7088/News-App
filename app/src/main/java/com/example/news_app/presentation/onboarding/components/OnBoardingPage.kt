package com.example.news_app.presentation.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.news_app.R
import com.example.news_app.presentation.onboarding.Page
import com.example.news_app.presentation.onboarding.pages
import com.example.news_app.presentation.theme.NewsAppTheme

@Composable
fun OnBoardingPage(
    page : Page,
    modifier: Modifier
) {
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Image(
            painter = painterResource(id = page.image),
            contentDescription = page.description,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.mediumPadding1)))

        Text(
            text = page.title,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.mediumPadding2)),
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF5ABCE6)
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.mediumPadding1)))
        Text(
            text = page.description,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.mediumPadding2)),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Justify
        )
    }
}

@Preview
@Composable
fun OnBoardingPagePreview() {
    NewsAppTheme {
        OnBoardingPage(page = pages[0] , modifier =  Modifier)
        //OnBoardingPage(page = pages[1], modifier = Modifier )
    }
}
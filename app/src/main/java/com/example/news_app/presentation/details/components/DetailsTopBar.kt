package com.example.news_app.presentation.details.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.news_app.R
import com.example.news_app.presentation.theme.NewsAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar (
    isBookmarked : Boolean = false,
    onBrowsingClick : () -> Unit,
    onShareClick : () -> Unit,
    onBookmarkClick : () -> Unit,
    onBackClick : () -> Unit
)

{



    TopAppBar(
        title = { /*TODO*/ },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            actionIconContentColor = colorResource(id = R.color.app_color),
            navigationIconContentColor = colorResource(id = R.color.app_color)
            
        ),
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                
                Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                
            }
        },
        actions = {

            IconButton(onClick = { onBrowsingClick() }) {

                Icon(painter = painterResource(id = R.drawable.global_search ), contentDescription = null, modifier = Modifier.size(24.dp))

            }
            IconButton(onClick = { onShareClick() }) {

                Icon(painter = painterResource(id = R.drawable.baseline_share_24 ), contentDescription = null)

            }
            IconButton(onClick = { onBookmarkClick()}) {

                Icon(
                    painter = painterResource(id = if(isBookmarked) R.drawable.baseline_bookmark_24 else R.drawable.baseline_bookmark_border_24 ) ,
                    contentDescription = null
                )

            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DetailsTopBarPreview() {
    NewsAppTheme {
        DetailsTopBar(
            onBrowsingClick = { /*TODO*/ },
            onShareClick = { /*TODO*/ },
            onBookmarkClick = {  },
            isBookmarked = true
        ) {
            
        }
    }
}
package com.example.news_app.presentation.news_navigator.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.news_app.R
import com.example.news_app.presentation.theme.NewsAppTheme

@Composable
fun NewsBottomNavigation(
    items : List<BottomNavigationItem>,
    selected  : Int,
    onItemClicked : (Int) -> Unit

) {
    NavigationBar(
        modifier = Modifier.height(90.dp)
            .fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 10.dp
    ) {

        items.forEachIndexed { index, bottomNavigationItem ->
            NavigationBarItem(
                selected = index == selected,
                onClick = { onItemClicked(index) },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Icon(painter = painterResource(id = bottomNavigationItem.icon), contentDescription = null)

                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.extraSmallPadding)))

                        Text(
                            text = bottomNavigationItem.text,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(id = R.color.app_color),
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.secondary,
                    unselectedTextColor = MaterialTheme.colorScheme.secondary,
                    indicatorColor = MaterialTheme.colorScheme.background
                )
            )

        }
    }
}


@Preview
@Composable
fun BottomNavigationBarPreview() {
    NewsAppTheme {
        NewsBottomNavigation(items = listOf(
            BottomNavigationItem(icon = R.drawable.baseline_bookmark_border_24, text = "Bookmark"),
            BottomNavigationItem(icon = R.drawable.baseline_share_24, text = "Share"),
           // BottomNavigationItem(icon = R.drawable.ic_search_document, text = "Search")
        ), selected = 0) {

        }
    }
}
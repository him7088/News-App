package com.example.news_app.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.news_app.R
import com.example.news_app.domain.model.Article
import com.example.news_app.presentation.common.ArticlesList
import com.example.news_app.presentation.common.SearchBar
import com.example.news_app.presentation.navgraph.Route

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    article : LazyPagingItems<Article>,
    navigate : (String) -> Unit
) {

    val titles by remember {
        derivedStateOf {
            if(article.itemCount>10) {
                article.itemSnapshotList.items
                    .slice(IntRange(start = 0, endInclusive = 9))
                    .joinToString(separator = " \uD83d\uDFE5 "){
                        it.title
                    }
            }
            else {
                ""
            }
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.smallPadding))
            .systemBarsPadding()

    ) {
        
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_logo),
            contentDescription = null,
            modifier = Modifier
                .height(90.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
            )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.mediumPadding1)))

        SearchBar(
            modifier = Modifier,
            text = "", readOnly = true,
            onValueChange = {},
            onClick = {
                navigate(Route.SearchScreen.route)
            },
            onSearch = {}
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.mediumPadding1)))
        
        Text(
            text = titles,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = dimensionResource(id = R.dimen.mediumPadding1))
                .basicMarquee()
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.extraSmallPadding)))
        
        ArticlesList(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.mediumPadding1)), 
            articles = article,
            onClick = {
                navigate(Route.DetailScreen.route)
            }
            ) 
    }
}
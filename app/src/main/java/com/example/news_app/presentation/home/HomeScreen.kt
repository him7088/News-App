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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.containerColor
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.paging.compose.LazyPagingItems
import com.example.news_app.R
import com.example.news_app.domain.model.Article
import com.example.news_app.presentation.common.PullToRefreshLazyColumn
import com.example.news_app.presentation.common.SearchBar

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    article : LazyPagingItems<Article>,
    navigateToSearch : () -> Unit,
    navigateToDetails : (Article) -> Unit,
    viewModel: HomeViewModel
) {
    val pullToRefreshState = rememberPullToRefreshState()


    val isRefreshing = viewModel.isRefreshing.collectAsState()

    val onRefresh = { viewModel.refreshArticles() }



    val titles by remember {
        derivedStateOf {
            if(article.itemCount>10) {
                article.itemSnapshotList.items
                    .slice(IntRange(start = 0, endInclusive = 9))
                    .joinToString(separator = " \uD83D\uDFE6 "){
                        it.title
                    }
            }
            else { "" }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = dimensionResource(id = R.dimen.extraSmallPadding))
            .background(MaterialTheme.colorScheme.background)


    ) {
        PullToRefreshContainer(
            state = pullToRefreshState,
            indicator = {pullToRefreshState ->
                Indicator(state = pullToRefreshState, color = LocalContentColor.current
                )
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .zIndex(10f),
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = colorResource(id = R.color.app_color)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_logo),
            contentDescription = null,
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)

        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.smallPadding)))

        SearchBar(
            modifier = Modifier,
            text = "", readOnly = true,
            onValueChange = {},
            onClick = {
                navigateToSearch()
            },
            onSearch = {}
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.mediumPadding1)))

        Text(
            text = titles,
            modifier = Modifier
                .fillMaxWidth()
                //.padding(start = dimensionResource(id = R.dimen.extraSmallPadding))
                .basicMarquee()
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.smallPadding)))


        PullToRefreshLazyColumn(
            items = article,
            isRefreshing = isRefreshing.value,
            onRefresh ={onRefresh()},
            onClick = {navigateToDetails(it)},
            modifier = Modifier,
            pullToRefreshState = pullToRefreshState
        )


    }
}
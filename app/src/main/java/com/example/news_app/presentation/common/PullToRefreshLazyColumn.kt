package com.example.news_app.presentation.common

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.paging.compose.LazyPagingItems
import com.example.news_app.domain.model.Article

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshLazyColumn(
    pullToRefreshState : PullToRefreshState,
    items : LazyPagingItems<Article>,
    isRefreshing : Boolean,
    onRefresh : () -> Unit,
    onClick : (Article) -> Unit,
    modifier : Modifier,
    lazyListState: LazyListState = rememberLazyListState()
){



    Box (
        modifier = modifier.nestedScroll(pullToRefreshState.nestedScrollConnection)
    ){
        val handlePagingResult = handlePagingResult(articles = items)
        
        if(handlePagingResult) {
            LazyColumn (
                state = lazyListState,
                modifier = modifier.fillMaxSize()
            ){
                items(count = items.itemCount) { index ->
                    items[index]?.let {
                        ArticleCard(
                            modifier = Modifier,
                            article = it,
                            onClick = {onClick(it)}
                        )
                    }
                }
            }
        }
        LaunchedEffect(pullToRefreshState.isRefreshing) {
            if (pullToRefreshState.isRefreshing) {
                onRefresh()
            }
        }


        LaunchedEffect(isRefreshing) {
            if(isRefreshing) {

                pullToRefreshState.startRefresh()
            }
            else {
                pullToRefreshState.endRefresh()
            }
        }



        PullToRefreshContainer(
            state = pullToRefreshState,
            indicator = {pullToRefreshState ->
                Indicator(state = pullToRefreshState, color = LocalContentColor.current
                )
            },
            modifier = modifier.align(Alignment.TopCenter),
            containerColor = Color.Transparent
        )


    }
}





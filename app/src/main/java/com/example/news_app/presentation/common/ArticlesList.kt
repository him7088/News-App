package com.example.news_app.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.news_app.R
import com.example.news_app.domain.model.Article

@Composable
fun ArticlesList(
    modifier: Modifier,
    articles : List<Article>,
    onClick : (Article) -> Unit,
) {
    if(articles.isEmpty()) {
        EmptyScreen()
    }
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.mediumPadding1)),
            contentPadding = PaddingValues(all = dimensionResource(id = R.dimen.extraSmallPadding))
        ) {
            items(count = articles.size) { index ->
                articles[index].let {
                    ArticleCard(
                        modifier = Modifier,
                        article = it,
                        onClick = {onClick(it)},
                    )
                }
            }
        }

}

@Composable
fun ArticlesList(
    modifier: Modifier,
    articles : LazyPagingItems<Article>,
    onClick : (Article) -> Unit
) {

    val handlePagingResult = handlePagingResult(articles = articles)

    if(handlePagingResult) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.extraSmallPadding)),
            contentPadding = PaddingValues(all = dimensionResource(id = R.dimen.extraSmallPadding))
        ) {
            items(count = articles.itemCount) { index ->
                articles[index]?.let {
                    ArticleCard(
                        modifier = Modifier,
                        article = it,
                        onClick = {onClick(it)}
                    )
                }
            }
        }
    }
}


@Composable
fun handlePagingResult(
    articles: LazyPagingItems<Article>
) : Boolean {

    val loadState = articles.loadState
    val error = when {
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        else -> null
    }
        return when{

            loadState.refresh is LoadState.Loading -> {

                ShimmerEffect()
                false
            }

            articles.itemCount == 0 -> {
                EmptyScreen(noResult = true)
                false
            }

            error != null -> {
                EmptyScreen(error)
                false
            }
            else -> true
        }
}

@Composable
fun ShimmerEffect() {
    Column (
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.mediumPadding1))
    ){
        repeat(10) {
            ArticleCardShimmerEffect(
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.mediumPadding1))
            )
        }
    }
}
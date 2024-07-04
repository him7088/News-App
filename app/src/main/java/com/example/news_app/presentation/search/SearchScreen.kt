package com.example.news_app.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.news_app.R
import com.example.news_app.domain.model.Article
import com.example.news_app.presentation.common.ArticlesList
import com.example.news_app.presentation.common.SearchBar

@Composable
fun SearchScreen(
    state: SearchState,
    event: (SearchEvent) -> Unit,
    navigateToDetails : (Article) -> Unit
) {

    Column (
        modifier = Modifier
            .padding(
                top = dimensionResource(id = R.dimen.mediumPadding1),
                start = dimensionResource(id = R.dimen.extraSmallPadding),
                end = dimensionResource(id = R.dimen.extraSmallPadding)
            )
            .statusBarsPadding()
    ) {

        SearchBar(
            modifier = Modifier,
            text = state.searchQuery,
            readOnly = false,
            onValueChange = {
                event(SearchEvent.UpdateSearchQuery(it))
            },
            onSearch = {
                event(SearchEvent.SearchNews)
            }
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.mediumPadding1)))

        state.articles?.let {
            val articles = state.articles.collectAsLazyPagingItems()
            ArticlesList(
                modifier = Modifier,
                articles = articles,
                onClick = {
                    navigateToDetails(it)
                }
            )
        }
    }
}
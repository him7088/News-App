package com.example.news_app.presentation.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import coil.compose.rememberAsyncImagePainter
import com.example.news_app.R
import com.example.news_app.domain.model.Article
import com.example.news_app.presentation.bookmark.BookmarkViewModel
import com.example.news_app.presentation.details.components.DetailsTopBar

@Composable
fun DetailScreen(
    article: Article,
    event : (DetailsEvent) -> Unit,
    navigateUp : () -> Unit,
    viewModel: BookmarkViewModel
) {
   
    val context = LocalContext.current

    val bookmarkState by viewModel.state.collectAsState()

    val isBookmarked by remember(bookmarkState) {
        derivedStateOf { bookmarkState.articles.contains(article) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        DetailsTopBar(
            onBrowsingClick = {
                Intent(Intent.ACTION_VIEW).also {
                    it.data = Uri.parse(article.url)
                    if(it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onShareClick = {
                Intent(Intent.ACTION_SEND).also {
                    it.putExtra(Intent.EXTRA_TEXT, article.url)
                    it.type = "text/plain"
                    if(it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onBookmarkClick = {
                event(DetailsEvent.UpsertDeleteArticle(article))
            },

            onBackClick = {
                navigateUp()
            },
            isBookmarked = isBookmarked

        )
        
        LazyColumn (
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                start = dimensionResource(id = R.dimen.mediumPadding1),
                end = dimensionResource(id = R.dimen.mediumPadding1),
                top = dimensionResource(id = R.dimen.mediumPadding1)
            )
        ){
            item { 
                Image(
                    painter = rememberAsyncImagePainter(model = article.urlToImage),
                    contentDescription = null,
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.articleImageSize))
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
                
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.mediumPadding1)))
                
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.displaySmall,
                    color = colorResource(id = R.color.app_color)
                )
                Text(
                    text = article.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if(isSystemInDarkTheme()) Color.White else Color.Black
                )
                
            }
        }
    }
}


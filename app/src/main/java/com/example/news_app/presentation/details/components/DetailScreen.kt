package com.example.news_app.presentation.details.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.example.news_app.R
import com.example.news_app.domain.model.Article
import com.example.news_app.domain.model.Source
import com.example.news_app.presentation.theme.NewsAppTheme

@Composable
fun DetailScreen(
    article: Article,
    event : (DetailsEvent) -> Unit,
    navigateUp : () -> Unit
) {
   
    val context = LocalContext.current
    
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
                event(DetailsEvent.SaveArticle)
            },
            
            onBackClick = {
                navigateUp()
            }
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

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    NewsAppTheme {
        DetailScreen(article = Article(
            author= "Radek Zielinski",
            title= "VanEck Set to Launch Spot Bitcoin ETF on Australia’s ASX",
            description= "Prominent investment management firm VanEck announced that it is about to list Australia’s first spot Bitcoin (BTC) exchange-traded fund (ETF)… Continue reading VanEck Set to Launch Spot Bitcoin ETF on Australia’s ASX\nThe post VanEck Set to Launch Spot Bitcoi…",
            url= "https://readwrite.com/vaneck-set-to-launch-spot-bitcoin-etf-on-australias-asx/",
            urlToImage= "https://readwrite.com/wp-content/uploads/2024/06/ea85a934-c8fc-4d65-9279-ff85bb79fbae.webp",
            publishedAt= "2024-06-17T15:43:51Z",
            content= "Prominent investment management firm VanEck announced that it is about to list\r\n Australia’s first spot Bitcoin (BTC) exchange-traded fund (ETF) listed on the domestic Australian Securities Exchange … [+1968 chars]",
            source = Source(id = "", name = "")

        ),  event = {},
            navigateUp = {})
    }
    }

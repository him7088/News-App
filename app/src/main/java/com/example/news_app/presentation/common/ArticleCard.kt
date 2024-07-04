package com.example.news_app.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.news_app.R
import com.example.news_app.domain.model.Article
import com.example.news_app.domain.model.Source
import com.example.news_app.presentation.theme.NewsAppTheme

@Composable
fun ArticleCard(
    modifier: Modifier,
    article: Article,
    onClick:() -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .padding(bottom = dimensionResource(id = R.dimen.mediumPadding1))
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = article.urlToImage),
            contentDescription = null,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.articleSize))
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.smallPadding))
                .height(dimensionResource(id = R.dimen.articleSize))
        ) {

            Text(
                text = article.title,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.app_color),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis

            )
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = article.source.name,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color =  MaterialTheme.colorScheme.secondary,

                )
                Spacer(modifier = Modifier.width(6.dp))

               Icon(
                   painter = painterResource(id = R.drawable.baseline_access_time_24),
                   contentDescription = null,
                   modifier = Modifier.size(dimensionResource(id = R.dimen.smallIconSize)),
                   tint = MaterialTheme.colorScheme.secondary
               )
                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = article.publishedAt,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.secondary,

                    )


            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ArticlePreview() {
    NewsAppTheme {
        ArticleCard(modifier = Modifier, article = Article(
            author = "",
            content = "",
            description = "",
            publishedAt = "9:22 pm 30/06/2024",
            source = Source(id = "", name = "ABC"),
            title = "hello this article is for preview",
            url = "abc.com",
            urlToImage = ""
        )) {

        }
    }
}
package com.example.news_app.ui.onboarding.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.news_app.R
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.WormIndicatorType

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PageIndicator(
    pagerState: PagerState,
    pageSize : Int,

) {

    DotsIndicator(
            dotCount = pageSize,
            type = WormIndicatorType(
                dotsGraphic = DotGraphic(
                    16.dp,
                    borderWidth = 2.dp,
                    borderColor = Color(0xFF5ABCE6),
                    color = Color.Transparent,
                ),
                wormDotGraphic = DotGraphic(
                    16.dp,
                    color = Color(0xFF5ABCE6),
                ),
            ),
            pagerState = pagerState,
            modifier = Modifier.fillMaxWidth(0.4f)
        )


}






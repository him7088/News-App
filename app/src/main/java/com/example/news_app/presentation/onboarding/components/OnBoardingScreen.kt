package com.example.news_app.presentation.onboarding.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.news_app.R
import com.example.news_app.presentation.common.OnBoardingPageButton
import com.example.news_app.presentation.onboarding.pages
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    onEvent: (OnBoardingEvent) -> Unit,
    modifier: Modifier
) {

    Column (
        modifier = modifier.fillMaxSize()
    ){
        val pagerState = rememberPagerState (initialPage = 0){ pages.size}

        val buttonState = remember {
            derivedStateOf {
                when(pagerState.currentPage) {
                    0-> listOf("","Next")
                    in(1..2)-> listOf("Back","Next")
                    3-> listOf("Back","Welcome")
                    else -> listOf("","")
                }
            }
        }

        HorizontalPager(state = pagerState) {index->

            OnBoardingPage(page = pages[index], modifier = Modifier)
        }
        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.mediumPadding2))
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            PageIndicator(
                pagerState = pagerState,
                pageSize = pages.size,
                )


            Spacer(modifier = Modifier.fillMaxWidth(0.3f))


            val scope = rememberCoroutineScope()

            if(buttonState.value[0].isNotEmpty()) {
                OnBoardingPageButton(
                    text = buttonState.value[0],
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(page = pagerState.currentPage-1)
                        }
                    },
                    modifier = Modifier
                )

            }

            OnBoardingPageButton(
                text = buttonState.value[1],
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                onClick = {
                    scope.launch {
                        if(pagerState.currentPage == 3) {
                           onEvent(OnBoardingEvent.SaveAppEntry)
                        }
                        else {
                            pagerState.animateScrollToPage(page = pagerState.currentPage +1)
                        }
                    }
                },
                modifier = Modifier
            )


        }




    }
}

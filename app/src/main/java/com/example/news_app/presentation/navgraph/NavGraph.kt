package com.example.news_app.presentation.navgraph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.news_app.presentation.common.enterTransition
import com.example.news_app.presentation.common.exitTransition
import com.example.news_app.presentation.news_navigator.NewsNavigator
import com.example.news_app.presentation.news_navigator.SwipeNewsNavigator
import com.example.news_app.presentation.onboarding.components.OnBoardingScreen
import com.example.news_app.presentation.onboarding.components.OnBoardingViewModel

@Composable
fun NavGraph(
    startDestination : String
) {
    val navController = rememberNavController()



    NavHost(navController = navController, startDestination = startDestination ) {

        navigation(route = Route.AppStartNavigation.route, startDestination = Route.OnBoardingScreen.route){
            composable(
                route = Route.OnBoardingScreen.route,
                enterTransition = enterTransition(),
                exitTransition = exitTransition()
            ) {

                val viewModel : OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                )
            }
        }

        navigation(route = Route.NewsNavigation.route , startDestination = Route.NewsNavigationScreen.route) {
            
            composable(route = Route.NewsNavigationScreen.route,

            ){

                //NewsNavigator()
                SwipeNewsNavigator()
            }


            }

        }
    }

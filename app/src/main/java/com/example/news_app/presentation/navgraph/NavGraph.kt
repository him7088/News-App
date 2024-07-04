package com.example.news_app.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.news_app.presentation.news_navigator.NewsNavigator
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
                route = Route.OnBoardingScreen.route
            ) {
                val viewModel : OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                )
            }
        }

        navigation(route = Route.NewsNavigation.route , startDestination = Route.NewsNavigationScreen.route) {
            
            composable(route = Route.NewsNavigationScreen.route){



                NewsNavigator()
            }


            }
        }
    }

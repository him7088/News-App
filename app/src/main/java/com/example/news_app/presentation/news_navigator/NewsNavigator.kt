package com.example.news_app.presentation.news_navigator

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.map
import com.example.news_app.domain.model.Article
import com.example.news_app.presentation.bookmark.BookmarkScreen
import com.example.news_app.presentation.bookmark.BookmarkViewModel
import com.example.news_app.presentation.details.DetailScreen
import com.example.news_app.presentation.details.DetailsEvent
import com.example.news_app.presentation.details.DetailsViewModel
import com.example.news_app.presentation.home.HomeScreen
import com.example.news_app.presentation.home.HomeViewModel
import com.example.news_app.presentation.navgraph.Route
import com.example.news_app.presentation.news_navigator.components.NewsBottomNavigation
import com.example.news_app.presentation.news_navigator.components.bottomNavigationItems
import com.example.news_app.presentation.search.SearchScreen
import com.example.news_app.presentation.search.SearchViewModel

@Composable
fun NewsNavigator() {

    val navController : NavHostController = rememberNavController()

    val navBackState = navController.currentBackStackEntryAsState().value

    val isBottomBarVisible = remember(key1 = navBackState) {
        navBackState?.destination?.route == Route.HomeScreen.route ||
        navBackState?.destination?.route == Route.SearchScreen.route ||
        navBackState?.destination?.route == Route.BookmarkScreen.route
    }
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    selectedItem = remember(key1 = navBackState) {
        when(navBackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.BookmarkScreen.route ->2
            else -> 0
        }

    }



    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if(isBottomBarVisible) {
                NewsBottomNavigation(
                    items = bottomNavigationItems,
                    selected = selectedItem,
                    onItemClicked = { index ->
                        when (index) {

                            0 -> navigateToTab(
                                navController = navController,
                                route = Route.HomeScreen.route
                            )

                            1 -> navigateToTab(
                                navController = navController,
                                route = Route.SearchScreen.route
                            )

                            2 -> navigateToTab(
                                navController = navController,
                                route = Route.BookmarkScreen.route
                            )

                            else -> navigateToTab(
                                navController = navController,
                                route = Route.HomeScreen.route
                            )
                        }
                    }
                )
            }
        }
    )
    {

        val bottomPadding = it.calculateBottomPadding()

        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {

            composable(route = Route.HomeScreen.route) {
                val viewModel : HomeViewModel = hiltViewModel()

                val newsState = viewModel.newsState.collectAsState()

                val articles = newsState.value.collectAsLazyPagingItems()
                HomeScreen(
                    article = articles,
                    navigateToSearch = {
                        navigateToTab(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )
                    },
                    navigateToDetails = {article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )

                    },
                    viewModel = viewModel,

                )
            }

            composable(route = Route.SearchScreen.route) {
                val viewModel : SearchViewModel = hiltViewModel()
                val state = viewModel.state.collectAsState().value
                SearchScreen(
                    state = state,
                    event = viewModel::onEvent,
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }

            composable(route = Route.DetailScreen.route) {
                val viewModel : DetailsViewModel = hiltViewModel()
                val bookmarkViewModel : BookmarkViewModel = hiltViewModel()
                if(viewModel.sideEffect != null) {
                    Toast.makeText(LocalContext.current,viewModel.sideEffect,Toast.LENGTH_SHORT).show()
                    viewModel.onEvent(DetailsEvent.RemoveSideEffect)
                }
                navController.previousBackStackEntry?.savedStateHandle?.get<Article>("article")?.let { article ->
                    DetailScreen(
                        article = article,
                        event = viewModel::onEvent ,
                        navigateUp = {navController.navigateUp()},
                        viewModel = bookmarkViewModel
                    )
                }
            }

            composable(route = Route.BookmarkScreen.route) {
                val viewModel : BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.collectAsState().value

                BookmarkScreen(
                    state = state,
                    navigateToDetails = {article->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }


        }

    }

}

private fun navigateToTab(
    navController: NavController,
    route : String
) {
    navController.navigate(route = route){
        navController.graph.startDestinationRoute?.let {

            popUpTo(it) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}

private fun navigateToDetails(
    navController: NavController,
    article: Article
) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article",article)
    navController.navigate(
        route = Route.DetailScreen.route
    )
}


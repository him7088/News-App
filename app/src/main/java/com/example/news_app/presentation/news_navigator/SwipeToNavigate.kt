package com.example.news_app.presentation.news_navigator

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.news_app.domain.model.Article
import com.example.news_app.presentation.bookmark.BookmarkScreen
import com.example.news_app.presentation.bookmark.BookmarkViewModel
import com.example.news_app.presentation.details.DetailScreen
import com.example.news_app.presentation.details.DetailsViewModel
import com.example.news_app.presentation.home.HomeScreen
import com.example.news_app.presentation.home.HomeViewModel
import com.example.news_app.presentation.navgraph.Route
import com.example.news_app.presentation.news_navigator.components.NewsBottomNavigation
import com.example.news_app.presentation.news_navigator.components.bottomNavigationItems
import com.example.news_app.presentation.search.SearchScreen
import com.example.news_app.presentation.search.SearchViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeNewsNavigator() {
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val isBottomBarVisible = remember(navBackStackEntry) {
        navBackStackEntry?.destination?.route == Route.HomeScreen.route ||
                navBackStackEntry?.destination?.route == Route.SearchScreen.route ||
                navBackStackEntry?.destination?.route == Route.BookmarkScreen.route
    }

    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    // Synchronize pager state with navigation state
    LaunchedEffect(navBackStackEntry) {
        when (navBackStackEntry?.destination?.route) {
            Route.HomeScreen.route -> coroutineScope.launch { pagerState.animateScrollToPage(0, animationSpec = tween(200, easing = LinearEasing)) }
            Route.SearchScreen.route -> coroutineScope.launch { pagerState.animateScrollToPage(1,animationSpec = tween(200, easing = LinearEasing)) }
            Route.BookmarkScreen.route -> coroutineScope.launch { pagerState.animateScrollToPage(2,animationSpec = tween(200, easing = LinearEasing)) }
        }
    }

    // Update navigation state when pager state changes
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selectedItem = page
            val route = when(page) {
                0 -> Route.HomeScreen.route
                1-> Route.SearchScreen.route
                2-> Route.BookmarkScreen.route
                else -> return@collect
            }
            navController.navigate(route) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                NewsBottomNavigation(
                    items = bottomNavigationItems,
                    selected = selectedItem,
                    onItemClicked = { index ->
                        coroutineScope.launch {
                            pagerState.scrollToPage(index)
                        }
                    }
                )
            }
        }
    ) { innerPadding ->

        val bottomPadding = innerPadding.calculateBottomPadding()
        Box(modifier = Modifier.fillMaxSize()
            .padding(bottom = bottomPadding)) {
            // Render HorizontalPager only if not on DetailScreen
            if (navBackStackEntry?.destination?.route != Route.DetailScreen.route) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    when (page) {
                        0 -> {
                            val viewModel: HomeViewModel = hiltViewModel()
                            val newsState = viewModel.newsState.collectAsState()
                            val articles = newsState.value.collectAsLazyPagingItems()
                            HomeScreen(
                                article = articles,
                                navigateToSearch = {
                                    navController.navigate(Route.SearchScreen.route)
                                },
                                navigateToDetails = { article ->
                                    navigateToDetails(navController, article)
                                },
                                viewModel = viewModel
                            )
                        }
                        1 -> {
                            val viewModel: SearchViewModel = hiltViewModel()
                            val state = viewModel.state.collectAsState().value
                            SearchScreen(
                                state = state,
                                event = viewModel::onEvent,
                                navigateToDetails = { article ->
                                    navigateToDetails(navController, article)
                                }
                            )
                        }
                        2 -> {
                            val viewModel: BookmarkViewModel = hiltViewModel()
                            val state = viewModel.state.collectAsState().value
                            BookmarkScreen(
                                state = state,
                                navigateToDetails = { article ->
                                    navigateToDetails(navController, article)
                                }
                            )
                        }
                    }
                }
            }

            // Ensure NavHost is always initialized
            NavHost(
                navController = navController,
                startDestination = Route.HomeScreen.route
            ) {
                composable(route = Route.HomeScreen.route) { /* No-op, handled by pager */ }
                composable(route = Route.SearchScreen.route) { /* No-op, handled by pager */ }
                composable(route = Route.BookmarkScreen.route) { /* No-op, handled by pager */ }
                composable(route = Route.DetailScreen.route) {
                    val viewModel: DetailsViewModel = hiltViewModel()
                    val bookmarkViewModel: BookmarkViewModel = hiltViewModel()
                    navController.previousBackStackEntry?.savedStateHandle?.get<Article>("article")?.let { article ->
                        DetailScreen(
                            article = article,
                            event = viewModel::onEvent,
                            navigateUp = { navController.navigateUp() },
                            viewModel = bookmarkViewModel
                        )
                    }
                }
            }
        }
    }
}

private fun navigateToDetails(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(Route.DetailScreen.route)
}


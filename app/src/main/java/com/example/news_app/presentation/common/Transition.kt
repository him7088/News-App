package com.example.news_app.presentation.common

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry

fun enterTransition(): @JvmSuppressWildcards() AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? {
    return {
//        fadeIn(animationSpec = tween(300, easing = LinearEasing)) +
                slideInHorizontally(
                    initialOffsetX = {-it},
                    animationSpec = tween(300, easing = FastOutLinearInEasing)
                )
    }
}

fun exitTransition() : @JvmSuppressWildcards() AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? {
    return {
//        fadeOut(
//            animationSpec = tween(
//                300, easing = LinearEasing
//            )
//        ) +
        slideOutHorizontally(
            targetOffsetX = {it},
            animationSpec = tween(300, easing = FastOutLinearInEasing)
        )
    }
}
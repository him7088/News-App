package com.example.news_app

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.news_app.domain.usecases.app_Entry.AppEntryUseCases
import com.example.news_app.presentation.common.CircularProgressIndicator
import com.example.news_app.presentation.navgraph.NavGraph
import com.example.news_app.presentation.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var useCases: AppEntryUseCases
    private val viewModel  by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window,false)
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT
        installSplashScreen().apply {
            setKeepOnScreenCondition(viewModel.splashCondition)
        }
        lifecycleScope.launch {
            useCases.readAppEntry().collect{
             Log.d("test",it.toString())
            }
            //Log.d("screen",viewModel.startDestination.value)

        }
        setContent {
            NewsAppTheme {

               Surface {
                   val isLoading by viewModel.isLoading.collectAsState()
                   val startDestination by viewModel.startDestination.collectAsState()

                   if(isLoading) {
                        CircularProgressIndicator()
                   }
                   else {
                       NavGraph(startDestination = startDestination)
                   }


               }
            }
        }
    }
}

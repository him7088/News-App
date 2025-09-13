package com.example.news_app

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news_app.domain.usecases.app_Entry.AppEntryUseCases
import com.example.news_app.presentation.navgraph.Route

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases,

) : ViewModel() {

   val splashValue = mutableStateOf(false)

    private var _startDestination = MutableStateFlow(Route.AppStartNavigation.route)
    var startDestination : StateFlow<String> = _startDestination

    private var _isLoading = MutableStateFlow(true)
    var isLoading: StateFlow<Boolean> = _isLoading

    init {
       viewModelScope.launch {
           appEntryUseCases.readAppEntry().collect { shouldStartHomeScreen ->

               if(shouldStartHomeScreen) {
                   _startDestination.value = Route.NewsNavigation.route
               } else {
                   _startDestination.value = Route.AppStartNavigation.route
               }

                delay(4000)
                _isLoading.value = false
           }
       }


    }



}
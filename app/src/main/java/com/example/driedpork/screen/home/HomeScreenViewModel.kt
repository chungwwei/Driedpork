package com.example.driedpork.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.driedpork.model.coingecko.Market
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class HomeScreenUiState(
    val coinsList: List<Market> = emptyList(),
)

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    init {
        Log.d("HomeViewModel", "init homeviewmodel")
        getCoins()
    }

    private fun getCoins() {
        viewModelScope.launch {
            homeRepository.coinsList.collect { coinsList ->
                _uiState.value = _uiState.value.copy(
                    coinsList = coinsList
                )
            }
        }
    }

    override fun onCleared() {
        Log.d("HomeViewModel:", "homeviewmodel cleared")
        super.onCleared()
        Log.d("HomeViewModel:", "homeviewmodel cleared")
    }
}
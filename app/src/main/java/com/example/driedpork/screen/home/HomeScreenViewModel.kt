package com.example.driedpork.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.driedpork.model.coingecko.Market
import com.example.driedpork.screen.home.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class HomeScreenUiState(
    val coinsList: List<Market> = emptyList(),
)

class HomeScreenViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    fun getCoins() {
        viewModelScope.launch {
            homeRepository.coinsList.collect { coinsList ->
                _uiState.value = _uiState.value.copy(
                    coinsList = coinsList
                )
            }
        }
    }
}
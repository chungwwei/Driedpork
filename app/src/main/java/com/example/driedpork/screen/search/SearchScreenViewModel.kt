package com.example.driedpork.screen.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.driedpork.model.coingecko.search.TrendingCoin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class SearchScreenUiState(
    val trendingCoinList: List<TrendingCoin> = emptyList(),
)

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchScreenUiState())
    val uiState: StateFlow<SearchScreenUiState> = _uiState.asStateFlow()

    init {
        getTrending()
    }

    private fun getTrending() {
        viewModelScope.launch() {
            searchRepository.trending.collect { trending ->
                _uiState.value = _uiState.value.copy(
                    trendingCoinList = trending.coins
                )
            }
        }
    }
}
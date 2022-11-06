package com.example.driedpork.screen.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CoinDisplay(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val marketCapRank: Int?,
)

data class SearchScreenUiState(
    val trendingCoinList: List<CoinDisplay> = emptyList(),
    val coinsList: List<CoinDisplay> = emptyList(),
    val firstFiveCoins: List<CoinDisplay> = emptyList(),
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
                _uiState.value = _uiState.value.copy(trendingCoinList = trending.coins.map {
                    CoinDisplay(
                        id = it.item.id,
                        symbol = it.item.symbol,
                        name = it.item.name,
                        image = it.item.thumb,
                        marketCapRank = it.item.market_cap_rank ?: 0
                    )
                })
            }
        }
    }

    fun search(query: String) {
        viewModelScope.launch() {
            searchRepository.search(query).collect { searchResults ->
                _uiState.value = _uiState.value.copy(coinsList = searchResults.coins.map {
                    CoinDisplay(
                        id = it.id,
                        symbol = it.symbol,
                        name = it.name,
                        image = it.thumb,
                        marketCapRank = it.market_cap_rank ?: 0
                    )
                }, firstFiveCoins = searchResults.coins.map {
                    CoinDisplay(
                        id = it.id,
                        symbol = it.symbol,
                        name = it.name,
                        image = it.thumb,
                        marketCapRank = it.market_cap_rank ?: 0
                    )
                }.take(5)
                )
                Log.d("SearchScreenViewModel", "search: ${searchResults.coins}")
            }
        }
    }
}
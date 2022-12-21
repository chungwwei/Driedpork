package com.example.driedpork.screen.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.driedpork.model.coingecko.Market
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class DetailScreenUiState(
    val prices: List<List<Double>> = emptyList(),
)

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val detailRepository: DetailRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailScreenUiState())
    val uiState: StateFlow<DetailScreenUiState> = _uiState.asStateFlow()


    init {
        getCoinById("bitcoin")
        getMarkChartById("bitcoin")

        val coinId: String = savedStateHandle.get("coinId") ?: "null"
        Log.d("DetailViewModel", "coinId: $coinId")
        Log.d("DetailViewModel", "${savedStateHandle.keys()}")
        Log.d("DetailViewModel", "${savedStateHandle.get<String>("coinId")}")
    }


    private fun getCoinById(id: String) {
        viewModelScope.launch {
            detailRepository.getCoinById.collect { coin ->
                Log.d("DetailViewModel", "getCoinById: $coin")
//                _uiState.value = _uiState.value.copy(
//                    coinsList = coin
            }
        }
    }

    private fun getMarkChartById(id: String) {
        viewModelScope.launch {
            detailRepository.getMarketChartById.collect { marketChart ->
                _uiState.value = _uiState.value.copy(
                    prices = marketChart.prices.map {
                                it -> it.map { it.toDouble() }
                    }
                )
                Log.d("DetailViewModel", "getMarketChartById: ${uiState.value.prices}")
            }
        }
    }
}
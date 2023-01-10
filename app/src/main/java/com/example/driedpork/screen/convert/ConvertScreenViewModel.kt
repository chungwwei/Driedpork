package com.example.driedpork.screen.convert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.driedpork.model.coingecko.Market
import com.example.driedpork.screen.home.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ConvertedCoinItem(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String?,
    val convertedPrice: Double,
)

data class ConvertScreenUiState(
    val coinsList: List<Market> = emptyList(),
    val convertedCoinsList: List<ConvertedCoinItem> = emptyList(),
)

@HiltViewModel
class ConvertScreenViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConvertScreenUiState())
    val uiState: StateFlow<ConvertScreenUiState> = _uiState.asStateFlow()

    private var convertPriceJob: Job? = null


    init {
        getCoins()
    }


    fun submitValueChange(amount: String) {
        convertPriceJob?.cancel()
        convertPriceJob = viewModelScope.launch {
            // convert the amount to double
            val amountDouble = amount.toDoubleOrNull() ?: 0.0
            val convertedCoinsList = _uiState.value.coinsList.map {
                ConvertedCoinItem(
                    id = it.id,
                    symbol = it.symbol,
                    name = it.name,
                    image = it.image,
                    convertedPrice = String.format("%.10f", amountDouble / it.currentPrice)
                        .toDouble()
                )
            }
            // recompose
            _uiState.value = _uiState.value.copy(convertedCoinsList = convertedCoinsList)
        }
    }


    private fun getCoins() {
        viewModelScope.launch {
            homeRepository.coinsList.collect { coinsList ->
                _uiState.value = _uiState.value.copy(coinsList = coinsList,
                    convertedCoinsList = coinsList.take(20).map {
                        ConvertedCoinItem(
                            id = it.id,
                            symbol = it.symbol,
                            name = it.name,
                            image = it.image,
                            convertedPrice = 1.0 / it.currentPrice
                        )
                    })
            }
        }
    }
}
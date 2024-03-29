package com.example.driedpork.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.NumberFormat
import javax.inject.Inject
import javax.inject.Named


data class DetailScreenUiState(
    val prices: List<List<Double>> = emptyList(),
    val marketCapRank: String = "",
    val marketCap: String = "",
    val fullyDilutedvaluation: String = "",
    val totalVolume: String = "",
    val high24h: String = "",
    val low24h: String = "",
    val circulatingSupply: String = "",
    val totalSupply: String = "",
    val image: String = "",
    val currentPrice: String = "",
    val ath: String = "",
    val atl: String = "",
    val daysSelected: String = "1D",
)

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val detailRepository: DetailRepository,
    @Named("currency") private val currencyNumberFormat: NumberFormat,
    @Named("basic") private val basicNumberFormat: NumberFormat,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailScreenUiState())
    val uiState: StateFlow<DetailScreenUiState> = _uiState.asStateFlow()

    private val coinId: String = checkNotNull(savedStateHandle["coinId"])

    init {
        getCoinById(coinId)
        getMarketChartById(coinId, days = "1", shortHandDays = "1D")
    }

    private fun toCurrencyString(number: Double?): String {
        return try {
            currencyNumberFormat.format(number)
        } catch (e: Exception) {
            "NA"
        }
    }

    private fun toBasicString(number: Double?): String {
        return try {
            basicNumberFormat.format(number)
        } catch (e: Exception) {
            "NA"
        }
    }

    fun onDaysSelect(days: String) {
        when (days) {
            "1D" -> getMarketChartById(coinId, days = "1", shortHandDays = days)
            "1W" -> getMarketChartById(coinId, days = "7", shortHandDays = days)
            "1M" -> getMarketChartById(coinId, days = "30", shortHandDays = days)
            "3M" -> getMarketChartById(coinId, days = "90", shortHandDays = days)
            "max" -> getMarketChartById(coinId, days = "max", shortHandDays = days)
        }
    }

    private fun getCoinById(coinId: String) {
        viewModelScope.launch {
            detailRepository.getCoinById(coinId).collect { coin ->
                if (coin.marketData != null) {
                    _uiState.value = _uiState.value.copy(
                        marketCapRank = coin.marketData.marketCapRank.toString(),
                        marketCap = toCurrencyString(coin.marketData.marketCap["usd"]),
                        totalVolume = toCurrencyString(coin.marketData.totalVolume["usd"]),
                        high24h = toCurrencyString(coin.marketData.high24h["usd"]),
                        low24h = toCurrencyString(coin.marketData.low24h["uds"]),
                        circulatingSupply = toBasicString(coin.marketData.circulatingSupply),
                        totalSupply = toBasicString(coin.marketData.totalSupply),
                        currentPrice = toCurrencyString(coin.marketData.currentPrice["usd"]),
                        ath = toCurrencyString(coin.marketData.ath["usd"]),
                        atl = toCurrencyString(coin.marketData.atl["usd"]),
                        image = coin.image.thumb,
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        marketCapRank = "NA",
                        marketCap = "NA",
//                        fullyDilutedvaluation = "NA",
                        totalVolume = "NA",
                        high24h = "NA",
                        low24h = "NA",
                        circulatingSupply = "NA",
                        totalSupply = "NA",
                        image = coin.image.thumb,
                    )
                }
            }
        }
    }

    private fun getMarketChartById(coinId: String, days: String, shortHandDays: String) {
        viewModelScope.launch {
            detailRepository.getMarketChartById(coinId, days).collect { marketChart ->
                _uiState.value = _uiState.value.copy(
                    prices = marketChart.prices.map { it -> it.map { it.toDouble() } },
                    daysSelected = shortHandDays,
                )
            }
        }
    }
}
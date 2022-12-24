package com.example.driedpork.screen.detail

import com.example.driedpork.coingecko.CoingeckoAPI
import com.example.driedpork.model.coingecko.CoinData
import com.example.driedpork.model.coingecko.MarketChart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailRepository {

    suspend fun getCoinById(coinId: String): Flow<CoinData> = flow {
        val coinResponse = CoingeckoAPI.retrofitService.getCoinsId(coinId);
        val coinData = coinResponse.body();
        if (coinData != null) {
            emit(coinData)
        }
    }

    suspend fun getMarketChartById(coinId: String): Flow<MarketChart> = flow {
        val marketChartResponse = CoingeckoAPI.
            retrofitService.
            getCoinsIdMarketChart(id = coinId, vsCurrency = "usd", days = "10")
        val marketChart = marketChartResponse.body()
        if (marketChart!= null) {
            emit(marketChart)
        }
    }
}
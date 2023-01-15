package com.example.driedpork.screen.detail

import android.util.Log
import com.example.driedpork.coingecko.CoingeckoAPI
import com.example.driedpork.model.coingecko.CoinData
import com.example.driedpork.model.coingecko.MarketChart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class DetailRepository {

    suspend fun getCoinById(coinId: String): Flow<CoinData> = flow {
        val coinResponse = CoingeckoAPI.retrofitService.getCoinsId(coinId);
        val coinData = coinResponse.body();
        if (coinData != null) {
            emit(coinData)
        }
    }.catch {
        Log.d("DetailRepository", "error: $it")
    }

    suspend fun getMarketChartById(coinId: String, days: String): Flow<MarketChart> = flow {
        val marketChartResponse = CoingeckoAPI.retrofitService.getCoinsIdMarketChart(
            id = coinId,
            vsCurrency = "usd",
            days = days
        );
        val marketChart = marketChartResponse.body()
        if (marketChart != null) {
            emit(marketChart)
        }
    }.catch {
        Log.d("DetailRepository", "error: $it")
    }
}
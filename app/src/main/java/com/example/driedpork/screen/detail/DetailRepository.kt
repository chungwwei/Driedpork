package com.example.driedpork.screen.detail

import android.util.Log
import com.example.driedpork.coingecko.CoingeckoAPI
import com.example.driedpork.model.coingecko.MarketChart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailRepository {

    val getCoinById: Flow<String> = flow {
        val body = CoingeckoAPI.retrofitService.getCoinsId("bitcoin");
//        Log.d("DetailRepository", body.body().toString())
    }

    val getMarketChartById: Flow<MarketChart> = flow {
        val marketChartResponse = CoingeckoAPI.
            retrofitService.
            getCoinsIdMarketChart(id = "bitcoin", vsCurrency = "usd", days = "10")
        Log.d("DetailRepository", marketChartResponse.body().toString())
        val marketChart = marketChartResponse.body()
        if (marketChart!= null) {
            emit(marketChart)
        }
    }
}
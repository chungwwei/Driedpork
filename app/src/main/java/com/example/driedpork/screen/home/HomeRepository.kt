package com.example.driedpork.screen.home

import android.util.Log
import com.example.driedpork.coingecko.CoingeckoAPI
import com.example.driedpork.model.coingecko.Market
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeRepository {

    val coinsList: Flow<List<Market>> = flow {
        while(true) {
            val marketsResponse = CoingeckoAPI.retrofitService.getCoinsMarkets(
                vs_currency = "usd",
                ids = null,
                order = "market_cap_desc",
                per_page = 100,
                page = 1,
                sparkline = false,
                price_change_percentage = "1h,24h,7d,14d,30d,200d,1y"
            )
            val markets = marketsResponse.body()
            if (markets != null) {
                emit(markets)
            }
            delay(15000)
            Log.d("HomeRepository", "delaying then refetching")
        }
    }
}
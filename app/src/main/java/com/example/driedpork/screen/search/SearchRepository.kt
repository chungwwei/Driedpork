package com.example.driedpork.screen.search;

import com.example.driedpork.coingecko.CoingeckoAPI
import com.example.driedpork.model.coingecko.search.Trending;
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class SearchRepository {

    val trending: Flow<Trending> = flow {
            val trendingResponse = CoingeckoAPI
                .retrofitService
                .getSearchTrending()
            val trending = trendingResponse.body()
            if (trending != null) {
                emit(trending)
            }
    }
}

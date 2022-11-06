package com.example.driedpork.screen.search

import com.example.driedpork.coingecko.CoingeckoAPI
import com.example.driedpork.model.coingecko.search.SearchResults
import com.example.driedpork.model.coingecko.search.Trending
import com.example.driedpork.model.coingecko.search.TrendingCoinList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class SearchRepository {

    val trending: Flow<TrendingCoinList> = flow {
        val trendingResponse = CoingeckoAPI
            .retrofitService
            .getSearchTrending()
        val trending = trendingResponse.body()
        if (trending != null) {
            emit(trending)
        }
    }

    fun search(query: String): Flow<SearchResults> = flow {
        delay(1000)
        val searchResponse = CoingeckoAPI
            .retrofitService
            .search(query)
        val searchResults = searchResponse.body()
        if (searchResults != null) {
            emit(searchResults)
        }
    }

}

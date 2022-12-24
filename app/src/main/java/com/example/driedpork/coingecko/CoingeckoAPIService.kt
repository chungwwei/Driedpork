package com.example.driedpork.coingecko

import com.example.driedpork.model.coingecko.*
import com.example.driedpork.model.coingecko.search.SearchResults
import com.example.driedpork.model.coingecko.search.Trending
import com.example.driedpork.model.coingecko.search.TrendingCoinList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoingeckoAPIService {

    @GET("ping")
    suspend fun ping(): Response<Ping>

//    @GET("simple/price")
//    suspend fun getSimplePrice(): String
//
//    @GET("simple/supported_vs_currencies")
//    suspend fun getSupportedVsCurrencies(): String

    @GET("coins/list")
    suspend fun getCoinsList(): Response<List<Coin>>

    @GET("coins/markets")
    suspend fun getCoinsMarkets(
        @Query("vs_currency") vs_currency: String,
        @Query("ids") ids: String?,
        @Query("order") order: String?,
        @Query("per_page") per_page: Int?,
        @Query("page") page: Int?,
        @Query("sparkline") sparkline: Boolean?,
        @Query("price_change_percentage") price_change_percentage: String?,
    ): Response<List<Market>>

    @GET("coins/{id}")
    suspend fun getCoinsId(
        @Path("id") id: String,
    ): Response<CoinData>

    @GET("coins/{id}/market_chart")
    suspend fun getCoinsIdMarketChart(
        @Path("id") id: String,
        @Query("vs_currency") vsCurrency: String,
        @Query("days") days: String,
    ): Response<MarketChart>

//    @GET("coins/{id}/tickers")
//    suspend fun getCoinsIdTickers(): String
//
//    @GET("coins/{id}/history")
//    suspend fun getCoinsIdHistory(): String

    @GET("search/")
    suspend fun search(
        @Query("query") q: String,
    ) : Response<SearchResults>

    @GET("search/trending")
    suspend fun getSearchTrending(): Response<TrendingCoinList>
}
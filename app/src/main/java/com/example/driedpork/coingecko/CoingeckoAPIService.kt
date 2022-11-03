package com.example.driedpork.coingecko

import com.example.driedpork.model.coingecko.Coin
import com.example.driedpork.model.coingecko.Ping
import retrofit2.Response
import retrofit2.http.GET

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

//    @GET("coins/markets")
//    suspend fun getCoinsMarkets(): String
//
//    @GET("coins/{id}")
//    suspend fun getCoinsId(): String
//
//    @GET("coins/{id}/tickers")
//    suspend fun getCoinsIdTickers(): String
//
//    @GET("coins/{id}/history")
//    suspend fun getCoinsIdHistory(): String
}
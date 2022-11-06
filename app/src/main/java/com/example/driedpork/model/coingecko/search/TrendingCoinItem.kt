package com.example.driedpork.model.coingecko.search

data class TrendingCoinItem(

    val id: String,
//    @SerialName("coin_id")
    val coin_id: Int,
    val name: String,
    val symbol: String,
//    @SerialName("market_cap_rank")
    val market_cap_rank: Int,
    val thumb: String,
    val small: String,
    val large: String,
    val slug: String,
//    @SerialName("price_btc")
    val price_btc: Double,
    val score: Int
)

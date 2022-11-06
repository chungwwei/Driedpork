package com.example.driedpork.model.coingecko

data class CoinSearchList(
    val id: String,
    val symbol: String,
    val name: String,
    val api_symbol: String,
    val market_cap_rank: Int?,
    val thumb: String,
    val large: String,
    val platform: Map<String, String> = emptyMap()
)
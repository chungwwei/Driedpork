package com.example.driedpork.model.coingecko.search

data class Trending (
    val coins: List<TrendingCoin>,
    val exchanges: List<Any>,
)
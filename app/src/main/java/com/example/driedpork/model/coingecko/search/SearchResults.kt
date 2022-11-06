package com.example.driedpork.model.coingecko.search

import com.example.driedpork.model.coingecko.CoinSearchList

data class SearchResults(
    val coins: List<CoinSearchList>,
    val exchanges: List<Any>,
    val categories: List<Any>,
)


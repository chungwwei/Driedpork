package com.example.driedpork.model.coingecko

import com.squareup.moshi.Json

data class MarketChart(
    val prices: List<List<String>>,
    @Json(name = "market_caps")
    val marketCaps: List<List<String>>,
    @Json(name = "total_volumes")
    val totalVolumes: List<List<String>>
)

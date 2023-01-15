package com.example.driedpork.model.coingecko

import com.squareup.moshi.Json

data class Coin(
    val id: String,
    val symbol: String,
    val name: String,
    val platform: Any?,
)

data class CoinData(
    val id: String,
    val symbol: String,
    val name: String,
    @Json(name = "hashing_algorithm") val hashingAlgorithm: String? = null,
    @Json(name = "block_time_in_minutes") val blockTimeInMinutes: Long = 0,
    val categories: List<String> = emptyList(),
    val localization: Map<String, String> = emptyMap(),
    val description: Map<String, String> = emptyMap(),
//    val links: Links,
    val image: Image,
    @Json(name = "country_origin") val countryOrigin: String? = null,
    @Json(name = "genesis_date") val genesisDate: String? = null,
    @Json(name = "contract_address") val contractAddress: String? = null,
//    @Json(name = "ico_data")
//    val icoData: IcoData? = null,
    @Json(name = "market_cap_rank") val marketCapRank: Long = 0,
    @Json(name = "coingecko_rank") val coingeckoRank: Long? = 0,
    @Json(name = "coingecko_score") val coingeckoScore: Double = 0.0,
    @Json(name = "developer_score") val developerScore: Double = 0.0,
    @Json(name = "community_score") val communityScore: Double = 0.0,
    @Json(name = "liquidity_score") val liquidityScore: Double = 0.0,
    @Json(name = "public_interest_score") val publicInterestScore: Double = 0.0,
    @Json(name = "market_data") val marketData: MarketData? = null,
//    @Json(name = "community_data")
//    val communityData: CommunityData? = null,
//    @Json(name = "developer_data")
//    val developerData: DeveloperData? = null,
//    @Json(name = "public_interest_stats")
//    val publicInterestStats: PublicInterestStats? = null,
//    @Json(name = "status_updates")
//    val statusUpdates: List<Update>? = null,
    @Json(name = "last_updated") val lastUpdated: String? = null,
//    val tickers: List<Ticker>? = null,
    @Json(name = "sentiment_votes_up_percentage") val sentimentVotesUpPercentage: Float = 50f,
    @Json(name = "sentiment_votes_down_percentage") val sentimentVotesDownPercentage: Float = 50f,
    @Json(name = "asset_platform_id") val assetPlatformId: String?,
    val platforms: Map<String, String> = emptyMap(),
    @Json(name = "public_notice") val publicNotice: String? = null,
    @Json(name = "additional_notices") val additionalNotices: List<String> = emptyList(),
)


data class MarketData(
    @Json(name = "current_price") val currentPrice: Map<String, Double> = emptyMap(),
    val roi: Roi? = null,
    val ath: Map<String, Double> = emptyMap(),
    @Json(name = "ath_change_percentage") val athChangePercentage: Map<String, Double> = emptyMap(),
    @Json(name = "ath_date") val athDate: Map<String, String> = emptyMap(),
    val atl: Map<String, Double> = emptyMap(),
    @Json(name = "atl_change_percentage") val atlChangePercentage: Map<String, Double> = emptyMap(),
    @Json(name = "atl_date") val atlDate: Map<String, String> = emptyMap(),
    @Json(name = "market_cap") val marketCap: Map<String, Double> = emptyMap(),
    @Json(name = "market_cap_rank") val marketCapRank: Long = 0,
    @Json(name = "total_volume") val totalVolume: Map<String, Double> = emptyMap(),
    @Json(name = "high_24h") val high24h: Map<String, Double> = emptyMap(),
    @Json(name = "low_24h") val low24h: Map<String, Double> = emptyMap(),
    @Json(name = "price_change_24h") val priceChange24h: Double = 0.0,
    @Json(name = "price_change_percentage_24h") val priceChangePercentage24h: Double = 0.0,
    @Json(name = "price_change_percentage_7d") val priceChangePercentage7d: Double = 0.0,
    @Json(name = "price_change_percentage_14d") val priceChangePercentage14d: Double = 0.0,
    @Json(name = "price_change_percentage_30d") val priceChangePercentage30d: Double = 0.0,
    @Json(name = "price_change_percentage_60d") val priceChangePercentage60d: Double = 0.0,
    @Json(name = "price_change_percentage_200d") val priceChangePercentage200d: Double = 0.0,
    @Json(name = "price_change_percentage_1y") val priceChangePercentage1y: Double = 0.0,
    @Json(name = "market_cap_change_24h") val marketCapChange24h: Double = 0.0,
    @Json(name = "market_cap_change_percentage_24h") val marketCapChangePercentage24h: Double = 0.0,
    @Json(name = "price_change_24h_in_currency") val priceChange24hInCurrency: Map<String, Double> = emptyMap(),
    @Json(name = "price_change_percentage_1h_in_currency") val priceChangePercentage1hInCurrency: Map<String, Double> = emptyMap(),
    @Json(name = "price_change_percentage_24h_in_currency") val priceChangePercentage24hInCurrency: Map<String, Double> = emptyMap(),
    @Json(name = "price_change_percentage_7d_in_currency") val priceChangePercentage7dInCurrency: Map<String, Double> = emptyMap(),
    @Json(name = "price_change_percentage_14d_in_currency") val priceChangePercentage14dInCurrency: Map<String, Double> = emptyMap(),
    @Json(name = "price_change_percentage_30d_in_currency") val priceChangePercentage30dInCurrency: Map<String, Double> = emptyMap(),
    @Json(name = "price_change_percentage_60d_in_currency") val priceChangePercentage60dInCurrency: Map<String, Double> = emptyMap(),
    @Json(name = "price_change_percentage_200d_in_currency") val priceChangePercentage200dInCurrency: Map<String, Double> = emptyMap(),
    @Json(name = "price_change_percentage_1y_in_currency") val priceChangePercentage1yInCurrency: Map<String, Double> = emptyMap(),
    @Json(name = "market_cap_change_24h_in_currency") val marketCapChange24hInCurrency: Map<String, Double> = emptyMap(),
    @Json(name = "market_cap_change_percentage_24h_in_currency") val marketCapChangePercentage24hInCurrency: Map<String, Double> = emptyMap(),
    @Json(name = "total_supply") val totalSupply: Double? = null,
    @Json(name = "circulating_supply") val circulatingSupply: Double = 0.0,
    @Json(name = "last_updated") val lastUpdated: String? = null
)


data class Image(
    val thumb: String,
    val small: String,
    val large: String? = null,
)
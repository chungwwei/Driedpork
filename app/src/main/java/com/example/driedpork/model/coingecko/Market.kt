package com.example.driedpork.model.coingecko

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Market(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String? = null,
    @Json(name="current_price")
    val currentPrice: Double = 0.0,
    @Json(name="market_cap")
    val marketCap: Double = 0.0,
    @Json(name="market_cap_rank")
    val marketCapRank: Long = 0,
    @Json(name="total_volume")
    val totalVolume: Double = 0.0,
    @Json(name="high_24h")
    val high24h: Double = 0.0,
    @Json(name="low_24h")
    val low24h: Double = 0.0,
    @Json(name="price_change_24h")
    val priceChange24h: Double = 0.0,
    @Json(name="price_change_percentage_24h")
    val priceChangePercentage24h: Double = 0.0,
    @Json(name=" market_cap_change_24h")
    val marketCapChange24h: Double = 0.0,
    @Json(name="market_cap_change_percentage_24h")
    val marketCapChangePercentage24h: Double = 0.0,
    @Json(name="circulating_supply")
    val circulatingSupply: Double = 0.0,
    @Json(name="total_supply")
    val totalSupply: Double? = null,
    val ath: Double = 0.0,
    val atl: Double = 0.0,
    @Json(name="atl_change_percentage")
    val atlChangePercentage: Double = 0.0,
    @Json(name="ath_change_percentage")
    val athChangePercentage: Double = 0.0,
    @Json(name="atl_date")
    val atlDate: String? = null,
    @Json(name="ath_date")
    val athDate: String? = null,
    val roi: Roi? = null,
    @Json(name="last_updated")
    val lastUpdated: String? = null,
    @Json(name="sparkline_in_7d")
    val sparklineIn7d: SparklineIn7d? = null,
    @Json(name="price_change_percentage_1h_in_currency")
    val priceChangePercentage1hInCurrency: Double? = 0.0,
    @Json(name="fully_diluted_valuation")
    val fullyDilutedValuation: Double?,
    @Json(name="max_supply")
    val maxSupply: Double? = 0.0,
) : Parcelable


@Parcelize
data class Roi(
    val times: Double,
    val currency: String,
    val percentage: Double
): Parcelable

@Parcelize
data class SparklineIn7d(
    val price: List<Double>
): Parcelable

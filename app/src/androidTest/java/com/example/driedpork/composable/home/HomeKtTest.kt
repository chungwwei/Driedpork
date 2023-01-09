package com.example.driedpork.composable.home

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.driedpork.model.coingecko.Market
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test

class HomeKtTest {

    @get:Rule
    val rule = createComposeRule()

    private val m = Market(
        id = "bitcoin",
        symbol = "btc",
        name = "Bitcoin",
        image = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
        currentPrice = 1.0,
        priceChangePercentage24h = 1.0,
        marketCap = 1.0,
        marketCapRank = 1,
        totalVolume = 1.0,
        high24h = 1.0,
        low24h = 1.0,
        circulatingSupply = 1.0,
        totalSupply = 1.0,
        ath = 1.0,
        athChangePercentage = 1.0,
        athDate = "2021-05-12T10:54:46.763Z",
        atl = 1.0,
        atlChangePercentage = 1.0,
        atlDate = "2013-07-06T00:00:00.000Z",
        lastUpdated = "2021-05-12T10:54:46.763Z",
        priceChange24h = 1.0,
        priceChangePercentage1hInCurrency = 1.0,
        marketCapChange24h = 1.0,
        marketCapChangePercentage24h = 1.0,
        roi = null,
        sparklineIn7d = null,
        fullyDilutedValuation = null,
    )

    @Test
    fun cryptoItemExists() {
        // set content
        rule.setContent {
            CryptoItem(m = m, onItemClick = {})
        }
        // node exists
        rule.onNodeWithText("Bitcoin").assertExists()
    }

    @Test
    fun cryptoItemClickable() {
        rule.setContent {
            CryptoItem(m = m, onItemClick = {})
        }

        // clickable
        rule.onNodeWithText("Bitcoin").assertHasClickAction()
    }
}
package com.example.driedpork.screen

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.example.driedpork.MainScreenView
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test

class NavigationKtTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun bottomNavigationBarExists() {

        rule.setContent {
            BottomNavigationBar(navController = rememberNavController())
        }

        rule.onNodeWithText("Home").assertExists()
        rule.onNodeWithText("Search").assertExists()
        rule.onNodeWithText("Convert").assertExists()

    }

    @Test
    fun bottomNavigationBarItemsClickable() {

        rule.setContent {
            BottomNavigationBar(navController = rememberNavController())
        }

        rule.onNodeWithText("Home").assertHasClickAction()
        rule.onNodeWithText("Search").assertHasClickAction()
        rule.onNodeWithText("Convert").assertHasClickAction()

    }
}
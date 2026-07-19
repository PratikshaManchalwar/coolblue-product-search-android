package com.example.coolblueproductsearch

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.coolblueproductsearch.domain.model.Product
import com.example.coolblueproductsearch.domain.repository.ProductRepository
import com.example.coolblueproductsearch.presentation.search.SearchScreen
import com.example.coolblueproductsearch.presentation.search.SearchViewModel
import com.example.coolblueproductsearch.ui.theme.CoolblueProductSearchTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun app_launches_and_shows_search_bar() {
        val repository = object : ProductRepository {
            override suspend fun searchProducts(query: String): Result<List<Product>> {
                return Result.success(emptyList())
            }
        }

        val viewModel = SearchViewModel(repository)

        composeTestRule.setContent {
            CoolblueProductSearchTheme {
                SearchScreen(viewModel = viewModel)
            }
        }

        composeTestRule
            .onNodeWithText("What are you looking for?", ignoreCase = true)
            .assertIsDisplayed()
    }
}
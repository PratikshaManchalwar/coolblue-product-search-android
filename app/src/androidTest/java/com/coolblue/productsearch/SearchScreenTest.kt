package com.coolblue.productsearch

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.coolblue.productsearch.domain.model.Product
import com.coolblue.productsearch.domain.repository.ProductRepository
import com.coolblue.productsearch.presentation.search.SearchScreen
import com.coolblue.productsearch.presentation.search.SearchViewModel
import com.coolblue.productsearch.ui.theme.ProductSearchTheme
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
            ProductSearchTheme {
                SearchScreen(viewModel = viewModel)
            }
        }

        composeTestRule
            .onNodeWithText("What are you looking for?", ignoreCase = true)
            .assertIsDisplayed()
    }

    @Test
    fun searching_for_product_displays_result() {
        val mockProduct = Product(
            id = 1,
            name = "Apple iPhone X",
            priceLabel = "1.279,-",
            imageUrl = "",
            rating = 9.2,
            reviewCount = 200,
            usps = listOf("256 GB"),
            hasNextDayDelivery = true
        )

        val repository = object : ProductRepository {
            override suspend fun searchProducts(query: String): Result<List<Product>> {
                return Result.success(listOf(mockProduct))
            }
        }

        val viewModel = SearchViewModel(repository)
        composeTestRule.setContent {
            ProductSearchTheme {
                SearchScreen(viewModel = viewModel)
            }
        }

        composeTestRule
            .onNodeWithText("What are you looking for?", ignoreCase = true)
            .performTextInput("iPhone")

        composeTestRule
            .onNodeWithText("Apple iPhone X")
            .assertIsDisplayed()
    }
}
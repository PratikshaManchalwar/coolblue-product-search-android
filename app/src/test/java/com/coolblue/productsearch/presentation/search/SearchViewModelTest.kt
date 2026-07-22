package com.coolblue.productsearch.presentation.search

import app.cash.turbine.test
import com.coolblue.productsearch.domain.model.Product
import com.coolblue.productsearch.domain.repository.ProductRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val repository: ProductRepository = mockk()
    private lateinit var viewModel: SearchViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SearchViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when query is empty, initial load returns all products`() = runTest {
        val mockList = listOf(Product(1, "iPhone", "€100", "", 9.0, 10, emptyList(), true))
        coEvery { repository.searchProducts("") } returns Result.success(mockList)

        viewModel.uiState.test {
            advanceTimeBy(400)

            val state = expectMostRecentItem()
            assertTrue(state is SearchUiState.Success)
            Assert.assertEquals(1, (state as SearchUiState.Success).products.size)
        }
    }

    @Test
    fun `when repository fails, uiState emits Error`() = runTest {
        coEvery { repository.searchProducts(any()) } returns Result.failure(Exception("Error"))

        viewModel.onSearchQueryChanged("Apple")

        viewModel.uiState.test {
            advanceTimeBy(400)
            val state = expectMostRecentItem()
            assertTrue(state is SearchUiState.Error)
        }
    }
}
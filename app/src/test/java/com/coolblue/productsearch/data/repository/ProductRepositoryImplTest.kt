package com.coolblue.productsearch.data.repository

import android.content.Context
import android.content.res.AssetManager
import com.coolblue.productsearch.domain.repository.ProductRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream

class ProductRepositoryImplTest {

    private lateinit var repository: ProductRepository
    private val context: Context = mockk()
    private val assetManager: AssetManager = mockk()

    private val mockJson = """
        {
            "products": [
                {
                    "productId": 1,
                    "productName": "Apple iPhone X",
                    "reviewInformation": {
                        "reviewSummary": { "reviewAverage": 9.2, "reviewCount": 200 }
                    },
                    "salesPriceIncVat": 1279.0,
                    "productImage": "url1",
                    "nextDayDelivery": true
                },
                {
                    "productId": 2,
                    "productName": "Samsung Galaxy",
                    "reviewInformation": {
                        "reviewSummary": { "reviewAverage": 8.5, "reviewCount": 100 }
                    },
                    "salesPriceIncVat": 800.0,
                    "productImage": "url2",
                    "nextDayDelivery": false
                }
            ],
            "currentPage": 1,
            "pageSize": 20,
            "totalResults": 2,
            "pageCount": 1
        }
    """.trimIndent()

    @Before
    fun setup() {
        every { context.assets } returns assetManager
        every { assetManager.open("products.json") } returns ByteArrayInputStream(mockJson.toByteArray())

        repository = ProductRepositoryImpl(assetManager)
    }

    @Test
    fun `searchProducts with empty query returns all products`() = runTest {
        val result = repository.searchProducts("")

        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
    }

    @Test
    fun `searchProducts with specific query returns filtered products`() = runTest {
        val result = repository.searchProducts("iPhone")

        assertTrue(result.isSuccess)
        val products = result.getOrNull()
        assertEquals(1, products?.size)
        assertEquals("Apple iPhone X", products?.first()?.name)
    }

    @Test
    fun `searchProducts verifies that caching works and assets are only opened once`() = runTest {
        repository.searchProducts("a")
        repository.searchProducts("b")

        verify(exactly = 1) { assetManager.open("products.json") }
    }

    @Test
    fun `searchProducts returns failure when json is invalid`() = runTest {
        every { assetManager.open("products.json") } returns ByteArrayInputStream("invalid".toByteArray())

        val result = repository.searchProducts("iPhone")

        assertTrue(result.isFailure)
    }
}

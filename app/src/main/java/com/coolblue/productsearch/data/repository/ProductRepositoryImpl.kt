package com.coolblue.productsearch.data.repository

import com.coolblue.productsearch.data.mapper.toDomain
import com.coolblue.productsearch.data.model.ProductDto
import com.coolblue.productsearch.data.model.SearchResponseDto
import com.coolblue.productsearch.domain.model.Product
import com.coolblue.productsearch.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class ProductRepositoryImpl(
    private val assetManager: android.content.res.AssetManager,
    private val json: Json = Json { ignoreUnknownKeys = true }
) : ProductRepository {
    private var cachedProducts: List<ProductDto>? = null

    private suspend fun getRawProducts(): List<ProductDto> = withContext(Dispatchers.IO) {
        cachedProducts ?: run {
            val jsonString = assetManager.open("products.json").bufferedReader().use { it.readText() }
            val responseDto = json.decodeFromString<SearchResponseDto>(jsonString)
            responseDto.products.also {
                cachedProducts = it
            }
        }
    }
    override suspend fun searchProducts(query: String): Result<List<Product>> = withContext(Dispatchers.IO) {
        try {
            delay(500)

            val allProducts = getRawProducts()

            val filteredProducts = if (query.isBlank()) {
                allProducts
            } else {
                allProducts.filter { product ->
                    val matchesName = product.productName.contains(query, ignoreCase = true)

                    val matchesUsps = product.USPs?.any { usp ->
                        usp.contains(query, ignoreCase = true)
                    } == true

                    matchesName || matchesUsps
                }
            }

            Result.success(filteredProducts.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
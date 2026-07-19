package com.example.coolblueproductsearch.data.repository

import android.content.Context
import com.example.coolblueproductsearch.data.mapper.toDomain
import com.example.coolblueproductsearch.data.model.SearchResponseDto
import com.example.coolblueproductsearch.domain.model.Product
import com.example.coolblueproductsearch.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class ProductRepositoryImpl(
    private val context: Context,
    private val json: Json = Json { ignoreUnknownKeys = true }
) : ProductRepository {

    override suspend fun searchProducts(query: String): Result<List<Product>> = withContext(Dispatchers.IO) {
        try {
            delay(800)
            val jsonString = context.assets.open("products.json").bufferedReader().use { it.readText() }
            val responseDto = json.decodeFromString<SearchResponseDto>(jsonString)

            val filteredProducts = if (query.isBlank()) {
                responseDto.products
            } else {
                responseDto.products.filter {
                    it.productName.contains(query, ignoreCase = true)
                }
            }

            Result.success(filteredProducts.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
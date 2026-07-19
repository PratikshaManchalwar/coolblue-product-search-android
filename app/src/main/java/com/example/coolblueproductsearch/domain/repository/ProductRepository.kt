package com.example.coolblueproductsearch.domain.repository

import com.example.coolblueproductsearch.domain.model.Product

interface ProductRepository {
    suspend fun searchProducts(query: String): Result<List<Product>>
}
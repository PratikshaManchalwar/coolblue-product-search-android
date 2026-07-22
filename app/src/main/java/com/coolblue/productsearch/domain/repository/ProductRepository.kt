package com.coolblue.productsearch.domain.repository

import com.coolblue.productsearch.domain.model.Product

interface ProductRepository {
    suspend fun searchProducts(query: String): Result<List<Product>>
}
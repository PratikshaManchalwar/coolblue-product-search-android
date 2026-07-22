package com.coolblue.productsearch.domain.model

data class Product(
    val id: Int,
    val name: String,
    val priceLabel: String,
    val imageUrl: String,
    val rating: Double,
    val reviewCount: Int,
    val usps: List<String>,
    val hasNextDayDelivery: Boolean
)

package com.example.coolblueproductsearch.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    val products: List<ProductDto>,
    val currentPage: Int,
    val pageSize: Int,
    val totalResults: Int,
    val pageCount: Int
)

@Serializable
data class ProductDto(
    val productId: Int,
    val productName: String,
    val reviewInformation: ReviewInformationDto,
    val USPs: List<String>? = emptyList(),
    val salesPriceIncVat: Double,
    val productImage: String,
    val nextDayDelivery: Boolean = false
)

@Serializable
data class ReviewInformationDto(
    val reviewSummary: ReviewSummaryDto
)

@Serializable
data class ReviewSummaryDto(
    val reviewAverage: Double,
    val reviewCount: Int
)
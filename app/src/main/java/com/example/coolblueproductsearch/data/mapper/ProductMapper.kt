package com.example.coolblueproductsearch.data.mapper

import com.example.coolblueproductsearch.data.model.ProductDto
import com.example.coolblueproductsearch.domain.model.Product

fun ProductDto.toDomain(): Product {
    val formattedPrice = String.format("%,.0f", this.salesPriceIncVat)
        .replace(",", ".")
    return Product(
        id = this.productId,
        name = this.productName,
        priceLabel = "$formattedPrice,-",
        imageUrl = this.productImage,
        rating = this.reviewInformation.reviewSummary.reviewAverage,
        reviewCount = this.reviewInformation.reviewSummary.reviewCount,
        usps = this.USPs ?: emptyList(),
        hasNextDayDelivery = this.nextDayDelivery
    )
}
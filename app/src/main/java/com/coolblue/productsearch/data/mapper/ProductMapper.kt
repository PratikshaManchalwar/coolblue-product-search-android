package com.coolblue.productsearch.data.mapper

import com.coolblue.productsearch.data.model.ProductDto
import com.coolblue.productsearch.domain.model.Product
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun ProductDto.toDomain(): Product {
    val symbols = DecimalFormatSymbols(Locale.US).apply {
        groupingSeparator = '.'
    }

    val formatter = DecimalFormat("#,###", symbols)
    val formattedPrice = formatter.format(this.salesPriceIncVat)

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
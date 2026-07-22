package com.coolblue.productsearch.data.mapper

import com.coolblue.productsearch.data.model.ProductDto
import com.coolblue.productsearch.data.model.ReviewInformationDto
import com.coolblue.productsearch.data.model.ReviewSummaryDto
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductMapperTest {

    @Test
    fun `toDomain maps DTO fields correctly and formats price`() {
        val dto = ProductDto(
            productId = 123,
            productName = "Test Product",
            reviewInformation = ReviewInformationDto(
                reviewSummary = ReviewSummaryDto(9.5, 10)
            ),
            salesPriceIncVat = 1299.0,
            productImage = "url",
            USPs = listOf("USP1"),
            nextDayDelivery = true
        )

        val domain = dto.toDomain()

        assertEquals(123, domain.id)
        assertEquals("Test Product", domain.name)
        assertEquals("1.299,-", domain.priceLabel)
        assertEquals(9.5, domain.rating, 0.0)
        assertEquals(10, domain.reviewCount)
        assertEquals(true, domain.hasNextDayDelivery)
    }
}
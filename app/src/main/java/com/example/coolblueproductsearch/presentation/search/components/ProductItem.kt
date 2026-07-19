package com.example.coolblueproductsearch.presentation.search.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.coolblueproductsearch.R
import com.example.coolblueproductsearch.domain.model.Product

@Composable
fun ProductItem(product: Product, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF00A1E4)
                )
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.rating_value, product.rating),
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    val context = LocalContext.current
                    val reviewsText = context.resources.getQuantityString(
                        R.plurals.review_count,
                        product.reviewCount,
                        product.reviewCount
                    )

                    Text(
                        text = reviewsText,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                product.usps.take(2).forEach { usp ->
                    Text(
                        text = "• $usp",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Text(
                    text = product.priceLabel,
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                if (product.hasNextDayDelivery) {
                    Text(
                        text = stringResource(R.string.next_day_delivery),
                        color = Color(0xFF008800),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF2F2F2)
@Composable
fun PreviewProductItem() {
    val mockProduct = Product(
        id = 1,
        name = "Apple iPhone X 256GB Silver",
        priceLabel = "1279",
        imageUrl = "",
        rating = 9.2,
        reviewCount = 209,
        usps = listOf("256 GB", "5,8 inch Retina HD scherm"),
        hasNextDayDelivery = true
    )

    MaterialTheme {
        ProductItem(product = mockProduct)
    }
}
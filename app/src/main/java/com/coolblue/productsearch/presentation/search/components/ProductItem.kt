package com.coolblue.productsearch.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.coolblue.productsearch.R
import com.coolblue.productsearch.domain.model.Product
import com.coolblue.productsearch.ui.theme.DeliveryGreen
import com.coolblue.productsearch.ui.theme.Dimens
import com.coolblue.productsearch.ui.theme.White

@Composable
fun ProductItem(product: Product, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.SpacingMedium, vertical = Dimens.SpacingSmall),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.CardElevation)
    ) {
        Row(modifier = Modifier.padding(Dimens.SpacingMedium)) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = stringResource(
                    R.string.product_image_description,
                    product.name
                ),
                modifier = Modifier.size(Dimens.ProductImageSize),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(Dimens.SpacingMedium))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Dimens.SpacingExtraSmall)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.rating_value, product.rating),
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(modifier = Modifier.width(Dimens.SpacingExtraSmall))

                    val context = LocalContext.current
                    val reviewsText = context.resources.getQuantityString(
                        R.plurals.review_count,
                        product.reviewCount,
                        product.reviewCount
                    )

                    Text(
                        text = reviewsText,
                        style = MaterialTheme.typography.bodySmall
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
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                if (product.hasNextDayDelivery) {
                    Text(
                        text = stringResource(R.string.next_day_delivery),
                        color = DeliveryGreen,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
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
package com.coolblue.productsearch.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coolblue.productsearch.R
import com.coolblue.productsearch.domain.model.Product
import com.coolblue.productsearch.presentation.search.components.ProductItem
import com.coolblue.productsearch.presentation.search.components.SearchHeader
import com.coolblue.productsearch.ui.theme.Dimens
import com.coolblue.productsearch.ui.theme.ProductSearchTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val query by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            SearchHeader(
                query = query,
                onQueryChange = { newQuery ->
                    viewModel.onSearchQueryChanged(newQuery)
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            when (val state = uiState) {
                is SearchUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                is SearchUiState.Success -> {
                    val resultCount = state.products.size
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = Dimens.SpacingMedium)
                    ) {
                        item {
                            val headerText = if (query.isEmpty()) {
                                stringResource(R.string.recommended_for_you)
                            } else {
                                context.resources.getQuantityString(
                                    R.plurals.found_results,
                                    resultCount,
                                    resultCount
                                )
                            }

                            Text(
                                text = headerText,
                                modifier = Modifier.padding(Dimens.SpacingMedium),
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.DarkGray
                            )
                        }

                        items(state.products, key = { it.id }) { product ->
                            ProductItem(product = product)
                        }
                    }
                }
                is SearchUiState.Empty -> {
                    InfoMessage(stringResource(R.string.empty_message, query))
                }
                is SearchUiState.Error -> {
                    InfoMessage(text = stringResource(R.string.connection_error), isError = true)
                }
                is SearchUiState.Idle -> {
                    InfoMessage(stringResource(R.string.idle_message))
                }
            }
        }
    }
}

@Composable
fun InfoMessage(text: String, isError: Boolean = false) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.SpacingExtraLarge),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = if (isError) MaterialTheme.colorScheme.error else Color.Gray
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSearchScreenSuccess() {
    val mockProducts = listOf(
        Product(1, "iPhone X", "1279", "", 9.2, 200, emptyList(), true),
        Product(2, "iPad Air", "600", "", 8.5, 50, emptyList(), false)
    )

    ProductSearchTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    Text(
                        text = "Found 2 results",
                        modifier = Modifier.padding(Dimens.SpacingMedium),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.DarkGray
                    )
                }
                items(mockProducts) { product ->
                    ProductItem(product = product)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchScreenLoading() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchScreenEmpty() {
    MaterialTheme {
        InfoMessage(text = stringResource(R.string.idle_message))
    }
}

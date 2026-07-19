package com.example.coolblueproductsearch.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coolblueproductsearch.R
import com.example.coolblueproductsearch.domain.model.Product
import com.example.coolblueproductsearch.presentation.search.components.ProductItem
import com.example.coolblueproductsearch.presentation.search.components.SearchHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var localQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            SearchHeader(
                query = localQuery,
                onQueryChange = { newQuery ->
                    localQuery = newQuery
                    viewModel.onSearchQueryChanged(newQuery)
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF2F2F2))
        ) {
            when (val state = uiState) {
                is SearchUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color(0xFFFF6900)
                    )
                }
                is SearchUiState.Success -> {
                    val resultCount = state.products.size
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        item {
                            val headerText = if (localQuery.isEmpty()) {
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
                                modifier = Modifier.padding(16.dp),
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
                    InfoMessage(stringResource(R.string.empty_message, localQuery))
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
            .padding(32.dp),
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

    MaterialTheme {
        Box(modifier = Modifier.background(Color(0xFFF2F2F2))) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    Text(
                        text = "Found 2 results",
                        modifier = Modifier.padding(16.dp),
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
            CircularProgressIndicator(color = Color(0xFFFF6900))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchScreenEmpty() {
    MaterialTheme {
        InfoMessage(text = "We couldn't find anything for \"Samsung\".")
    }
}

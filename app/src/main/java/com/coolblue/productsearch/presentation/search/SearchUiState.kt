package com.coolblue.productsearch.presentation.search

import com.coolblue.productsearch.domain.model.Product

sealed interface SearchUiState {
    data object Idle : SearchUiState
    data object Loading : SearchUiState
    data class Success(val products: List<Product>) : SearchUiState
    data class Error(val messageResId: Int) : SearchUiState
    data object Empty : SearchUiState
}
package com.example.coolblueproductsearch.presentation.search

import com.example.coolblueproductsearch.domain.model.Product

sealed interface SearchUiState {
    data object Idle : SearchUiState
    data object Loading : SearchUiState
    data class Success(val products: List<Product>) : SearchUiState
    data class Error(val messageResId: Int) : SearchUiState
    data object Empty : SearchUiState
}
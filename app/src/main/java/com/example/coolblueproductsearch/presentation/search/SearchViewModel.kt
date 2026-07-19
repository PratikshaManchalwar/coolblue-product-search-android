package com.example.coolblueproductsearch.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolblueproductsearch.domain.repository.ProductRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.example.coolblueproductsearch.R

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    init {
        observeSearchQuery()
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .collect { query ->
                    performSearch(query)
                }
        }
    }

    private suspend fun performSearch(query: String) {
        _uiState.value = SearchUiState.Loading

        repository.searchProducts(query)
            .onSuccess { products ->
                _uiState.value = if (products.isEmpty()) {
                    SearchUiState.Empty
                } else {
                    SearchUiState.Success(products)
                }
            }
            .onFailure {
                _uiState.value = SearchUiState.Error(R.string.error_message)
            }
    }
}
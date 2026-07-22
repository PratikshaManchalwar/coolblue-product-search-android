package com.coolblue.productsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coolblue.productsearch.data.repository.ProductRepositoryImpl
import com.coolblue.productsearch.presentation.search.SearchScreen
import com.coolblue.productsearch.presentation.search.SearchViewModel
import com.coolblue.productsearch.ui.theme.ProductSearchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: SearchViewModel by viewModels {
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val repository = ProductRepositoryImpl(assets)
                    return SearchViewModel(repository) as T
                }
            }
        }

        enableEdgeToEdge()

        setContent {
            ProductSearchTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SearchScreen(viewModel = viewModel)
                }
            }
        }
    }
}
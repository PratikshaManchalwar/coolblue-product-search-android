package com.coolblue.productsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.coolblue.productsearch.data.repository.ProductRepositoryImpl
import com.coolblue.productsearch.presentation.search.SearchScreen
import com.coolblue.productsearch.presentation.search.SearchViewModel
import com.coolblue.productsearch.ui.theme.ProductSearchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = ProductRepositoryImpl(applicationContext)
        val viewModel = SearchViewModel(repository)

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
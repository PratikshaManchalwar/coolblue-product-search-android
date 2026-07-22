package com.coolblue.productsearch.presentation.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.coolblue.productsearch.R
import com.coolblue.productsearch.ui.theme.Dimens
import com.coolblue.productsearch.ui.theme.LightCharcoal
import com.coolblue.productsearch.ui.theme.LightGrey
import com.coolblue.productsearch.ui.theme.ProductSearchTheme
import com.coolblue.productsearch.ui.theme.White

@Composable
fun SearchHeader(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    Surface(
        color = MaterialTheme.colorScheme.primary,
        shadowElevation = Dimens.SearchHeaderElevation,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(Dimens.SpacingMedium)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.SpacingMedium),
                placeholder = {
                    Text(stringResource(R.string.search_hint), color = LightCharcoal)
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = stringResource(R.string.search_icon),
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = {
                            onQueryChange("")
                            focusManager.clearFocus()
                        }) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = stringResource(R.string.clear_search),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                    }
                ),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = White,
                    unfocusedContainerColor = White,
                    focusedBorderColor = LightGrey,
                    unfocusedBorderColor = LightGrey,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SearchHeaderPreviewWithEmptyState() {
    ProductSearchTheme {
        SearchHeader(
            query = "",
            onQueryChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchHeaderPreviewWithText() {
    ProductSearchTheme {
        SearchHeader(
            query = "iPhone 15",
            onQueryChange = {}
        )
    }
}
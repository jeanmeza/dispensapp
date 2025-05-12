package com.jeanmeza.dispensapp.ui.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanmeza.dispensapp.R
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme

@Composable
fun DispensAppSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val animatedPadding by animateDpAsState(
        targetValue = dimensionResource(if (expanded) R.dimen.p_none else R.dimen.p_md),
        label = "searchBarPadding"
    )
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = Modifier.fillMaxSize(),
                placeholder = { Text(text = stringResource(R.string.search_your_items)) },
                leadingIcon = {
                    if (expanded || query.isNotEmpty()) {
                        IconButton(
                            onClick = { expanded = false },
                            content = {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = null,
                                )
                            }
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null,
                        )
                    }
                },
            )
        },
        expanded = expanded,
        onExpandedChange = {},
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = animatedPadding),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DispensAppSearchBarPreview() {
    DispensAppTheme {
        DispensAppSearchBar(
            query = "",
            onQueryChange = {},
            onSearch = {},
        )
    }
}



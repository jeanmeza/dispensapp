package com.jeanmeza.dispensapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.jeanmeza.dispensapp.R
import com.jeanmeza.dispensapp.data.model.Item

@Composable
fun ItemCardList(
    items: List<Item>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.p_md))
            .background(color = MaterialTheme.colorScheme.surface),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.p_md)),
        ) {
            items(items = items, key = { it.id }) {
                ItemCard(
                    item = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { onItemClick(it.id) })
                        .height(80.dp)
                )
            }
        }
    }
}
package com.jeanmeza.dispensapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jeanmeza.dispensapp.R
import com.jeanmeza.dispensapp.data.model.Item
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme
import com.jeanmeza.dispensapp.ui.theme.Shapes
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ItemCard(item: Item, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = Shapes.small,
        colors = CardDefaults.cardColors()
            .copy(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.p_sm)),
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(color = MaterialTheme.colorScheme.surfaceContainerHighest)
                    .width(80.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.p_sm)),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        vertical = dimensionResource(R.dimen.p_sm),
                        horizontal = dimensionResource(R.dimen.p_md),
                    )
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box() {
                        Text(
                            text = "${item.quantity} ${item.measureUnit}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    if (item.expiryDate != null) {
                        Box() {
                            Text(
                                text = "Expires ${formatDate(item.expiryDate)}",
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun formatDate(date: LocalDate): String {
    return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
}

@Preview
@Composable
fun ItemCardPreviewWithExpiryDate() {
    DispensAppTheme(dynamicColor = false) {
        ItemCard(
            item = Item(
                id = 1,
                categoryId = null,
                name = "Pasta",
                quantity = 12,
                measureUnit = "Kg",
                expiryDate = LocalDate.now().plusWeeks(1)
            ),
            modifier = Modifier.height(80.dp)
        )
    }
}


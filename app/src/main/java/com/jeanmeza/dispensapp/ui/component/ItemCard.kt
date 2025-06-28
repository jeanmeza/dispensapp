package com.jeanmeza.dispensapp.ui.component

import androidx.compose.foundation.Image
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.jeanmeza.dispensapp.R
import com.jeanmeza.dispensapp.data.model.Item
import com.jeanmeza.dispensapp.ui.formattedDate
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme
import com.jeanmeza.dispensapp.ui.theme.Shapes
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.days

@Composable
fun ItemCard(item: Item, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier,
        shape = Shapes.small,
        colors = CardDefaults.cardColors()
            .copy(
                containerColor = itemCardContainerColor(item.expiryDate),
                contentColor = Color(0xFF1B1C19)  // It's the same as onSurfaceLight
            ),
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
            ) {
                if (item.imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(item.imageUri),
                        contentDescription = stringResource(R.string.item_image),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
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
                    Box {
                        Text(
                            text = "${item.quantity} ${item.measureUnit}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    if (item.expiryDate != null) {
                        val formattedDate = formattedDate(item.expiryDate)
                        Box {
                            Text(
                                text = "Expires $formattedDate",
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun itemCardContainerColor(expiryDate: Instant?): Color {
    if (expiryDate == null) {
        return Color(0xFFE4FFE0)
    }
    val today = Clock.System.now()
    val sevenDaysFromNow = today.plus(7.days)
    if (expiryDate < today) {
        return Color(0xFFFDE9EB)
    }
    if (expiryDate < sevenDaysFromNow) {
        return Color(0xFFFFF2E5)
    }
    return Color(0xFFE4FFE0)
}

@Preview
@Composable
fun ItemCardPreviewExpired() {
    DispensAppTheme(dynamicColor = false) {
        ItemCard(
            item = Item(
                id = 1,
                name = "Pasta",
                quantity = 12,
                measureUnit = "Kg",
                expiryDate = Clock.System.now().minus(1.days),
                categories = emptyList(),
                imageUri = null,
            ),
            modifier = Modifier.height(80.dp)
        )
    }
}

@Preview
@Composable
fun ItemCardPreviewAboutToExpire() {
    DispensAppTheme(dynamicColor = false) {
        ItemCard(
            item = Item(
                id = 1,
                name = "Pasta",
                quantity = 12,
                measureUnit = "Kg",
                expiryDate = Clock.System.now().plus(1.days),
                categories = emptyList(),
                imageUri = null,
            ),
            modifier = Modifier.height(80.dp)
        )
    }
}

@Preview
@Composable
fun ItemCardPreview() {
    DispensAppTheme(dynamicColor = false) {
        ItemCard(
            item = Item(
                id = 1,
                name = "Pasta",
                quantity = 12,
                measureUnit = "Kg",
                expiryDate = Clock.System.now().plus(7.days),
                categories = emptyList(),
                imageUri = null,
            ),
            modifier = Modifier.height(80.dp)
        )
    }
}


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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.jeanmeza.dispensapp.R
import com.jeanmeza.dispensapp.data.model.Item
import com.jeanmeza.dispensapp.ui.formattedDate
import com.jeanmeza.dispensapp.ui.theme.DispensAppIcons
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme
import com.jeanmeza.dispensapp.ui.theme.Shapes
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Instant

@Composable
fun ItemCard(item: Item, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = Shapes.small,
        colors = CardDefaults.cardColors()
            .copy(containerColor = Color.Transparent),

        ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.p_sm)),
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .width(80.dp)
                    .fillMaxHeight()
                    .background(color = MaterialTheme.colorScheme.surfaceContainerHighest)
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
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(4f)
                    )
                    Text(
                        text = "${item.quantity} ${item.measureUnit}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = dimensionResource(id = R.dimen.p_xs))
                    )
                }
                if (item.expiryDate != null) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Expires ${formattedDate(item.expiryDate)}",
                            style = MaterialTheme.typography.titleMedium,
                        )
                        ExpiryIcon(item.expiryDate)
                    }
                }
            }
        }
    }
}

@Composable
private fun ExpiryIcon(expiryDate: Instant) {
    val today = Clock.System.now()
    val sevenDaysFromNow = today.plus(7.days)
    if (expiryDate < today) {
        return Icon(
            imageVector = DispensAppIcons.DangerousOutlined,
            contentDescription = null,
            tint = Color(0xFFDC3545)  // Crimson
        )
    }
    if (expiryDate < sevenDaysFromNow) {
        return Icon(
            imageVector = DispensAppIcons.WarningAmber,
            contentDescription = null,
            tint = Color(0xFFFFBF00)  // Amber
        )
    }
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
                name = "Pasta With A Very Long Name That Should Be Clipped",
                quantity = 12345,
                measureUnit = "UnitsOfSomethingVeryLong",
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

@Preview
@Composable
fun ItemCardPreviewNoExpiry() {
    DispensAppTheme(dynamicColor = false) {
        ItemCard(
            item = Item(
                id = 1,
                name = "Pasta",
                quantity = 12,
                measureUnit = "Kg",
                expiryDate = null,
                categories = emptyList(),
                imageUri = null,
            ),
            modifier = Modifier.height(80.dp)
        )
    }
}

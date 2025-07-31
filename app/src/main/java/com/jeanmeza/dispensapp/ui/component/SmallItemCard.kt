package com.jeanmeza.dispensapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.jeanmeza.dispensapp.R
import com.jeanmeza.dispensapp.data.model.Item
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days

@Composable
fun SmallItemCard(
    item: Item,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .height(60.dp)
            .padding(dimensionResource(R.dimen.p_sm))
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .width(44.dp)
                    .height(44.dp)
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
            Spacer(Modifier.width(dimensionResource(R.dimen.p_md)))
            Text(
                text = item.name,
                style = MaterialTheme.typography.headlineSmall,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SmallItemCardPreview() {
    DispensAppTheme {
        SmallItemCard(
            item = Item(
                id = 1,
                name = "Pasta",
                quantity = 12,
                measureUnit = "pacchetti",
                expiryDate = Clock.System.now().plus(7.days),
                categories = emptyList(),
                imageUri = null,
            ),
        )
    }
}
package com.jeanmeza.dispensapp.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.jeanmeza.dispensapp.data.model.Category
import com.jeanmeza.dispensapp.ui.theme.DispensAppIcons
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme
import com.jeanmeza.dispensapp.ui.theme.Shapes

@Composable
fun CategoryCard(
    category: Category,
    onEditClick: (Int) -> Unit,
    onOpenClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier.clickable { onOpenClick(category.id) },
        shape = Shapes.small,
        colors = CardDefaults.cardColors()
            .copy(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.p_md)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
            IconButton(onClick = { onEditClick(category.id) }) {
                Icon(
                    imageVector = DispensAppIcons.Edit,
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview
@Composable
fun CategoryCardPreview() {
    DispensAppTheme {
        CategoryCard(
            category = Category(
                id = 1,
                name = "Fruits",
            ),
            onEditClick = {},
            onOpenClick = {},
            modifier = Modifier.height(80.dp)
        )
    }
}

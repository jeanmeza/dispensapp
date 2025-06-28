package com.jeanmeza.dispensapp.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
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
    modifier: Modifier = Modifier,
    selected: Boolean = false,
) {
    val cardColors = if (selected)
        CardDefaults.cardColors()
            .copy(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            )
    else
        CardDefaults.cardColors()
            .copy(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onSurface,
            )

    ElevatedCard(
        modifier = modifier,
        shape = Shapes.small,
        colors = cardColors,
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.p_md)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = DispensAppIcons.LabelOutlined,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.p_md)))
            Text(
                text = category.name,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
            if (selected) {
                Icon(
                    imageVector = DispensAppIcons.Checkbox,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryCardPreview() {
    DispensAppTheme {
        CategoryCard(
            category = Category(
                id = 1,
                name = "Fruits",
            ),
            modifier = Modifier.height(80.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryCardSelectedPreview() {
    DispensAppTheme {
        CategoryCard(
            category = Category(
                id = 1,
                name = "Fruits",
            ),
            modifier = Modifier.height(80.dp),
            selected = true,
        )
    }
}

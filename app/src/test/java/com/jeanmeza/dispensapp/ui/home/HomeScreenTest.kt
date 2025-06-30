package com.jeanmeza.dispensapp.ui.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.jeanmeza.dispensapp.data.model.Item
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme
import kotlinx.datetime.Clock
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `homeScreen displays empty state when no items`() {
        composeTestRule.setContent {
            DispensAppTheme {
                HomeScreen(
                    items = emptyList(),
                    onItemClick = {}
                )
            }
        }
    }

    @Test
    fun `homeScreen displays items when items are provided`() {
        // Arrange
        val items = listOf(
            createItem(1, "Item 1", 2, "kg"),
            createItem(2, "Item 2", 4, "l")
        )
        // Act
        composeTestRule.setContent {
            DispensAppTheme {
                HomeScreen(
                    items = items,
                    onItemClick = {},
                )
            }
        }
        // Assert
        composeTestRule.onNodeWithText("Item 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Item 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("2 kg").assertIsDisplayed()
        composeTestRule.onNodeWithText("4 l").assertIsDisplayed()
    }

    @Test
    fun `homeScreen calls onItemClick when item is clicked`() {
        // Arrange
        val mockOnItemClick: (Int) -> Unit = mock()
        val items = listOf(
            createItem(1, "Item 1", 2, "kg"),
            createItem(1, "Item 2", 2, "l"),
        )
        // Act
        composeTestRule.setContent {
            DispensAppTheme {
                HomeScreen(
                    items = items,
                    onItemClick = mockOnItemClick,
                )
            }
        }
        // Assert
        composeTestRule.onNodeWithText("Item 1").performClick()
        verify(mockOnItemClick).invoke(1)
    }

    private fun createItem(id: Int, name: String, quantity: Int, measureUnit: String) = Item(
        id = id,
        name = name,
        quantity = quantity,
        measureUnit = measureUnit,
        expiryDate = Clock.System.now(),
        categories = emptyList(),
        imageUri = null
    )
}
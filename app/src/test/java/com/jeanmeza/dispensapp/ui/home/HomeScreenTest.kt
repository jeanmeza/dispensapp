package com.jeanmeza.dispensapp.ui.home

import android.os.Build.VERSION_CODES.O
import android.os.Build.VERSION_CODES.VANILLA_ICE_CREAM
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.jeanmeza.dispensapp.data.model.Item
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.Test
import kotlin.time.Clock

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(
    sdk = [O, VANILLA_ICE_CREAM],
    application = HiltTestApplication::class
)
class HomeScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun homeScreen_displays_empty_state_when_no_items() {
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
    fun homeScreen_displays_items_when_items_are_provided() {
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
    fun homeScreen_calls_onItemClick_when_item_is_clicked() {
        // Arrange
        val mockOnItemClick: (Int) -> Unit = mock()
        val items = listOf(
            createItem(1, "Item 1", 2, "kg"),
            createItem(2, "Item 2", 2, "l"),
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
package com.jeanmeza.dispensapp.ui.home

import app.cash.turbine.test
import com.jeanmeza.dispensapp.data.local.entities.ItemEntity
import com.jeanmeza.dispensapp.data.local.entities.PopulatedItem
import com.jeanmeza.dispensapp.data.repository.ItemRepository
import com.jeanmeza.dispensapp.util.MainDispatcherRule
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.time.Clock

class HomeViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var itemRepository: ItemRepository

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `homeUiState emits empty list when repository returns empty`() = runTest {
        // Arrange
        whenever(itemRepository.getAllItemsStream()).thenReturn(flowOf(emptyList()))
        homeViewModel = HomeViewModel(itemRepository)
        // Act & Assert
        homeViewModel.homeUiState.test {
            val state = awaitItem()
            assertEquals(emptyList(), state.items)
        }
    }

    @Test
    fun `homeUiState emits items when repository returns items`() = runTest {
        // Arrange
        val mockItems = listOf(
            createMockPopulatedItem(1, "pasta"),
            createMockPopulatedItem(1, "milk")
        )

        whenever(itemRepository.getAllItemsStream()).thenReturn(flowOf(mockItems))
        homeViewModel = HomeViewModel(itemRepository)

        // Act & Assert
        homeViewModel.homeUiState.test {
            val state = awaitItem()
            assertEquals(2, state.items.size)
            assertEquals("pasta", state.items[0].name)
            assertEquals("milk", state.items[1].name)
        }
    }

    private fun createMockPopulatedItem(id: Int, name: String) = PopulatedItem(
        item = ItemEntity(
            id = id,
            name = name,
            quantity = 1,
            measureUnit = "unit",
            expiryDate = Clock.System.now(),
            imageUri = null,
        ),
        categories = emptyList(),
    )

}
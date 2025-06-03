package com.jeanmeza.dispensapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeanmeza.dispensapp.data.local.entities.asExternalModel
import com.jeanmeza.dispensapp.data.model.Item
import com.jeanmeza.dispensapp.data.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
) : ViewModel() {
    val homeUiState: StateFlow<HomeUiState> =
        itemRepository.getAllItemsStream().map { HomeUiState(it.asExternalModel()) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
                initialValue = HomeUiState()
            )

}

data class HomeUiState(
    val items: List<Item> = listOf()
)
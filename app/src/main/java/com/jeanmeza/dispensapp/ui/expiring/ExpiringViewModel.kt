package com.jeanmeza.dispensapp.ui.expiring

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
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class ExpiringViewModel @Inject constructor(
    itemRepository: ItemRepository,
) : ViewModel() {
    // TODO: May be configurable using a "Settings" dialog in the future
    private val date = getQueryTimestampForNext7Days()

    val expiringUiState: StateFlow<ExpiringUiState> =
        itemRepository.getExpiringItemsStream(date).map { ExpiringUiState(it.asExternalModel()) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
                initialValue = ExpiringUiState()
            )

    private fun getQueryTimestampForNext7Days(): Long {
        return LocalDate.now()
            .plus(7, ChronoUnit.DAYS)
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

}

data class ExpiringUiState(
    val expiringItems: List<Item> = emptyList(),
)
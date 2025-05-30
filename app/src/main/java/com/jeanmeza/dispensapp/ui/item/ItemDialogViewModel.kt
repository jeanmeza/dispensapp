package com.jeanmeza.dispensapp.ui.item

import androidx.lifecycle.ViewModel
import com.jeanmeza.dispensapp.data.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ItemDialogViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {
}
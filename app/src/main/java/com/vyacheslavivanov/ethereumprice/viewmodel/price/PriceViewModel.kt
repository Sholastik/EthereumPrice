package com.vyacheslavivanov.ethereumprice.viewmodel.price

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vyacheslavivanov.ethereumprice.data.Resource
import com.vyacheslavivanov.ethereumprice.data.price.PriceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PriceViewModel @Inject constructor(
    priceRepository: PriceRepository
) : ViewModel() {
    val priceStateFlow = priceRepository.priceFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = Resource.Loading
    )
}

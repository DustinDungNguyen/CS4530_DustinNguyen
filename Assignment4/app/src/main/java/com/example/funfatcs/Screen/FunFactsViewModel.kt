// File: FunFactsViewModel.kt
// Author: Dustin Nguyen — CS 4530
// Description: ViewModel exposing UI state and coordinating fetch requests via the repository.
package com.example.funfacts.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.funfacts.data.local.FunFactEntity
import com.example.funfacts.data.repository.FunFactsRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class UiState(
    val isFetching: Boolean = false,
    val facts: List<FunFactEntity> = emptyList(),
    val errorMessage: String? = null
)

class FunFactsViewModel(
    private val repository: FunFactsRepository
) : ViewModel() {

    val uiState: StateFlow<UiState> =
        repository.facts
            .map { UiState(isFetching = false, facts = it, errorMessage = null) }
            .catch { emit(UiState(errorMessage = it.message ?: "Unknown error")) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UiState()
            )
    private val _errorEvents = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val errorEvents = _errorEvents.asSharedFlow()


    fun fetchNewFact() {
        viewModelScope.launch {
            try {
                repository.fetchAndStoreRandomFact()
            } catch (ex: Exception) {
                _errorEvents.tryEmit(ex.message ?: "Failed to fetch fun fact")
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class FunFactsViewModelFactory(
    private val repository: FunFactsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(FunFactsViewModel::class.java)) {
            "Unknown ViewModel class: ${modelClass.name}"
        }
        return FunFactsViewModel(repository) as T
    }
}

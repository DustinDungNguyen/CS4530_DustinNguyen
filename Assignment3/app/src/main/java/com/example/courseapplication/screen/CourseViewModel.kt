/**
 * File: CourseViewModel.kt
 * Layer: Presentation (ViewModel)
 * Purpose: Holds navigation state and mediates access to CourseRepository.
 * State: uiState combines DB-backed courses Flow with screen state.
 * Factory: CourseViewModelFactory injects singleton repository.
 * Author: Dustin Nguyen | CS 4530
 */

package com.example.courseapplication.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.courseapplication.data.CourseRepository
import com.example.courseapplication.model.Course
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed interface Screen { object List: Screen; data class Detail(val id: Long): Screen; data class Edit(val id: Long?): Screen }

data class UiState(val screen: Screen = Screen.List, val courses: List<Course> = emptyList())

class CourseViewModel(private val repo: CourseRepository) : ViewModel() {
    private val screenFlow = MutableStateFlow<Screen>(Screen.List)

    val uiState: StateFlow<UiState> =
        combine(repo.courses, screenFlow) { courses, screen -> UiState(screen, courses) }
            .stateIn(viewModelScope, SharingStarted.Eagerly, UiState())

    fun goToList() { screenFlow.value = Screen.List }
    fun goToDetail(id: Long) { screenFlow.value = Screen.Detail(id) }
    fun goToAdd() { screenFlow.value = Screen.Edit(null) }
    fun goToEdit(id: Long) { screenFlow.value = Screen.Edit(id) }

    fun addCourse(dept: String, number: String, location: String) =
        viewModelScope.launch { repo.addCourse(dept, number, location); goToList() }

    fun updateCourse(id: Long, dept: String, number: String, location: String) =
        viewModelScope.launch { repo.updateCourse(id, dept, number, location); goToDetail(id) }

    fun deleteCourse(id: Long) =
        viewModelScope.launch { repo.deleteCourse(id); goToList() }

    fun getCourseOrNull(id: Long): Course? = uiState.value.courses.firstOrNull { it.id == id }
}

class CourseViewModelFactory(private val repository: CourseRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = CourseViewModel(repository) as T
}

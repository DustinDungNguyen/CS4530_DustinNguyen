package com.example.courseapplication.screen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.courseapplication.data.CourseRepository
import com.example.courseapplication.model.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

/**
 * Screen — navigation targets represented as immutable state for a single-activity app.
 * Author: Dustin Nguyen | Class: CS 4530
 */
sealed interface Screen { object List: Screen; data class Detail(val id: Long): Screen; data class Edit(val id: Long?): Screen }

/**
 * UiState — aggregate UI model observed by Compose screens (current screen + courses).
 * Author: Dustin Nguyen | Class: CS 4530
 */
data class UiState(val screen: Screen = Screen.List, val courses: List<Course> = emptyList())

/**
 * CourseViewModel — holds navigation state and mediates access to the repository.
 * Author: Dustin Nguyen | Class: CS 4530
 */
class CourseViewModel(private val repo: CourseRepository = CourseRepository()) : ViewModel() {
    private val screenFlow = MutableStateFlow<Screen>(Screen.List)
    val uiState: StateFlow<UiState> = combine(repo.courses, screenFlow) { courses, screen -> UiState(screen, courses) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, UiState())


    fun goToList() = run { screenFlow.value = Screen.List }
    fun goToDetail(id: Long) = run { screenFlow.value = Screen.Detail(id) }
    fun goToAdd() = run { screenFlow.value = Screen.Edit(null) }
    fun goToEdit(id: Long) = run { screenFlow.value = Screen.Edit(id) }


    fun addCourse(dept: String, number: String, location: String) { repo.addCourse(dept, number, location); goToList() }
    fun updateCourse(id: Long, dept: String, number: String, location: String) { repo.updateCourse(id, dept, number, location); goToDetail(id) }
    fun deleteCourse(id: Long) { repo.deleteCourse(id); goToList() }
    fun getCourseOrNull(id: Long): Course? = repo.getCourse(id)
}

/**
 * CourseViewModelFactory — simple factory to create CourseViewModel for Activity scope.
 * Author: Dustin Nguyen | Class: CS 4530
 */
@Suppress("UNCHECKED_CAST")
class CourseViewModelFactory : ViewModelProvider.Factory { override fun <T : ViewModel> create(modelClass: Class<T>): T = CourseViewModel() as T }


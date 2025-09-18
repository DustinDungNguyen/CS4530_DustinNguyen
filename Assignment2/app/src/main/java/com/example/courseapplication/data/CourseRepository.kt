package com.example.courseapplication.data
import com.example.courseapplication.model.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
/**
 * Correct emission-friendly repository: keep an immutable List in StateFlow
 * and use `update { ... }` to set a NEW list each time.
 */
/**
 * CourseRepository — in-memory source of truth for courses. Exposes a StateFlow of
 * the list and provides add/update/delete/get operations.
 * Author: Dustin Nguyen | Class: CS 4530
 */
class CourseRepository {
    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses
    private var nextId = 1L

    fun addCourse(dept: String, number: String, location: String) {
        val course = Course(nextId++, dept.trim(), number.trim(), location.trim())
        _courses.update { it + course }
    }

    fun updateCourse(id: Long, dept: String, number: String, location: String) {
        _courses.update { list ->
            list.map { c ->
                if (c.id == id) c.copy(
                    department = dept.trim(),
                    number = number.trim(),
                    location = location.trim()
                ) else c
            }
        }
    }

    fun deleteCourse(id: Long) {
        _courses.update { list -> list.filterNot { it.id == id } }
    }

    fun getCourse(id: Long): Course? = _courses.value.firstOrNull { it.id == id }
}
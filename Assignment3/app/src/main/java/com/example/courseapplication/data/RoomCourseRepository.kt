/**
 * File: RoomCourseRepository.kt
 * Layer: Data (Repository implementation)
 * Purpose: Implements CourseRepository using CourseDao (Room).
 * Behavior: Exposes Flows for lists/singletons; uses suspend for writes.
 * Author: Dustin Nguyen | CS 4530
 */
package com.example.courseapplication.data

import com.example.courseapplication.data.local.CourseDao
import com.example.courseapplication.model.Course
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class RoomCourseRepository(private val dao: CourseDao) : CourseRepository {

    override val courses: Flow<List<Course>> = dao.getAll()

    override fun getCourse(id: Long): Flow<Course?> = dao.getById(id)

    override suspend fun addCourse(dept: String, number: String, location: String) {
        dao.insert(Course(department = dept.trim(), number = number.trim(), location = location.trim()))
    }

    override suspend fun updateCourse(id: Long, dept: String, number: String, location: String) {
        // Fetch current, update fields, then call update
        val current = dao.getById(id).firstOrNull() ?: return
        dao.update(current.copy(
            department = dept.trim(),
            number = number.trim(),
            location = location.trim()
        ))
    }

    override suspend fun deleteCourse(id: Long) {
        dao.deleteById(id)
    }
}

/**
 * File: CourseRepository.kt
 * Layer: Data (Repository abstraction)
 * Purpose: Interface between ViewModel and storage (Room).
 * Notes: Keeps VM independent of Room; implemented by RoomCourseRepository.
 * Author: Dustin Nguyen | CS 4530
 */
package com.example.courseapplication.data

import com.example.courseapplication.model.Course
import kotlinx.coroutines.flow.Flow

/**
 * Repository abstraction — middle layer between VM and Room.
 */
interface CourseRepository {
    val courses: Flow<List<Course>>
    fun getCourse(id: Long): Flow<Course?>
    suspend fun addCourse(dept: String, number: String, location: String)
    suspend fun updateCourse(id: Long, dept: String, number: String, location: String)
    suspend fun deleteCourse(id: Long)
}

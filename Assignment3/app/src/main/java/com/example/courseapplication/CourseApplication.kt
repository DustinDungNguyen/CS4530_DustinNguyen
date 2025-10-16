/**
 * File: CourseApplication.kt
 * Layer: App (Application singleton)
 * Purpose: Creates single instances of the Room database and CourseRepository.
 * Pattern: Modern approach — DB/repository singletons via lazy properties.
 * Author: Dustin Nguyen | CS 4530
 */

package com.example.courseapplication

import android.app.Application
import androidx.room.Room
import com.example.courseapplication.data.CourseRepository
import com.example.courseapplication.data.RoomCourseRepository
import com.example.courseapplication.data.local.AppDatabase

class CourseApplication : Application() {

    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "course-db"
        )
            .build()
    }

    val repository: CourseRepository by lazy {
        RoomCourseRepository(database.courseDao())
    }
}

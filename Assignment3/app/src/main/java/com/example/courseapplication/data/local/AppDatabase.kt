/**
 * File: AppDatabase.kt
 * Layer: Data (Room)
 * Purpose: Defines the Room database for the app and exposes DAOs.
 * Notes: DB instance is created once in CourseApplication (modern approach).
 * Author: Dustin Nguyen | CS 4530
 */

package com.example.courseapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.courseapplication.model.Course

@Database(
    entities = [Course::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
}

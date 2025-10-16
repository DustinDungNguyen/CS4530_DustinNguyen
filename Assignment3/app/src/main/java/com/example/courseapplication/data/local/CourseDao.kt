/**
 * File: CourseDao.kt
 * Layer: Data (Room)
 * Purpose: Data access object for Course entity (CRUD + queries).
 * Returns: Flows for auto-updating UI on DB changes.
 * Author: Dustin Nguyen | CS 4530
 */

package com.example.courseapplication.data.local

import androidx.room.*
import com.example.courseapplication.model.Course
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Query("SELECT * FROM course ORDER BY department, number")
    fun getAll(): Flow<List<Course>>

    @Query("SELECT * FROM course WHERE id = :id")
    fun getById(id: Long): Flow<Course?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(course: Course): Long

    @Update
    suspend fun update(course: Course)

    @Delete
    suspend fun delete(course: Course)

    @Query("DELETE FROM course WHERE id = :id")
    suspend fun deleteById(id: Long)
}

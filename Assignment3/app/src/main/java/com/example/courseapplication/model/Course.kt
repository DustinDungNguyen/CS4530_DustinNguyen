package com.example.courseapplication.model
/**
 * Course — model for a single course with department, number, and location.
 * Author: Dustin Nguyen | Class: CS 4530
 */

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "course")
data class Course (
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val department: String,
    val number: String,
    val location: String

    ){
    val displayName: String get() = "$department $number"
}
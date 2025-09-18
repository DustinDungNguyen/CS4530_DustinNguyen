package com.example.courseapplication.model
/**
 * Course — model for a single course with department, number, and location.
 * Author: Dustin Nguyen | Class: CS 4530
 */
data class Course (
    val id: Long,
    val department: String,
    val number: String,
    val location: String

    ){
    val displayName: String get() = "$department $number"
}
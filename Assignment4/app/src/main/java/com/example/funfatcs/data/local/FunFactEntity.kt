// File: FunFactEntity.kt
// Author: Dustin Nguyen — CS 4530
// Description: Room entity representing a single fun fact stored locally.
package com.example.funfacts.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fun_facts")
data class FunFactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val text: String,
    val source: String?,
    val language: String?,
    val permalink: String?,
    val createdAtEpochMillis: Long = System.currentTimeMillis()
)

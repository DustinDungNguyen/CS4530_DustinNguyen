// File: FunFactDao.kt
// Author: Dustin Nguyen — CS 4530
// Description: Room DAO defining queries and inserts for persisted fun facts.

package com.example.funfacts.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FunFactDao {

    @Query("SELECT * FROM fun_facts ORDER BY createdAtEpochMillis DESC")
    fun observeAllFacts(): Flow<List<FunFactEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFact(funFact: FunFactEntity)
}

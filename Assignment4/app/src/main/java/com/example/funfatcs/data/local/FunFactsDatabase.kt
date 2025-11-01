// File: FunFactsDatabase.kt
// Author: Dustin Nguyen — CS 4530
// Description: Room database configuration and builder for the app.
package com.example.funfacts.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FunFactEntity::class],
    version = 1,
    exportSchema = true
)
abstract class FunFactsDatabase : RoomDatabase() {

    abstract fun funFactDao(): FunFactDao

    companion object {
        fun build(context: Context): FunFactsDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                FunFactsDatabase::class.java,
                "fun_facts.db"
            ).fallbackToDestructiveMigration()
                .build()
    }
}

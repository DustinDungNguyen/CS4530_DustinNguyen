// File: FunFactsApplication.kt
// Author: Dustin Nguyen — CS 4530
// Description: Application class holding singletons: HttpClient, Room database, Repository.
package com.example.funfacts

import android.app.Application
import com.example.funfacts.data.local.FunFactsDatabase
import com.example.funfacts.data.repository.FunFactsRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class FunFactsApplication : Application() {

    val httpClient: HttpClient by lazy {
        HttpClient(Android) {
            install(Logging) { level = LogLevel.INFO }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        encodeDefaults = true
                    }
                )
            }
        }
    }

    val database: FunFactsDatabase by lazy {
        FunFactsDatabase.build(applicationContext)
    }

    val repository: FunFactsRepository by lazy {
        FunFactsRepository(
            funFactDao = database.funFactDao(),
            httpClient = httpClient
        )
    }

    override fun onTerminate() {
        super.onTerminate()
        try { httpClient.close() } catch (_: Exception) {}
    }
}

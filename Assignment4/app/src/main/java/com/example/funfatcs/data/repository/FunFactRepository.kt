// File: FunFactsRepository.kt
// Author: Dustin Nguyen — CS 4530
// Description: Repository that mediates between Ktor (network) and Room (local DB).
package com.example.funfacts.data.repository

import com.example.funfacts.data.local.FunFactDao
import com.example.funfacts.data.local.FunFactEntity
import com.example.funfacts.data.remote.ApiFunFact
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow

class FunFactsRepository(
    private val funFactDao: FunFactDao,
    private val httpClient: HttpClient
) {
    companion object {
        private const val RANDOM_ENDPOINT =
            "https://uselessfacts.jsph.pl/random.json?language=en"
    }

    //Stream of locally stored facts.
    val facts: Flow<List<FunFactEntity>> = funFactDao.observeAllFacts()

    // Fetches a new random fact from the API and persists it locally.
    suspend fun fetchAndStoreRandomFact(): FunFactEntity {
        val api = httpClient.get(RANDOM_ENDPOINT).body<ApiFunFact>()

        val entity = FunFactEntity(
            text = api.text,
            source = api.source,
            language = api.language,
            permalink = api.permalink
        )
        funFactDao.insertFact(entity)
        return entity
    }
}

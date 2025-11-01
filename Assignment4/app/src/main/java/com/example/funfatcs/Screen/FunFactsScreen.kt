// File: FunFactsScreen.kt
// Author: Dustin Nguyen — CS 4530
// Description: Compose UI that shows stored fun facts and a button to fetch a new one.
package com.example.funfacts.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.funfacts.data.local.FunFactEntity
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FunFactsScreen(
    viewModel: FunFactsViewModel,
    onError: (String?) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.errorMessage) {
        val message = state.errorMessage
        if (!message.isNullOrBlank()) onError(message)
    }

    LaunchedEffect(Unit) {
        viewModel.errorEvents.collectLatest { onError(it) }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Fun Facts") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        try {
                            viewModel.fetchNewFact()
                        } catch (_: CancellationException) {
                        } catch (e: Exception) {
                            onError(e.message)
                        }
                    }
                ) { Text("Fetch New Fun Fact") }
            }

            Spacer(Modifier.height(12.dp))
            Divider()
            Spacer(Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.facts, key = { it.id }) { fact ->
                    FunFactCard(fact)
                }
            }
        }
    }
}

@Composable
private fun FunFactCard(fact: FunFactEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = fact.text,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Source: ${fact.source ?: "Unknown"}  |  Lang: ${fact.language ?: "n/a"}",
                style = MaterialTheme.typography.bodySmall
            )
            fact.permalink?.let {
                Spacer(Modifier.height(4.dp))
                Text(text = it, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}


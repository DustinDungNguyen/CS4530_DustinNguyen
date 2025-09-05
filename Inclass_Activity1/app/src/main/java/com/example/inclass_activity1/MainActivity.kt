package com.example.composeconcat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp

/**
 * App summary (simple):
 * - Shows two text boxes.
 * - Tap "Concatenate" to join them.
 * - Result appears below.
 *
 * Tech notes:
 * - Uses a ComposeView inside an XML layout (activity_main).
 * - UI state is kept with rememberSaveable so it survives rotations.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Load the XML that contains a <ComposeView android:id="@+id/compose_view" />
        setContentView(R.layout.activity_main)

        // Hook Compose content into that XML view
        val composeView = findViewById<ComposeView>(R.id.compose_view)
        composeView.setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ConcatenatorScreen()
                }
            }
        }
    }
}

/**
 * ConcatenatorScreen:
 * - first, second: user inputs
 * - result: joined string
 * - Layout: fields -> button -> result text
 */
@Composable
fun ConcatenatorScreen() {
    var first by rememberSaveable { mutableStateOf("") }
    var second by rememberSaveable { mutableStateOf("") }
    var result by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // First input
        OutlinedTextField(
            value = first,
            onValueChange = { first = it },
            label = { Text("First text") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Second input
        OutlinedTextField(
            value = second,
            onValueChange = { second = it },
            label = { Text("Second text") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Join the two strings when pressed
        Button(
            onClick = { result = first + second },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Concatenate")
        }

        // Show the result
        Text(
            text = "Result: $result",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

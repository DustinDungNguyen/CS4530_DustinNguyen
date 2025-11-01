// File: MainActivity.kt
// Author: Dustin Nguyen — CS 4530
// Description: Activity that wires the ViewModel and shows the Compose screen.
package com.example.funfacts

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.funfacts.ui.FunFactsScreen
import com.example.funfacts.ui.FunFactsViewModel
import com.example.funfacts.ui.FunFactsViewModelFactory

import androidx.activity.viewModels

class MainActivity : ComponentActivity() {

    private val factory by lazy {
        FunFactsViewModelFactory((application as FunFactsApplication).repository)
    }
    private val vm: FunFactsViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FunFactsScreen(
                viewModel = vm,
                onError = { msg ->
                    Toast.makeText(this, msg ?: "Something went wrong", Toast.LENGTH_LONG).show()
                }
            )
        }
    }
}


package com.example.assignment1

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Assignment 1 — Two-Activity App1
 *
 * Student: Dustin Nguyen (u1494912)
 * Course: CS 4530 — Mobile App Development
 * Date: Sep 2, 2025
 *
 * File: DetailActivity.kt
 * Shows the text from MainActivity and supports Up/Back navigation.
 * Expects a String in the intent under [EXTRA_BUTTON_TEXT].
 */
class DetailActivity : AppCompatActivity() {

    /** Intent key for the label to display. */
    companion object { const val EXTRA_BUTTON_TEXT = "extra_button_text" }

    /**
     * Android lifecycle entry point called when the Activity is being created.
     *
     * Sets the layout, enables the ActionBar Up button,
     * reads the incoming text extra, and displays it.
     * @param savedInstanceState State bundle for restoring UI (unused here).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Show ActionBar "Up" button for navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Read extra and display
        val message = intent.getStringExtra(EXTRA_BUTTON_TEXT) ?: ""
        findViewById<TextView>(R.id.textMessage).text = message

        // Optional explicit back button (system back works too)
        findViewById<Button>(R.id.buttonBack)?.setOnClickListener { finish() }
    }

    /**
     * Handles ActionBar Up by finishing this Activity.
     * @return true to indicate the event was handled.
     */
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

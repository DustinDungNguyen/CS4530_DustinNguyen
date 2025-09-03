package com.example.assignment1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * Assignment 1 — Two-Activity App
 *
 * Student: Dustin Nguyen (u1494912)
 * Course: CS 4530 — Mobile App Development
 * Date: Sep 2, 2025
 *
 * File: MainActivity.kt
 * Shows five buttons. Tapping any button opens [DetailActivity] and sends that
 * button's text along with the Intent.
 *
 * Requires: layout `activity_main` with Buttons: button1..button5.
*/
class MainActivity : AppCompatActivity() {

    /**
     * Android lifecycle entry point called when the Activity is created.
     *
     * Sets the content view and wires all five buttons to launch [DetailActivity].
     * @param savedInstanceState State bundle for restoring UI (unused here).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Wire the five buttons with labels and a shared click handler
        wireButton(R.id.button1, getString(R.string.btn_label_one))
        wireButton(R.id.button2, getString(R.string.btn_label_two))
        wireButton(R.id.button3, getString(R.string.btn_label_three))
        wireButton(R.id.button4, getString(R.string.btn_label_four))
        wireButton(R.id.button5, getString(R.string.btn_label_five))
    }

    /**
     * Sets the button's label and click behavior to open [DetailActivity],
     * passing the button's current text as an extra.
     *
     * @param buttonId The view id of the Button to configure (e.g., R.id.button1).
     * @param label The text to display on the Button before it is tapped.
     */
    private fun wireButton(buttonId: Int, label: String) {
        val btn = findViewById<Button>(buttonId)
        btn.text = label
        btn.setOnClickListener { view ->
            val text = (view as Button).text.toString()
            val intent = Intent(this, DetailActivity::class.java)
                .putExtra(DetailActivity.EXTRA_BUTTON_TEXT, text)
            startActivity(intent)
        }
    }
}

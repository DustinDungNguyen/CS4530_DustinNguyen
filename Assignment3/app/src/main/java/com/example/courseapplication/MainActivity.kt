/**
 * File: MainActivity.kt
 * Layer: UI (Entry Activity)
 * Purpose: Wires the singleton repository into CourseViewModel via Factory and
 *          sets the Compose root (CourseApp).
 * Pattern: viewModels { CourseViewModelFactory(app.repository) } for DI/singleton.
 * Author: Dustin Nguyen | CS 4530
 */

package com.example.courseapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import com.example.courseapplication.screen.CourseApp
import com.example.courseapplication.screen.CourseViewModel
import com.example.courseapplication.screen.CourseViewModelFactory

class MainActivity : ComponentActivity() {

    private val vm: CourseViewModel by viewModels {
        val app = application as CourseApplication
        CourseViewModelFactory(app.repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { CourseApp(vm) } }
    }
}

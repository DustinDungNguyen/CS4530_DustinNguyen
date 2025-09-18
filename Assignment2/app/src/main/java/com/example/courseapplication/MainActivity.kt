package com.example.courseapplication
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import com.example.courseapplication.screen.CourseApp
import com.example.courseapplication.screen.CourseViewModel
import com.example.courseapplication.screen.CourseViewModelFactory

/**
 * MainActivity — single-activity entry point that sets Compose content
 * and wires the CourseViewModel into the UI.
 * Author: Dustin Nguyen | Class: CS 4530
 */
class MainActivity : ComponentActivity() {
    private val vm: CourseViewModel by viewModels { CourseViewModelFactory() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { CourseApp(vm) } }
    }
}
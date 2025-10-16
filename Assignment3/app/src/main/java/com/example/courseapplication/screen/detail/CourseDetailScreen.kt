/**
 * File: CourseDetailScreen.kt
 * Description: Details for a course with edit/delete actions.
 * Author: Dustin Nguyen | Class: CS 4530
 */
package com.example.courseapplication.screen.detail
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.courseapplication.model.Course

@Composable
fun CourseDetailScreen(course: Course, onEdit: () -> Unit, onDelete: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = course.displayName, style = MaterialTheme.typography.headlineMedium)
        Text(text = "Department: ${course.department}")
        Text(text = "Number: ${course.number}")
        Text(text = "Location: ${course.location}")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = onEdit) { Text("Edit") }
            OutlinedButton(onClick = onDelete) { Text("Delete") }
        }
    }
}
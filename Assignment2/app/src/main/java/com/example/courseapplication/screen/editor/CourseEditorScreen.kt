/**
 * File: CourseEditorScreen.kt
 * Description: Add/Edit form for courses with validation and save/cancel actions.
 * Author: Dustin Nguyen | Class: CS 4530
 */
package com.example.courseapplication.screen.editor
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.courseapplication.model.Course

@Composable
fun CourseEditorScreen(
    course: Course?,
    onCancel: () -> Unit,
    onSave: (dept: String, number: String, location: String) -> Unit
) {
    var dept by remember { mutableStateOf(course?.department.orEmpty()) }
    var number by remember { mutableStateOf(course?.number.orEmpty()) }
    var location by remember { mutableStateOf(course?.location.orEmpty()) }
    val canSave = dept.isNotBlank() && number.isNotBlank() && location.isNotBlank()


    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(if (course == null) "Add Course" else "Edit Course")
        OutlinedTextField(value = dept, onValueChange = { dept = it.uppercase() }, label = { Text("Department (e.g., CS)") })
        OutlinedTextField(value = number, onValueChange = { number = it.filter { ch -> ch.isDigit() } }, label = { Text("Number (e.g., 4530)") })
        OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Location") })
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick = onCancel) { Text("Cancel") }
            Button(onClick = { onSave(dept, number, location) }, enabled = canSave) { Text("Save") }
        }
    }
}
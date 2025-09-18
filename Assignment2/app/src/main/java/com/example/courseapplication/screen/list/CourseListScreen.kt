/**
 * File: CourseListScreen.kt
 * Description: LazyColumn list of courses with row navigation and delete actions.
 * Author: Dustin Nguyen | Class: CS 4530
 */
package com.example.courseapplication.screen.list
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.courseapplication.model.Course

@Composable
fun CourseListScreen(courses: List<Course>, onClick: (Long) -> Unit, onDelete: (Long) -> Unit) {
    if (courses.isEmpty()) { Text("No courses yet. Tap + to add.") }
    else LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
        items(courses, key = { it.id }) { course ->
            ListItem(
                headlineContent = { Text(course.displayName, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                modifier = Modifier.fillMaxWidth().clickable { onClick(course.id) },
                trailingContent = { Text("Delete", modifier = Modifier.clickable { onDelete(course.id) }) }
            )
            HorizontalDivider()
        }
    }
}
/**
 * File: CourseListScreen.kt
 * Description: LazyColumn list of courses with row navigation and delete actions.
 * Author: Dustin Nguyen | Class: CS 4530
 */
package com.example.courseapplication.screen.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.courseapplication.model.Course

@Composable
fun CourseListScreen(
    courses: List<Course>,
    onClick: (Long) -> Unit,
    onDelete: (Long) -> Unit
) {
    if (courses.isEmpty()) {
        EmptyCoursesState()
    } else {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(courses, key = { it.id }) { course ->
                ListItem(
                    headlineContent = {
                        Text(
                            course.displayName,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    supportingContent = {
                        Text(
                            text = course.location,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    trailingContent = {
                        IconButton(onClick = { onDelete(course.id) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete course"
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClick(course.id) }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
private fun EmptyCoursesState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "No courses yet",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Tap the • Add Course • button to add your first course.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

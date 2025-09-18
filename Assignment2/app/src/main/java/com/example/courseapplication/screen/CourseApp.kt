/**
 * File: CourseApp.kt
 * Description: Root Compose UI with Scaffold, top app bar, FAB, and simple ViewModel-driven nav.
 * Author: Dustin Nguyen | Class: CS 4530
 */
package com.example.courseapplication.screen
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.courseapplication.screen.detail.CourseDetailScreen
import com.example.courseapplication.screen.editor.CourseEditorScreen
import com.example.courseapplication.screen.list.CourseListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseApp(vm: CourseViewModel) {
    val state by vm.uiState.collectAsState()


// Handle system back to return to the proper screen
    BackHandler(enabled = state.screen !is Screen.List) {
        when (val s = state.screen) {
            is Screen.Detail -> vm.goToList()
            is Screen.Edit -> if (s.id == null) vm.goToList() else vm.goToDetail(s.id)
            else -> Unit
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Course Manager") },
                navigationIcon = {
                    if (state.screen !is Screen.List) {
                        TextButton(onClick = {
                            when (val s = state.screen) {
                                is Screen.Detail -> vm.goToList()
                                is Screen.Edit -> if (s.id == null) vm.goToList() else vm.goToDetail(s.id)
                                else -> Unit
                            }
                        }) { Text("Back") }
                    }
                }
            )
        },
        floatingActionButton = {
            if (state.screen is Screen.List) { ExtendedFloatingActionButton(onClick = vm::goToAdd) { Text("Add Course") } }
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            when (val scr = state.screen) {
                is Screen.List -> CourseListScreen(state.courses, onClick = vm::goToDetail, onDelete = vm::deleteCourse)
                is Screen.Detail -> vm.getCourseOrNull(scr.id)?.let { c ->
                    CourseDetailScreen(course = c, onEdit = { vm.goToEdit(c.id) }, onDelete = { vm.deleteCourse(c.id) })
                } ?: MissingCourse()
                is Screen.Edit -> CourseEditorScreen(
                    course = scr.id?.let(vm::getCourseOrNull),
                    onCancel = { if (scr.id == null) vm.goToList() else vm.goToDetail(scr.id) },
                    onSave = { d, n, l -> if (scr.id == null) vm.addCourse(d, n, l) else vm.updateCourse(scr.id, d, n, l) }
                )
            }
        }
    }
}
@Composable fun MissingCourse() { Text("Course not found") }
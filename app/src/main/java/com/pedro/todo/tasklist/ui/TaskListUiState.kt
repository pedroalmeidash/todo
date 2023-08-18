package com.pedro.todo.tasklist.ui

data class TaskListUiState(
    val tasks: List<TaskListItemUiState>
)

data class TaskListItemUiState(
    val id: String,
    val title: String,
    val description: String,
    val isChecked: Boolean,
)
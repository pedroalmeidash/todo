package com.pedro.todo.tasklist.ui

data class TaskListUiState(
    val tasks: List<TaskListItemUiState>
)

sealed class TaskListItemUiState {
    data class Task(
        val title: String,
        val description: String,
    ) : TaskListItemUiState()
}
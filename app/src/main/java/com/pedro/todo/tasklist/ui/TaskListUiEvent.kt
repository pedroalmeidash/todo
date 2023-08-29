package com.pedro.todo.tasklist.ui

sealed class TaskListUiEvent {
    data class OnCheckChanged(
        val taskId: String,
    ) : TaskListUiEvent()
}
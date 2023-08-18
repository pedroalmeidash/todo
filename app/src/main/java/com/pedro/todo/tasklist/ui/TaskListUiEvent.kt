package com.pedro.todo.tasklist.ui

sealed class TaskListUiEvent {
    data class OnCheckChanged(
        val taskId: String,
        val isChecked: Boolean,
    ) : TaskListUiEvent()
}
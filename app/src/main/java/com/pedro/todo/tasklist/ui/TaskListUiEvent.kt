package com.pedro.todo.tasklist.ui

sealed class TaskListUiEvent {
    data class OnCheckChanged(
        val taskId: String,
    ) : TaskListUiEvent()

    data class OnTaskTapped(
        val taskId: String,
        val taskTitle: String,
        val taskDescription: String,
    ) : TaskListUiEvent()

    object OnPrimaryButtonTapped : TaskListUiEvent()
}
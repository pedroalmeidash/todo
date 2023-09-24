package com.pedro.todo.taskdetails.ui

sealed class TaskDetailUiEvent {
    data class OnTitleChange(val newTitle: String) : TaskDetailUiEvent()
    data class OnDescriptionChange(val newDescription: String) : TaskDetailUiEvent()
}
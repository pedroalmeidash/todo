package com.pedro.todo.createtask.ui

sealed class CreateTaskUiEvent {
    object OnPrimaryButtonTapped : CreateTaskUiEvent()
    data class OnTitleChange(val newTitle: String) : CreateTaskUiEvent()
    data class OnDescriptionChange(val newDescription: String) : CreateTaskUiEvent()
}

package com.pedro.todo.tasklist.mapper

import com.pedro.todo.repository.TaskDTO
import com.pedro.todo.tasklist.ui.TaskListItemUiState

interface TaskItemUiStateMapper {
    fun mapToUiState(taskDTO: TaskDTO): TaskListItemUiState
}
package com.pedro.todo.tasklist.mapper

import com.pedro.todo.repository.TaskDTO
import com.pedro.todo.tasklist.ui.TaskListItemUiState

class TaskItemUiStateMapperImpl : TaskItemUiStateMapper {
    override fun mapToUiState(taskDTO: TaskDTO): TaskListItemUiState {
        return TaskListItemUiState(
            title = taskDTO.title,
            description = taskDTO.description,
        )
    }
}
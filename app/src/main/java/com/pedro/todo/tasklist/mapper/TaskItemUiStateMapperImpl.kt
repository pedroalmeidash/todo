package com.pedro.todo.tasklist.mapper

import com.pedro.todo.repository.TaskDTO
import com.pedro.todo.tasklist.ui.TaskListItemUiState

class TaskItemUiStateMapperImpl : TaskItemUiStateMapper {
    override fun mapToUiState(
        taskDTO: TaskDTO,
        checkedTasksId: List<String>,
    ): TaskListItemUiState {
        return TaskListItemUiState(
            id = taskDTO.id,
            title = taskDTO.title,
            description = taskDTO.description,
            isChecked = checkedTasksId.contains(taskDTO.id),
        )
    }
}
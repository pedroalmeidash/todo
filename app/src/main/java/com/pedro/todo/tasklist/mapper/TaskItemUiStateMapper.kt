package com.pedro.todo.tasklist.mapper

import com.pedro.todo.data.dto.TaskDTO
import com.pedro.todo.tasklist.ui.TaskListItemUiState

interface TaskItemUiStateMapper {
    fun mapToUiState(
        taskDTO: TaskDTO,
        checkedTasksId: List<String>,
    ): TaskListItemUiState
}
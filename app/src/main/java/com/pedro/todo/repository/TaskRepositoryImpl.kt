package com.pedro.todo.repository

import com.pedro.todo.tasklist.ui.TaskListItemUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.startWith

class TaskRepositoryImpl : TaskRepository {

    private val _taskDTOListStream: MutableSharedFlow<List<TaskDTO>> = MutableSharedFlow<List<TaskDTO>>(
        replay = 1
    )
    override fun getAllTasks(): Flow<List<TaskDTO>> {
        return _taskDTOListStream.onStart {
            emit(listOf(TaskDTO("Id", "Title", "Description")))
        }
    }
}
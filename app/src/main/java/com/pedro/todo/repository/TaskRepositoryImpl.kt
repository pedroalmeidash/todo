package com.pedro.todo.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TaskRepositoryImpl : TaskRepository {

    private val _taskDTOListStream: MutableSharedFlow<List<TaskDTO>> = MutableSharedFlow(
        replay = 1
    )

    override fun getAllTasks(): Flow<List<TaskDTO>> {
        return _taskDTOListStream
    }
}
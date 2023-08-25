package com.pedro.todo.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TaskRepositoryImpl : TaskRepository {

    private val taskDTOListStream: MutableSharedFlow<List<TaskDTO>> = MutableSharedFlow(
        replay = 1
    )

    override fun getAllTasks(): Flow<List<TaskDTO>> {
        return taskDTOListStream
    }

    override fun updateTasks(taskDTOList: List<TaskDTO>) {
        println("Update called")
        taskDTOListStream.tryEmit(taskDTOList)
    }
}
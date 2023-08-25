package com.pedro.todo.repository

import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<TaskDTO>>
    fun updateTasks(taskDTOList: List<TaskDTO>)
}
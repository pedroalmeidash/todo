package com.pedro.todo.repository

import kotlinx.coroutines.flow.Flow

interface TaskUpdateRepository {
    fun init(): Flow<Unit>
    fun addTask(taskDTO: TaskDTO)
}
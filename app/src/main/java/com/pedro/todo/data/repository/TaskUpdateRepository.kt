package com.pedro.todo.data.repository

import com.pedro.todo.data.dto.TaskDTO

interface TaskUpdateRepository {
    fun addTask(taskDTO: TaskDTO)
}
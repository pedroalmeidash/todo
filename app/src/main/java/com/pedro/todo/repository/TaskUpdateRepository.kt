package com.pedro.todo.repository

interface TaskUpdateRepository {
    fun addTask(taskDTO: TaskDTO)
}
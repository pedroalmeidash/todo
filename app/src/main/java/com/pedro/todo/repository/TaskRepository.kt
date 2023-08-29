package com.pedro.todo.repository

import io.reactivex.rxjava3.core.Observable

interface TaskRepository {
    fun getAllTasks(): Observable<List<TaskDTO>>
    fun updateTasks(taskDTOList: List<TaskDTO>)
}
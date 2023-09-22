package com.pedro.todo.data.repository

import com.pedro.todo.data.dto.TaskDTO
import io.reactivex.rxjava3.core.Observable

interface TaskRepository {
    fun getAllTasks(): Observable<List<TaskDTO>>
    fun updateTasks(taskDTOList: List<TaskDTO>)
}
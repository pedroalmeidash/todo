package com.pedro.todo.data.repository

import com.pedro.todo.data.dto.TaskDTO
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface TaskRepository {
    fun getAllTasks(): Observable<List<TaskDTO>>
    fun updateTasks(taskDTOList: List<TaskDTO>)
    fun getNexTaskId(): Single<String>
}
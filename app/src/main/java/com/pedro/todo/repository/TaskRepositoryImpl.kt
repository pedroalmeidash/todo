package com.pedro.todo.repository

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class TaskRepositoryImpl : TaskRepository {

    private val taskDTOListStream = BehaviorSubject.createDefault<List<TaskDTO>>(emptyList())

    override fun getAllTasks(): Observable<List<TaskDTO>> {
        return taskDTOListStream
    }

    override fun updateTasks(taskDTOList: List<TaskDTO>) {
        taskDTOListStream.onNext(taskDTOList)
    }
}
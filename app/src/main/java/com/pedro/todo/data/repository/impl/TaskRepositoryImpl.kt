package com.pedro.todo.data.repository.impl

import com.pedro.todo.data.dto.TaskDTO
import com.pedro.todo.data.repository.TaskRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor() : TaskRepository {

    private val taskDTOListStream = BehaviorSubject.createDefault<List<TaskDTO>>(emptyList())

    override fun getAllTasks(): Observable<List<TaskDTO>> {
        return taskDTOListStream
    }

    override fun updateTasks(taskDTOList: List<TaskDTO>) {
        taskDTOListStream.onNext(taskDTOList)
    }

    override fun getNexTaskId(): Single<String> {
        return taskDTOListStream
            .take(1)
            .singleOrError()
            .map {
                if (it.isEmpty()) {
                    "1"
                } else {
                    it.last().id.toInt().inc().toString()
                }
            }
    }
}
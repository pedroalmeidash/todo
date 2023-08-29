package com.pedro.todo.repository

import android.annotation.SuppressLint
import io.reactivex.rxjava3.subjects.PublishSubject

@SuppressLint("CheckResult")
class TaskUpdateRepositoryImpl(
    private val taskRepository: TaskRepository
) : TaskUpdateRepository {

    private val taskUpdateStream = PublishSubject.create<TaskUpdate>()

    init {
        taskUpdateStream.withLatestFrom(
            taskRepository.getAllTasks(),
            ::Pair,
        ).subscribe { (taskUpdate, taskList) ->
            val updatedTaskList = when (taskUpdate) {
                is TaskUpdate.Add -> handleAddTaskUpdate(taskUpdate.taskDTO, taskList)
            }
            taskRepository.updateTasks(updatedTaskList)
        }
    }

    private fun handleAddTaskUpdate(
        taskDTO: TaskDTO,
        taskList: List<TaskDTO>,
    ): List<TaskDTO> {
        return taskList + taskDTO
    }

    override fun addTask(taskDTO: TaskDTO) {
        taskUpdateStream.onNext(TaskUpdate.Add(taskDTO))
    }
}

private sealed class TaskUpdate {
    data class Add(val taskDTO: TaskDTO) : TaskUpdate()
}
package com.pedro.todo.data.repository.impl

import android.annotation.SuppressLint
import com.pedro.todo.data.dto.TaskDTO
import com.pedro.todo.data.repository.TaskRepository
import com.pedro.todo.data.repository.TaskUpdateRepository
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@SuppressLint("CheckResult")
class TaskUpdateRepositoryImpl @Inject constructor(
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

    private sealed class TaskUpdate {
        data class Add(val taskDTO: TaskDTO) : TaskUpdate()
    }
}
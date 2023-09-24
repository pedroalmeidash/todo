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
                is TaskUpdate.Add -> handleAddTask(taskUpdate.taskDTO, taskList)
                is TaskUpdate.Details -> handleTaskDetailsUpdate(taskUpdate.taskDTO, taskList)
            }
            taskRepository.updateTasks(updatedTaskList)
        }
    }

    private fun handleAddTask(
        taskDTO: TaskDTO,
        taskList: List<TaskDTO>,
    ): List<TaskDTO> {
        return taskList + taskDTO
    }

    private fun handleTaskDetailsUpdate(
        taskDTO: TaskDTO,
        taskList: List<TaskDTO>,
    ): List<TaskDTO> {
        val updatedTaskList = taskList.toMutableList()
        val taskIndex = updatedTaskList.indexOfFirst { it.id == taskDTO.id }

        return if (taskIndex >= 0) {
            updatedTaskList[taskIndex] = taskDTO
            updatedTaskList
        } else {
            taskList
        }
    }

    override fun addTask(taskDTO: TaskDTO) {
        taskUpdateStream.onNext(TaskUpdate.Add(taskDTO))
    }

    override fun updateTask(taskDTO: TaskDTO) {
        taskUpdateStream.onNext(TaskUpdate.Details(taskDTO))
    }

    private sealed class TaskUpdate {
        data class Add(val taskDTO: TaskDTO) : TaskUpdate()
        data class Details(val taskDTO: TaskDTO) : TaskUpdate()
    }
}
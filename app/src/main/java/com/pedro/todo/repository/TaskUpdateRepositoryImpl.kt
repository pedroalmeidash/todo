package com.pedro.todo.repository

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take

class TaskUpdateRepositoryImpl(
    private val taskRepository: TaskRepository
) : TaskUpdateRepository {

    private val taskUpdateStream = MutableSharedFlow<TaskUpdate>(replay = 0, extraBufferCapacity = 10)

    @OptIn(FlowPreview::class)
    private val taskUpdateAndListStream = taskUpdateStream.flatMapConcat { taskUpdate ->
        taskRepository.getAllTasks().take(1).map { taskList ->
            Pair(taskUpdate, taskList)
        }
    }

    override fun init(): Flow<Unit> {
        return taskUpdateAndListStream.onEach {
            val (taskUpdate, taskList) = it
            val updatedTaskList = when (taskUpdate) {
                is TaskUpdate.Add -> handleAddTaskUpdate(taskUpdate.taskDTO, taskList)
            }
            taskRepository.updateTasks(updatedTaskList)
        }.map {}
    }

    private fun handleAddTaskUpdate(
        taskDTO: TaskDTO,
        taskList: List<TaskDTO>,
    ): List<TaskDTO> {
        return taskList + taskDTO
    }

    override fun addTask(taskDTO: TaskDTO) {
        taskUpdateStream.tryEmit(TaskUpdate.Add(taskDTO))
    }
}

private sealed class TaskUpdate {
    data class Add(val taskDTO: TaskDTO) : TaskUpdate()
}
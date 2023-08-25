package com.pedro.todo.tasklist.ui

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedro.todo.repository.TaskDTO
import com.pedro.todo.repository.TaskRepository
import com.pedro.todo.repository.TaskUpdateRepository
import com.pedro.todo.tasklist.mapper.TaskItemUiStateMapper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val taskRepository: TaskRepository,
    private val taskItemUiStateMapper: TaskItemUiStateMapper,
    private val taskUpdateRepository: TaskUpdateRepository,
) : ViewModel() {
    private val taskListDTOLiveData: MutableLiveData<List<TaskDTO>> = MutableLiveData()
    private val checkedTasksIdLiveData: MutableLiveData<List<String>> = MutableLiveData()
    val taskListLiveData: MediatorLiveData<TaskListUiState> = MediatorLiveData()

    init {
        taskListLiveData.apply {
            addSource(taskListDTOLiveData) { taskDTOList ->
                this.value = buildUiState(taskDTOList, checkedTasksIdLiveData.value.orEmpty())
            }
            addSource(checkedTasksIdLiveData) { checkedTasksId ->
                this.value = buildUiState(taskListDTOLiveData.value.orEmpty(), checkedTasksId)
            }
        }

        viewModelScope.launch {
            taskRepository
                .getAllTasks()
                .collect { taskListDTOLiveData.postValue(it) }
        }

        viewModelScope.launch {
            taskUpdateRepository.init().collect()
        }

        taskRepository.updateTasks(emptyList())
        onTaskClick()
    }

    private fun onTaskClick() {
        taskUpdateRepository.addTask(
            TaskDTO("Id", "Title", "Description")
        )
    }

    private fun buildUiState(
        taskDTOList: List<TaskDTO>,
        checkedTasksId: List<String>,
    ): TaskListUiState {
        val tasks = taskDTOList.map { taskDTO ->
            taskItemUiStateMapper.mapToUiState(
                taskDTO = taskDTO,
                checkedTasksId = checkedTasksId,
            )
        }
        return TaskListUiState(tasks)
    }

    fun handleUiEvents(uiEvent: TaskListUiEvent) {
        when (uiEvent) {
            is TaskListUiEvent.OnCheckChanged -> {
                onTaskClick()
                updateTaskCheckStatus(
                    taskId = uiEvent.taskId,
                    isChecked = uiEvent.isChecked,
                )
            }
        }
    }

    private fun updateTaskCheckStatus(taskId: String, isChecked: Boolean) {
        val checkedTasksId = checkedTasksIdLiveData.value.orEmpty().toMutableList()
        if (isChecked) {
            checkedTasksId.add(taskId)
        } else {
            checkedTasksId.remove(taskId)
        }
        checkedTasksIdLiveData.value = checkedTasksId
    }
}
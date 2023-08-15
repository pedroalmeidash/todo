package com.pedro.todo.tasklist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedro.todo.repository.TaskRepository
import com.pedro.todo.tasklist.mapper.TaskItemUiStateMapper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val taskRepository: TaskRepository,
    private val taskItemUiStateMapper: TaskItemUiStateMapper,
) : ViewModel() {
    private val _taskListLiveData: MutableLiveData<TaskListUiState> = MutableLiveData()
    val taskListLiveData: LiveData<TaskListUiState> = _taskListLiveData

    init {
        viewModelScope.launch {
            taskRepository
                .getAllTasks()
                .map { it.map(taskItemUiStateMapper::mapToUiState) }
                .collect {
                    _taskListLiveData.postValue(TaskListUiState(it))
                }
        }
    }
}
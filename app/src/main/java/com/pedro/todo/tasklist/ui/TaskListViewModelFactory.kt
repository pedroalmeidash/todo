package com.pedro.todo.tasklist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pedro.todo.repository.TaskRepositoryImpl
import com.pedro.todo.repository.TaskUpdateRepositoryImpl
import com.pedro.todo.tasklist.mapper.TaskItemUiStateMapperImpl

class TaskListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val taskRepository = TaskRepositoryImpl()
        return TaskListViewModel(
            taskRepository = taskRepository,
            taskItemUiStateMapper = TaskItemUiStateMapperImpl(),
        ) as T
    }
}
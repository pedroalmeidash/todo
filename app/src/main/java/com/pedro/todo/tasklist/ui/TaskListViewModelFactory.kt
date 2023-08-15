package com.pedro.todo.tasklist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pedro.todo.repository.TaskRepositoryImpl
import com.pedro.todo.tasklist.mapper.TaskItemUiStateMapperImpl

class TaskListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskListViewModel(
            taskRepository = TaskRepositoryImpl(),
            taskItemUiStateMapper = TaskItemUiStateMapperImpl(),
        ) as T
    }
}
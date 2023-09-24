package com.pedro.todo.navigation

import com.pedro.todo.data.dto.TaskDTO

sealed class NavigationModel {
    object CreateTask : NavigationModel()
    object NavigateBack : NavigationModel()
    data class TaskDetail(val taskDTO: TaskDTO) : NavigationModel()
}
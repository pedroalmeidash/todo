package com.pedro.todo.navigation

sealed class NavigationModel {
    object CreateTask : NavigationModel()
    object NavigateBack : NavigationModel()
}
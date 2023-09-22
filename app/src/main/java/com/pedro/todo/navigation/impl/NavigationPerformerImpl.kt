package com.pedro.todo.navigation.impl

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.fragment.app.FragmentActivity
import com.pedro.todo.R
import com.pedro.todo.navigation.NavigationModel
import com.pedro.todo.navigation.NavigationPerformer
import com.pedro.todo.tasklist.ui.TaskListFragment

class NavigationPerformerImpl(
    private val activity: FragmentActivity,
) : NavigationPerformer {
    override fun navigateTo(navigationModel: NavigationModel) {
        when (navigationModel) {
            is NavigationModel.CreateTask -> navigateToCreateTask()
        }
    }

    private fun navigateToCreateTask() {
        activity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view,  TaskListFragment())
            .commit()
    }
}
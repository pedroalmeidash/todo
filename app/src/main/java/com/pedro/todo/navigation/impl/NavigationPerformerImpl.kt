package com.pedro.todo.navigation.impl

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.pedro.todo.R
import com.pedro.todo.createtask.ui.CreateTaskFragment
import com.pedro.todo.navigation.NavigationModel
import com.pedro.todo.navigation.NavigationPerformer
import com.pedro.todo.tasklist.ui.TaskListFragment

class NavigationPerformerImpl(
    private val activity: FragmentActivity,
) : NavigationPerformer {
    override fun navigateTo(navigationModel: NavigationModel) {
        when (navigationModel) {
            is NavigationModel.CreateTask -> navigateToCreateTask()
            is NavigationModel.NavigateBack -> navigateBack()
        }
    }

    private fun navigateBack() {
        if (activity.supportFragmentManager.fragments.size > 1) {
            activity.supportFragmentManager.popBackStack()
        } else {
            activity.onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun navigateToCreateTask() {
        navigateToFragment(CreateTaskFragment())
    }

    private fun navigateToFragment(fragment: Fragment) {
        activity.supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container_view,  fragment)
            .addToBackStack("")
            .commit()
    }
}
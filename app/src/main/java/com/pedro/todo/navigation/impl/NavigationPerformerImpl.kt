package com.pedro.todo.navigation.impl

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.pedro.todo.R
import com.pedro.todo.createtask.ui.CreateTaskFragment
import com.pedro.todo.data.dto.TaskDTO
import com.pedro.todo.navigation.NavigationModel
import com.pedro.todo.navigation.NavigationPerformer
import com.pedro.todo.navigation.VIEW_MODEL_ARGUMENTS
import com.pedro.todo.taskdetails.ui.TaskDetailFragment

class NavigationPerformerImpl(
    private val activity: FragmentActivity,
) : NavigationPerformer {
    override fun navigateTo(navigationModel: NavigationModel) {
        when (navigationModel) {
            is NavigationModel.CreateTask -> navigateToCreateTask()
            is NavigationModel.NavigateBack -> navigateBack()
            is NavigationModel.TaskDetail -> navigateToTaskDetail(navigationModel.taskDTO)
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

    private fun navigateToTaskDetail(
        taskDTO: TaskDTO
    ) {
        val fragment = TaskDetailFragment().apply {
            arguments = bundleOf(
                VIEW_MODEL_ARGUMENTS to taskDTO
            )
        }
        navigateToFragment(fragment)
    }

    private fun navigateToFragment(fragment: Fragment) {
        activity.supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container_view,  fragment)
            .addToBackStack("")
            .commit()
    }
}
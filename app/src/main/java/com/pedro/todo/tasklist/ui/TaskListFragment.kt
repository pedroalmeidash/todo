package com.pedro.todo.tasklist.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class TaskListFragment : Fragment() {
    private val viewModel: TaskListViewModel by viewModels(
        factoryProducer = { TaskListViewModelFactory() }
    )

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.taskListLiveData.observe(viewLifecycleOwner) {

        }
    }
}
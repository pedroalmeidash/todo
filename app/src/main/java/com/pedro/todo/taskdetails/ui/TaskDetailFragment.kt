package com.pedro.todo.taskdetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskDetailFragment : Fragment() {

    private val viewModel: TaskDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val stateStream = viewModel.buildStateStream().subscribeAsState(
                    initial = TaskDetailUiState("", "")
                )
                TaskDetailsView(
                    uiState = stateStream.value,
                    onUiEvent = { viewModel.handleUiEvent(it) }
                )
            }
        }
    }
}
package com.pedro.todo.tasklist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pedro.todo.navigation.NavigationPerformer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TaskListFragment : Fragment() {
    @Inject
    lateinit var navigationPerformer: NavigationPerformer

    private val viewModel: TaskListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val screenState = viewModel.buildUiState().subscribeAsState(
                    TaskListUiState(emptyList())
                )
                TaskListView(
                    uiState = screenState.value,
                ) { uiEvent -> viewModel.handleUiEvents(uiEvent) }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigationStream.subscribe {
            navigationPerformer.navigateTo(it)
        }.apply {}
    }
}
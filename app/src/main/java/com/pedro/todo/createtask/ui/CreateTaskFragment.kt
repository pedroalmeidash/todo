package com.pedro.todo.createtask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pedro.todo.navigation.NavigationPerformer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateTaskFragment : Fragment() {
    @Inject
    lateinit var navigationPerformer: NavigationPerformer

    private val viewModel: CreateTaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                val screenState = viewModel.uiStateStream().subscribeAsState(
                    CreateTaskUiState("", "")
                )
                CreateTaskView(
                    uiState = screenState.value,
                    onUiEvent = { viewModel.handleUiEvents(it) },
                )
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
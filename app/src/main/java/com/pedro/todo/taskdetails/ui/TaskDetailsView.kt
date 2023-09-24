package com.pedro.todo.taskdetails.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.pedro.todo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsView(
    uiState: TaskDetailUiState,
    onUiEvent: (TaskDetailUiEvent) -> Unit,
) {
    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(dimensionResource(id = R.dimen.small_padding))
            ) {
                TaskInputFields(
                    title = uiState.title,
                    description = uiState.description,
                    onUiEvent = onUiEvent,
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskInputFields(
    title: String,
    description: String,
    onUiEvent: (TaskDetailUiEvent) -> Unit,
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = title,
        onValueChange = { onUiEvent(TaskDetailUiEvent.OnTitleChange(it)) }
    )

    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_padding)))

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = description,
        onValueChange = { onUiEvent(TaskDetailUiEvent.OnDescriptionChange(it)) }
    )
}
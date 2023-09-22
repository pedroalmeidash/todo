package com.pedro.todo.createtask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.pedro.todo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskView(
    uiState: CreateTaskUiState,
    onUiEvent: (CreateTaskUiEvent) -> Unit,
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
        bottomBar = {
            Button(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.small_padding))
                    .fillMaxWidth(),
                onClick = { onUiEvent(CreateTaskUiEvent.OnPrimaryButtonTapped) },
            ) {
                Text(text = "Create Task", color = Color.White)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskInputFields(
    title: String,
    description: String,
    onUiEvent: (CreateTaskUiEvent) -> Unit,
) {
    TextField(
        value = title,
        onValueChange = { onUiEvent(CreateTaskUiEvent.OnTitleChange(it)) }
    )

    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.medium_padding)))

    TextField(
        value = description,
        onValueChange = { onUiEvent(CreateTaskUiEvent.OnDescriptionChange(it)) }
    )
}

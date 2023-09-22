package com.pedro.todo.tasklist.di

import com.pedro.todo.tasklist.mapper.TaskItemUiStateMapper
import com.pedro.todo.tasklist.mapper.TaskItemUiStateMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface TaskListModule {
    @Binds
    fun bindTaskItemUiStateMapper(impl: TaskItemUiStateMapperImpl): TaskItemUiStateMapper
}
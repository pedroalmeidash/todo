package com.pedro.todo.data.di

import com.pedro.todo.data.repository.TaskRepository
import com.pedro.todo.data.repository.impl.TaskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindTaskRepository(impl: TaskRepositoryImpl): TaskRepository
}
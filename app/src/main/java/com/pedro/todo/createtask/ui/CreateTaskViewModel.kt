package com.pedro.todo.createtask.ui

import androidx.lifecycle.ViewModel
import com.pedro.todo.data.dto.TaskDTO
import com.pedro.todo.data.repository.TaskRepository
import com.pedro.todo.data.repository.TaskUpdateRepository
import com.pedro.todo.navigation.NavigationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskUpdateRepository: TaskUpdateRepository,
) : ViewModel() {
    private val disposables: MutableList<Disposable> = mutableListOf()
    private val titleStream: BehaviorSubject<String> = BehaviorSubject.createDefault("")
    private val descriptionStream: BehaviorSubject<String> = BehaviorSubject.createDefault("")

    val navigationStream: PublishSubject<NavigationModel> = PublishSubject.create()

    fun buildUiStateStream(): Observable<CreateTaskUiState> {
        return Observable.combineLatest(
            titleStream.distinctUntilChanged(),
            descriptionStream.distinctUntilChanged(),
        ) { title, description ->
            CreateTaskUiState(
                title = title,
                description = description,
            )
        }
    }

    fun handleUiEvents(uiEvent: CreateTaskUiEvent) {
        when (uiEvent) {
            is CreateTaskUiEvent.OnPrimaryButtonTapped -> {
                createTask()
                navigationStream.onNext(NavigationModel.NavigateBack)
            }
            is CreateTaskUiEvent.OnTitleChange -> {
                titleStream.onNext(uiEvent.newTitle)
            }
            is CreateTaskUiEvent.OnDescriptionChange -> {
                descriptionStream.onNext(uiEvent.newDescription)
            }
        }
    }

    private fun createTask() {
        Observable.combineLatest(
            titleStream.take(1),
            descriptionStream.take(1),
            ::Pair,
        ).flatMapSingle { (title, description) ->
            taskRepository.getNexTaskId().map { newTaskId ->
                Triple(title, description, newTaskId)
            }
        }.subscribe { (title, description, newTaskId) ->
            taskUpdateRepository.addTask(
                TaskDTO(
                    id = newTaskId,
                    title = title,
                    description = description,
                )
            )
        }.apply { disposables.add(this) }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.forEach {
            if (!it.isDisposed) it.dispose()
        }
    }
}
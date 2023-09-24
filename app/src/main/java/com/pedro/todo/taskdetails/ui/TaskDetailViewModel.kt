package com.pedro.todo.taskdetails.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.pedro.todo.data.dto.TaskDTO
import com.pedro.todo.data.repository.TaskUpdateRepository
import com.pedro.todo.navigation.VIEW_MODEL_ARGUMENTS
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val taskUpdateRepository: TaskUpdateRepository,
) : ViewModel() {

    private val disposables: MutableList<Disposable> = mutableListOf()
    private val taskDTO = savedStateHandle.get<TaskDTO>(VIEW_MODEL_ARGUMENTS)!!

    private val titleStream: BehaviorSubject<String> = BehaviorSubject.createDefault(taskDTO.title)
    private val descriptionStream: BehaviorSubject<String> =
        BehaviorSubject.createDefault(taskDTO.description)

    private val updateTaskStream = PublishSubject.create<Unit>()

    init {
        updateTaskStream.withLatestFrom(
            titleStream,
            descriptionStream,
            ::Triple,
        ).subscribe { (_, title, description) ->
            val updatedTask = TaskDTO(
                id = taskDTO.id,
                title = title,
                description = description,
            )
            taskUpdateRepository.updateTask(updatedTask)
        }.apply { disposables.add(this) }

        Observable.timer(5L, TimeUnit.SECONDS)
            .repeat()
            .subscribe {
                updateTaskStream.onNext(Unit)
            }.apply { disposables.add(this) }
    }

    fun buildStateStream(): Observable<TaskDetailUiState> {
        return Observable.combineLatest(
            titleStream.distinctUntilChanged(),
            descriptionStream.distinctUntilChanged(),
        ) { title, description ->
            TaskDetailUiState(
                title = title,
                description = description,
            )
        }
    }

    fun handleUiEvent(uiEvent: TaskDetailUiEvent) {
        when (uiEvent) {
            is TaskDetailUiEvent.OnTitleChange -> titleStream.onNext(uiEvent.newTitle)
            is TaskDetailUiEvent.OnDescriptionChange -> descriptionStream.onNext(uiEvent.newDescription)
        }
    }

    override fun onCleared() {
        super.onCleared()
        updateTaskStream.onNext(Unit)
        disposables.forEach {
            if (!it.isDisposed) it.dispose()
        }
    }
}
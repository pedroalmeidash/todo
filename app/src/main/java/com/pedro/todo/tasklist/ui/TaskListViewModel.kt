package com.pedro.todo.tasklist.ui

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.pedro.todo.data.repository.TaskRepository
import com.pedro.todo.navigation.NavigationModel
import com.pedro.todo.tasklist.mapper.TaskItemUiStateMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskItemUiStateMapper: TaskItemUiStateMapper,
) : ViewModel() {
    private val checkedTasksIdStream: BehaviorSubject<List<String>> = BehaviorSubject.createDefault(
        emptyList()
    )

    val navigationStream: PublishSubject<NavigationModel> = PublishSubject.create()

    fun buildUiState(): Observable<TaskListUiState> {
        return Observable.combineLatest(
            taskRepository.getAllTasks(),
            checkedTasksIdStream,
         ) { taskDTOList, checkedTasksId ->
            val tasks = taskDTOList.map { taskDTO ->
                taskItemUiStateMapper.mapToUiState(
                    taskDTO = taskDTO,
                    checkedTasksId = checkedTasksId,
                )
            }
            TaskListUiState(tasks)
        }
    }

    fun handleUiEvents(uiEvent: TaskListUiEvent) {
        when (uiEvent) {
            is TaskListUiEvent.OnCheckChanged -> {
                updateTaskCheckStatus(
                    taskId = uiEvent.taskId,
                )
            }
            is TaskListUiEvent.OnPrimaryButtonTapped -> {
                navigationStream.onNext(NavigationModel.CreateTask)
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun updateTaskCheckStatus(taskId: String) {
        checkedTasksIdStream
            .take(1)
            .subscribe { checkedTasksId ->
                val mutableCheckedTasksId = checkedTasksId.toMutableList()
                if (checkedTasksId.contains(taskId)) {
                    mutableCheckedTasksId.add(taskId)
                } else {
                    mutableCheckedTasksId.remove(taskId)
                }
                checkedTasksIdStream.onNext(mutableCheckedTasksId)
            }
    }
}
package com.pedro.todo.tasklist.ui

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.pedro.todo.repository.TaskRepository
import com.pedro.todo.tasklist.mapper.TaskItemUiStateMapper
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class TaskListViewModel(
    private val taskRepository: TaskRepository,
    private val taskItemUiStateMapper: TaskItemUiStateMapper,
) : ViewModel() {
    private val checkedTasksIdStream: BehaviorSubject<List<String>> = BehaviorSubject.createDefault(
        emptyList()
    )

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
package com.pedro.todo.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskDTO(
    val id: String,
    val title: String,
    val description: String,
) : Parcelable
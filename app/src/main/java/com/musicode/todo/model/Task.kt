package com.musicode.todo.model

import java.util.*

data class Task(
        var id: Long,
        var name: String,
        var isDone: Boolean,
        var remindTime: Date?,
        var endTime: Date?,
        var note: String,
        var createTime: Date,
        var doneTime: Date?
)
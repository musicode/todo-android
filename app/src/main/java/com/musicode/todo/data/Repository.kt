package com.musicode.todo.data

import com.musicode.todo.model.Task
import java.util.*

object Repository {

    val tasks: ArrayList<Task> = ArrayList<Task>()

    fun addTask(task: Task) {
        tasks.add(task)
    }

    fun findTask(taskId: Long): Task? {
        return tasks.find { it.id == taskId }
    }

    fun updateTask(taskId: Long, name: String, isDone: Boolean, remindTime: Date?, endTime: Date?, note: String) {
        val task = findTask(taskId)
        if (task != null) {
            task.name = name
            task.isDone = isDone
            task.remindTime = remindTime
            task.endTime = endTime
            task.note = note
        }
    }

    fun deleteTask(taskId: Long) {
        val task = tasks.find { it.id == taskId }
        if (task != null) {
            tasks.remove(task)
        }
    }

}
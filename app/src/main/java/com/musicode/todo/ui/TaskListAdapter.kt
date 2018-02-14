package com.musicode.todo.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class TaskListAdapter(
        private val taskList: List<String>,
        private val onClick: () -> Unit
) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = View(parent?.context)
        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindTask(
                taskList[position]
        )
    }

    override fun getItemCount(): Int = taskList.size


    class ViewHolder(view: View, onClick: () -> Unit) : RecyclerView.ViewHolder(view) {

        fun bindTask(task: String) {

        }

    }
}
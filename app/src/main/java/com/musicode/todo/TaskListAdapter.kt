package com.musicode.todo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.musicode.todo.model.Task
import kotlinx.android.synthetic.main.task_item.view.*

class TaskListAdapter(
        private val taskList: List<Task>,
        private val onClick: (task: Task) -> Unit
) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.task_item, parent, false)
        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindTask(
            taskList[position]
        )
    }

    override fun getItemCount(): Int = taskList.size

    class ViewHolder(view: View, onClick: (task: Task) -> Unit) : RecyclerView.ViewHolder(view) {

        lateinit var task: Task

        init {
            with (view) {
                done_checkbox.setOnClickListener {
                    task.isDone = !task.isDone
                    setDone(task.isDone)
                }
                name_button.setOnClickListener {
                    onClick(task)
                }
            }
        }

        fun bindTask(task: Task) {
            this.task = task
            setDone(task.isDone)
            itemView.name_button.text = task.name
        }

        fun setDone(isDone: Boolean) {
            var resourceId = R.drawable.ic_radio_button_unchecked_black_24dp
            if (isDone) {
                resourceId = R.drawable.ic_radio_button_checked_black_24dp
            }
            itemView.done_checkbox.setImageResource(resourceId)
        }

    }
}
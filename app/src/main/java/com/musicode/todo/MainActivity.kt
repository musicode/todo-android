package com.musicode.todo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.musicode.todo.data.Repository
import com.musicode.todo.model.Task
import com.musicode.todo.TaskListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_TASK_FORM: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Repository.addTask(Task(1, "1", false, Date(), Date(), "", Date(), Date()))
        Repository.addTask(Task(2, "2", false, Date(), Date(), "", Date(), Date()))
        Repository.addTask(Task(3, "3", false, Date(), Date(), "", Date(), Date()))
        Repository.addTask(Task(4, "4", false, Date(), Date(), "", Date(), Date()))
        Repository.addTask(Task(5, "5", false, Date(), Date(), "", Date(), Date()))
        Repository.addTask(Task(6, "6", false, Date(), Date(), "", Date(), Date()))
        Repository.addTask(Task(7, "7", false, Date(), Date(), "", Date(), Date()))
        Repository.addTask(Task(8, "8", false, Date(), Date(), "", Date(), Date()))
        Repository.addTask(Task(9, "9", false, Date(), Date(), "", Date(), Date()))
        Repository.addTask(Task(10, "10", false, Date(), Date(), "", Date(), Date()))
        Repository.addTask(Task(11, "11", false, Date(), Date(), "", Date(), Date()))
        Repository.addTask(Task(12, "12", false, Date(), Date(), "", Date(), Date()))
        Repository.addTask(Task(13, "13", false, Date(), Date(), "", Date(), Date()))
        Repository.addTask(Task(14, "14", false, Date(), Date(), "", Date(), Date()))

        initToolbar()

        initRecycleView()

    }

    private fun initToolbar() {

        setSupportActionBar(tool_bar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun initRecycleView() {

        task_list.adapter = TaskListAdapter(Repository.tasks) {
            val bundle = Bundle()
            bundle.putLong("taskId", it.id)

            val intent = Intent(this, TaskFormActivity::class.java)
            intent.putExtras(bundle)

            startActivityForResult(intent, REQUEST_CODE_TASK_FORM)
        }

        task_list.layoutManager = LinearLayoutManager(this)

        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.task_list_divider))

        task_list.addItemDecoration(divider)

    }

}

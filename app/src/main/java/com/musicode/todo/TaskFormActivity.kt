package com.musicode.todo

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.musicode.todo.data.Repository
import com.musicode.todo.model.Task
import kotlinx.android.synthetic.main.activity_task_form.*
import java.text.SimpleDateFormat
import java.util.*

class TaskFormActivity : AppCompatActivity() {

    private val REQUEST_CODE_NOTE = 1

    private var taskId: Long? = null

    private var task: Task? = null

    /**
     * 是否已标记完成
     *
     * @type {Boolean}
     */
    private var isDone = false

    /**
     * 提醒时间
     *
     * @type {Calendar}
     */
    private var remindTime: Calendar? = null

    /**
     * 截止时间
     *
     * @type {Calendar}
     */
    private var endTime: Calendar? = null

    /**
     * 备注
     *
     * @type {String}
     */
    private var note: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        initData()
        initToolbar()

        remind_time_button.setOnClickListener {
            val time = remindTime ?: Calendar.getInstance()
            DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { datePicker, year, month, date ->
                        setRemindTime(year, month, date)
                    },
                    time.get(Calendar.YEAR),
                    time.get(Calendar.MONTH),
                    time.get(Calendar.DATE)
            ).show()
        }

        end_time_button.setOnClickListener {
            val time = endTime ?: Calendar.getInstance()
            DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { datePicker, year, month, date ->
                        setEndTime(year, month, date)
                    },
                    time.get(Calendar.YEAR),
                    time.get(Calendar.MONTH),
                    time.get(Calendar.DATE)
            ).show()
        }

        note_button.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("value", note)

            val intent = Intent(this, NoteFormActivity::class.java)
            intent.putExtras(bundle)

            startActivityForResult(intent, REQUEST_CODE_NOTE)

        }

        delete_button.setOnClickListener {

        }

        done_checkbox.setOnClickListener {
            setDone(!isDone)
            updateStatus()
        }
    }


    private fun initData() {
        if (intent != null) {
            val bundle = intent.extras
            if (bundle != null) {
                val taskId = bundle.getLong("taskId")
                val task = Repository.findTask(taskId)
                if (task != null) {
                    this.taskId = taskId
                    name_input.setText(task.name)
                    note_button.setText(task.note)
                    setDone(task.isDone)
                    updateStatus()
                    val remindTime = task.remindTime
                    if (remindTime != null) {
                        setRemindTime(remindTime.year, remindTime.month, remindTime.date)
                    }
                    val endTime = task.endTime
                    if (endTime != null) {
                        setEndTime(endTime.year, endTime.month, endTime.date)
                    }
                    return
                }
            }
        }
        setDone(isDone)
        updateStatus()
    }

    private fun initToolbar() {

        setSupportActionBar(tool_bar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    fun setDone(done: Boolean) {
        if (done) {
            name_input.paintFlags = name_input.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            done_checkbox.setImageResource(R.drawable.ic_radio_button_checked_black_24dp)
        }
        else {
            name_input.paintFlags = name_input.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            done_checkbox.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp)
        }
        this.isDone = done
    }

    fun updateStatus() {
        val id = taskId
        if (id != null) {
            val task = Repository.findTask(id)
            if (task != null) {
                val doneTime = task.doneTime
                if (task.isDone && doneTime != null) {
                    status_text.text = "完成于：" + formatTime(doneTime)
                }
                else {
                    status_text.text = "创建于：" + formatTime(task.createTime)
                }
                return
            }
        }
        status_bar.visibility = View.GONE
    }

    fun formatTime(date: Date): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:MM", Locale.CHINA)
        return formatter.format(date)
    }

    fun setRemindTime(year: Int, month: Int, date: Int) {
        val calendar = remindTime ?: Calendar.getInstance()
        calendar.set(year, month, date)

        remindTime = calendar
        remind_time_button.text = formatTime(calendar.time)
    }

    fun setEndTime(year: Int, month: Int, date: Int) {
        val calendar = endTime ?: Calendar.getInstance()
        calendar.set(year, month, date)

        endTime = calendar
        end_time_button.text = formatTime(calendar.time)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_task_form, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.save -> {
                if (taskId != null) {

                }

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_NOTE -> {
                if (resultCode === RESULT_OK && data != null) {
                    note = data.extras.getString("value")
                    note_button.setText(note)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}

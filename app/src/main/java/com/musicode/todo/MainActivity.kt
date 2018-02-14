package com.musicode.todo

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.fragment_task_form.*
import org.jetbrains.anko.contentView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var done = false

    private var remindDate: Calendar? = null

    private lateinit var inputMethodManager: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_task_form)

        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        end_date_button.setOnClickListener {
            var date = remindDate
            if (date == null) {
                date = Calendar.getInstance()
            }
            DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { datePicker, year, month, date ->
                        setRemindDate(year, month, date)
                    },
                    date!!.get(Calendar.YEAR),
                    date!!.get(Calendar.MONTH),
                    date!!.get(Calendar.DATE)
            ).show()


            val view = contentView
            if (inputMethodManager.isActive && view != null) {
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
        done_checkbox.setOnClickListener {
            setDone(!done)
        }
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
        this.done = done
    }

    fun setRemindDate(year: Int, month: Int, date: Int) {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        if (remindDate == null) {
            remindDate = Calendar.getInstance()
        }
        remindDate?.set(year, month, date)

        val string = formatter.format(remindDate?.time)
        end_date_button.text = string
    }

}

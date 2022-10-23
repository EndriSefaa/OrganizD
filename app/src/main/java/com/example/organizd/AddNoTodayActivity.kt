package com.example.organizd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.organizd.ViewModels.TaskViewModel
import com.example.organizd.ViewModels.TaskViewModelFactory
import com.example.organizd.databinding.ActivityAddBinding
import com.example.organizd.databinding.ActivityAddNoTodayBinding
import com.example.organizd.db.Task
import java.text.SimpleDateFormat
import java.util.*

class AddNoTodayActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddNoTodayBinding
    lateinit var app : String
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_no_today)

        binding = ActivityAddNoTodayBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.timePicker1.setIs24HourView(true)

        app = intent.getStringExtra("EXTRA_DATE").toString()

        if(app != null){
            binding.textView5.setText("Task for " + app)
        }

        val taskViewModelFactory = TaskViewModelFactory(application, app)
        taskViewModel = ViewModelProvider(this, taskViewModelFactory).get(TaskViewModel::class.java)

        binding.button2.setOnClickListener {
            finish()
        }

        val buttonAdd: Button = findViewById(R.id.button3)

        buttonAdd.setOnClickListener {
            if(app != null){
                insertToDatabase()
                finish()
            }


        }



    }


    private fun insertToDatabase(){

        val taskName = binding.editTextTaskName.text.toString()
        val hour = binding.timePicker1.hour.toString()
        val minutes = binding.timePicker1.minute.toString()
        val orario: String = hour + ":" + minutes

        if (inputCheck(hour, minutes, taskName)){

            val task = Task(0, app, taskName,  orario, false)
            taskViewModel.addTask(task)
            Toast.makeText(
                this,
                "Task Added",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun inputCheck(hour: String, minutes: String, taskName: String): Boolean{
        if(taskName == "") {
            Toast.makeText(
                this,
                "Write what task you havo to do :)",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }


        return true

    }
}
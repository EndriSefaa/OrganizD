package com.example.organizd

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.organizd.ViewModels.TaskViewModel
import com.example.organizd.ViewModels.TaskViewModelFactory
import com.example.organizd.databinding.ActivityAddBinding
import com.example.organizd.db.Task
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddBinding
    lateinit var app : String
    private lateinit var taskViewModel: TaskViewModel




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityAddBinding.inflate(layoutInflater)
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

                }


        }



    }


    private fun insertToDatabase(){

        val taskName = binding.editTextTaskName.text.toString()
        var hour = binding.timePicker1.hour.toString()
        var minutes = binding.timePicker1.minute.toString()
        if (minutes.length == 1){
            minutes = "0" + minutes
        }
        if (hour.length == 1){
            hour = "0" + hour
        }
        val orario: String = hour + ":" + minutes

        if (inputCheck(hour, minutes, taskName)){

            val task = Task(0, app, taskName,  orario, false)
            taskViewModel.addTask(task)


            var pref = this.getSharedPreferences("pref", Context.MODE_PRIVATE)
            var notDoneTask = pref.getInt("NOTDONE", 0)
            notDoneTask++
            pref.edit().putInt("NOTDONE", notDoneTask).apply()

            Toast.makeText(
                    this,
            "Task Added",
            Toast.LENGTH_SHORT
            ).show()
            finish()
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

        val hf = SimpleDateFormat("HH")
        val mf = SimpleDateFormat("mm")
        val currentHour = hf.format(Date()).toInt()
        val currentMinutes = mf.format(Date()).toInt()


            if(currentHour > hour.toInt()){
                Toast.makeText(
                    this,
                    "Time selected is not correct. Select new time",
                Toast.LENGTH_SHORT
                ).show()
                return false
            }
        if(currentHour == hour.toInt() && currentMinutes > minutes.toInt()){
            Toast.makeText(
                this,
                "Time selected is not correct. Select new time",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }


}
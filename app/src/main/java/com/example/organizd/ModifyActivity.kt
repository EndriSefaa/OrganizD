package com.example.organizd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.organizd.ViewModels.SpecificTaskViewModel
import com.example.organizd.ViewModels.SpecificTaskViewModelFactory
import com.example.organizd.ViewModels.TaskViewModel
import com.example.organizd.ViewModels.TaskViewModelFactory
import com.example.organizd.databinding.ActivityAddBinding
import com.example.organizd.databinding.ActivityModifyBinding
import com.example.organizd.db.Task
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class ModifyActivity : AppCompatActivity() {

    lateinit var binding: ActivityModifyBinding
    var app by Delegates.notNull<Int>()
    private lateinit var taskViewModel: SpecificTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityModifyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.timePicker1.setIs24HourView(true)

        app = intent.getIntExtra("EXTRA_ID", 0)
        if(app != null){
            binding.textView5.setText("Task for " + app)
        }

        val taskViewModelFactory = SpecificTaskViewModelFactory(application, app)
        taskViewModel = ViewModelProvider(this, taskViewModelFactory).get(SpecificTaskViewModel::class.java)

        binding.buttonBack.setOnClickListener {
            finish()
        }

       binding.buttonUpdate.setOnClickListener {
                insertToDatabase()
                finish()
            }


        }



    private fun insertToDatabase(){

        val taskName = binding.editTextTaskName.text.toString()
        val hour = binding.timePicker1.hour.toString()
        val minutes = binding.timePicker1.minute.toString()
        val orario: String = hour + ":" + minutes

        if (inputCheck(hour, minutes, taskName)){

            val task = Task(app, taskViewModel.specificTask.date, taskName,  orario, false)
            taskViewModel.update(task)
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

        val hf = SimpleDateFormat("HH")
        val mf = SimpleDateFormat("mm")
        val currentHour = hf.format(Date()).toString()
        val currentMinutes = mf.format(Date()).toString()

        if((currentHour == hour && currentMinutes > minutes) || (currentHour > hour) ){
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
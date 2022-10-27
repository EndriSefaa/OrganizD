package com.example.organizd

import android.content.Context
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

        var pref = this.getSharedPreferences("pref", Context.MODE_PRIVATE)
        var savedLevel = pref.getInt("LEVEL", 0)



        binding = ActivityModifyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.timePicker1.setIs24HourView(true)


        app = intent.getIntExtra("EXTRA_ID", 0)


        val taskViewModelFactory = SpecificTaskViewModelFactory(application, app)
        taskViewModel = ViewModelProvider(this, taskViewModelFactory).get(SpecificTaskViewModel::class.java)

        if(app != null){
            binding.textView5.setText("Task for " + (taskViewModel.specificTask?.date))
            binding.editTextTaskName.setText(taskViewModel.specificTask?.name ?: "")
        }
        println(taskViewModel.specificTask?.hour.toString().substringBefore(":").toInt())
        println(taskViewModel.specificTask?.hour.toString().substringAfter(":").toInt())
        binding.timePicker1.hour = taskViewModel.specificTask?.hour.toString().substringBefore(":").toInt()
        binding.timePicker1.minute = taskViewModel.specificTask?.hour.toString().substringAfter(":").toInt()
        binding.buttonBack.setOnClickListener {
            finish()
        }

       binding.buttonUpdate.setOnClickListener {
                insertToDatabase()
                finish()
            }


        binding.buttonCancel.setOnClickListener {
            val taskName = binding.editTextTaskName.text.toString()
            val hour = binding.timePicker1.hour.toString()
            val minutes = binding.timePicker1.minute.toString()
            val orario: String = hour + ":" + minutes
            val task = Task(app, taskViewModel.specificTask?.date ?: "", taskName,  orario, false)
            taskViewModel.delete(task)

            Toast.makeText(
                this,
                "Task Deleted",
                Toast.LENGTH_SHORT
            ).show()

            finish()
        }
        binding.buttonComplete.setOnClickListener {
            val taskName = binding.editTextTaskName.text.toString()
            val hour = binding.timePicker1.hour.toString()
            var minutes = binding.timePicker1.minute.toString()
            println(hour)
            println(minutes)
            if (minutes.length == 1){
                minutes = "0" + minutes
            }
            val orario: String = hour + ":" + minutes
            val task = Task(app, taskViewModel.specificTask?.date ?: "", taskName,  orario, true)
            taskViewModel.update(task)

            savedLevel++
            pref.edit().putInt("LEVEL", savedLevel).apply()
            println(savedLevel)

            Toast.makeText(
                this,
                "Task Completed",
                Toast.LENGTH_SHORT
            ).show()

            finish()
        }


        }



    private fun insertToDatabase(){

        val taskName = binding.editTextTaskName.text.toString()
        val hour = binding.timePicker1.hour.toString()
        var minutes = binding.timePicker1.minute.toString()
        println(hour)
        println(minutes)
        if (minutes.length == 1){
            minutes = "0" + minutes
        }
        val orario: String = hour + ":" + minutes

        if (inputCheck(hour, minutes, taskName)){

            val task = Task(app, taskViewModel.specificTask?.date ?: "", taskName,  orario, false)
            taskViewModel.update(task)
            Toast.makeText(
                this,
                "Task Updated",
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
        println("MInuti Correnti:  " +currentMinutes )
        println("MInuti counter:  " + minutes )

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
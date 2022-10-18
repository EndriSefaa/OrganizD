package com.example.organizd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import com.example.organizd.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val button: Button = findViewById(R.id.button2)
        val timePicker: TimePicker = findViewById(R.id.timePicker1)
        timePicker.setIs24HourView(true)
        val text : TextView = findViewById(R.id.textView5)
        val app = intent.getStringExtra("EXTRA_DATE")
        text.setText("Task for " + app)

        button.setOnClickListener {
            finish()
        }

    }
}
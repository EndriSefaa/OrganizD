package com.example.organizd

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.organizd.Adapters.TaskCalendarPastAdapter
import com.example.organizd.Adapters.TasksListAdapter
import com.example.organizd.ViewModels.TaskViewModel
import com.example.organizd.ViewModels.TaskViewModelFactory
import com.example.organizd.databinding.FragmentCalendarBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    lateinit var binding: FragmentCalendarBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(layoutInflater)
        val view = binding.root


        var adapter = TaskCalendarPastAdapter()
        binding.recyclerViewCalendar.adapter = adapter
        binding.recyclerViewCalendar.layoutManager = LinearLayoutManager(requireContext())



        //return super.onCreateView(inflater, container, savedInstanceState)
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        var date: String = currentDate.toString()
        binding.textCurrentDate.setText(date)

        var taskViewModelFactory = TaskViewModelFactory(this.activity!!.application, date)
        taskViewModel = ViewModelProvider(this, taskViewModelFactory).get(TaskViewModel::class.java)

        taskViewModel.redAllDoData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {  task ->
            adapter.setData(task)
        })



        binding.calendarView.setOnDateChangeListener(CalendarView.OnDateChangeListener{view, year, month, dayOfMonth ->

            date = (dayOfMonth.toString() + "-" + (month + 1) + "-" + year)



            binding.textCurrentDate.setText(date)

            var adapter2 = TaskCalendarPastAdapter()
            binding.recyclerViewCalendar.swapAdapter(adapter2, true);
            binding.recyclerViewCalendar.adapter = adapter2
            binding.recyclerViewCalendar.layoutManager = LinearLayoutManager(requireContext())
            var taskViewModelFactory2 = TaskViewModelFactory(this.activity!!.application, date)
            var taskViewModel2 = ViewModelProvider(this, taskViewModelFactory2).get(TaskViewModel::class.java)
            println(taskViewModel2.redAllDoData)

            taskViewModel2.redAllDoData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {  task ->
                adapter2.setData(task)
            })



        })


        binding.button.setOnClickListener {

            if (!checkDate(currentDate.toString() , date)){
                Toast.makeText(
                    this.requireContext(),
                    "Time selected is not correct. Select new time",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else {

                var intent = Intent()


                if(date != currentDate.toString()){
                    intent = Intent(requireContext(), AddNoTodayActivity::class.java)
                    intent.putExtra("EXTRA_DATE", date)
                }else{
                    intent = Intent(requireContext(), AddActivity::class.java)
                    intent.putExtra("EXTRA_DATE", date)
                }

                startActivity(intent)
            }
        }

        binding.infoButton.setOnClickListener{
            val  dialogBinding = layoutInflater.inflate(R.layout.calendar_dialog, null)

            val myDialog = Dialog(requireContext())
            myDialog.setContentView(dialogBinding)

            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()
        }

        return view
    }

    private fun checkDate(current: String, selected: String): Boolean{
        var cday = current.substringBefore("-").toInt()
        var app =  current.substringAfter("-")
        var cmonth = app.substringBefore("-").toInt()
        var cyear = app.substringAfter("-").toInt()

        var sday = selected.substringBefore("-").toInt()
        var app1 =  selected.substringAfter("-")
        var smonth = app1.substringBefore("-").toInt()
        var syear = app1.substringAfter("-").toInt()

        if(cyear > syear) return false
        if(cyear == syear && cmonth > smonth) return false
        if(cyear == syear && cmonth == smonth && cday > sday) return false
        return true
    }

}
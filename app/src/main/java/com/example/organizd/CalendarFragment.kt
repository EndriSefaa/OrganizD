package com.example.organizd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import com.example.organizd.databinding.FragmentCalendarBinding

class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    lateinit var binding: FragmentCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(layoutInflater)
        val view = binding.root

        //return super.onCreateView(inflater, container, savedInstanceState)


        binding.calendarView.setOnDateChangeListener(CalendarView.OnDateChangeListener{view, year, month, dayOfMonth ->

            val Date = (dayOfMonth.toString() + "-" + (month + 1) + "-" + year)

            binding.textCurrentDate.setText(Date)
        })

        return view
    }



}
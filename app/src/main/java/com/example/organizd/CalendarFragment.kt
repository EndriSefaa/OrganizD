package com.example.organizd

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import com.example.organizd.databinding.FragmentCalendarBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

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
        val app: LocalDate = LocalDate.now()
        var date: String = app.toString()
        binding.textCurrentDate.setText(date)

        binding.calendarView.setOnDateChangeListener(CalendarView.OnDateChangeListener{view, year, month, dayOfMonth ->

            date = (dayOfMonth.toString() + "-" + (month + 1) + "-" + year)

            binding.textCurrentDate.setText(date)
        })


        binding.button.setOnClickListener {

            val intent = Intent(requireContext(), AddActivity::class.java)

            intent.putExtra("EXTRA_DATE", date)

            startActivity(intent)
        }

        return view
    }



}
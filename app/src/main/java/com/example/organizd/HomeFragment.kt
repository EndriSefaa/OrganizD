package com.example.organizd

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.organizd.Adapters.TasksListAdapter
import com.example.organizd.databinding.FragmentHomeBinding
import com.example.organizd.db.Task
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.Player
import com.google.android.material.snackbar.Snackbar
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var binding: FragmentHomeBinding
    var tasksList = mutableListOf<Task>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        val view = binding.root

        tasksList = mutableListOf(
            Task(1, "", "pino", ""),
            Task(1, "", "pino", ""),
            Task(1, "", "pino", ""),
            Task(1, "", "pino", ""),
            Task(1, "", "pino", ""),
            Task(1, "", "pino", "")
        )


        val adapter = TasksListAdapter(tasksList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var tasksList = mutableListOf(
            Task(1, "", "pino", ""),
            Task(1, "", "pino", ""),
            Task(1, "", "pino", ""),
            Task(1, "", "pino", ""),
            Task(1, "", "pino", ""),
            Task(1, "", "pino", "")
        )

        val adapter = TasksListAdapter(tasksList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.floatingActionButton.setOnClickListener {
            val name = binding.etTask.text.toString()
            val todo = Task(2, "", name, "")
            println("tass sei bellissimo")
            tasksList.add(todo)
            //adapter.notifyDataSetChanged() non efficiente
            adapter.notifyItemInserted(tasksList.size - 1)
        }



        // Giorno mese anno nella home.

        val currentDate = LocalDate.now()

        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        val formattedDate = currentDate.format(formatter)

        binding.Data.text = formattedDate

        swipeToDelete()

    }


    // Funzione di swipe per rimuovere le task

    private fun swipeToDelete()
    {

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.adapterPosition
                tasksList.removeAt(position)


                Toast.makeText(this@HomeFragment.requireActivity(), "Task eliminata.", Toast.LENGTH_LONG)
            }


        }).attachToRecyclerView(binding.recyclerView)

    }

}



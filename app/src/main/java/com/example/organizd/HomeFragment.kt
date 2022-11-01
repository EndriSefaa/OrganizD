package com.example.organizd

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.organizd.Adapters.TaskCalendarPastAdapter
import com.example.organizd.Adapters.TasksListAdapter
import com.example.organizd.ViewModels.TaskViewModel
import com.example.organizd.ViewModels.TaskViewModelFactory
import com.example.organizd.databinding.FragmentHomeBinding
import com.example.organizd.db.Task
import org.jetbrains.annotations.TestOnly
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

@Suppress("DEPRECATION")
class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var binding: FragmentHomeBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        val view = binding.root

        val intent = Intent(requireContext(), AddActivity::class.java)

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date()).toString()






        val adapter = TasksListAdapter{ task -> adapterOnClick(task) }
        val adapter2 = TaskCalendarPastAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerDone.adapter = adapter2
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerDone.layoutManager = LinearLayoutManager(requireContext())



        val taskViewModelFactory = TaskViewModelFactory(this.activity!!.application, currentDate)
        taskViewModel = ViewModelProvider(this, taskViewModelFactory).get(TaskViewModel::class.java)



        taskViewModel.redAllDoData?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { task ->
            adapter.setData(task)

        })

        taskViewModel.redAllDoneData?.observe(viewLifecycleOwner, androidx.lifecycle.Observer { task ->
            adapter2.setData(task)

        })




        registerForContextMenu(binding.recyclerView)
        binding.floatingActionButton.setOnClickListener {

            intent.putExtra("EXTRA_DATE", currentDate)

            startActivity(intent)

        }

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Giorno mese anno nella home.

        val currentDate = LocalDate.now()

        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        val formattedDate = currentDate.format(formatter)

        binding.Data.text = formattedDate

        binding.infoButton.setOnClickListener{
            val  dialogBinding = layoutInflater.inflate(R.layout.home_dialog, null)

            val myDialog = Dialog(requireContext())
            myDialog.setContentView(dialogBinding)

            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()
        }

    }


    private fun adapterOnClick(task: Task){
        val intent = Intent(this.context, ModifyActivity()::class.java)
        intent.putExtra("EXTRA_ID", task.id)
        startActivity(intent)
    }




}



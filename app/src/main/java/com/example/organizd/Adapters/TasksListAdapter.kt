package com.example.organizd.Adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.ListFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.organizd.AddActivity
import com.example.organizd.ModifyActivity
import com.example.organizd.R
import com.example.organizd.db.Task
import com.google.android.material.snackbar.Snackbar

class TasksListAdapter(private val onClick: (Task)-> Unit) : RecyclerView.Adapter<TasksListAdapter.TaskViewHolder>() {

    private var taskList = emptyList<Task>()


    class TaskViewHolder(row: View, val onClick: (Task) -> Unit) : RecyclerView.ViewHolder(row){
        val taskView = row.findViewById<TextView>(R.id.taskName)
        val timeView = row.findViewById<TextView>(R.id.textHour)
        val imageView = row.findViewById<ImageView>(R.id.edit_button)
        private var currentTask: Task? = null

        init{
            imageView.setOnClickListener{
                currentTask?.let{
                    onClick(it)
                }
            }
        }
        fun bind(task: Task){
            currentTask = task
            taskView.text = currentTask!!.name
            timeView.text = currentTask!!.hour



        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {



        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_todo, parent, false)
        val holder = TaskViewHolder(layout, onClick)
        holder.taskView.setOnClickListener {
            Snackbar.make(parent.rootView, "Click!", Snackbar.LENGTH_SHORT).show()
        }



        return holder
    }



    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = taskList[position]
        holder.bind(currentTask)

    }

    fun setData(task: List<Task>){
        this.taskList = task
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return taskList.size
    }


}
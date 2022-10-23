package com.example.organizd.Adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.ListFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.organizd.R
import com.example.organizd.db.Task
import com.google.android.material.snackbar.Snackbar

class TasksListAdapter : RecyclerView.Adapter<TasksListAdapter.TaskViewHolder>() {

    private var taskList = emptyList<Task>()

    class TaskViewHolder(row: View) : RecyclerView.ViewHolder(row){
        val taskView = row.findViewById<TextView>(R.id.taskName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {



        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_todo, parent, false)
        val holder = TaskViewHolder(layout)
        holder.taskView.setOnClickListener {
            Snackbar.make(parent.rootView, "Click!", Snackbar.LENGTH_SHORT).show()
        }


        return holder
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = taskList[position]
        holder.taskView.text = currentTask.name

    }

    fun setData(task: List<Task>){
        this.taskList = task
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return taskList.size
    }


}
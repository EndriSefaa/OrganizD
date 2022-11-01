package com.example.organizd.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.organizd.R
import com.example.organizd.db.Task
import com.google.android.material.snackbar.Snackbar

class TaskCalendarPastAdapter: RecyclerView.Adapter<TaskCalendarPastAdapter.TaskCalendarViewHolder>() {

    private var taskList = emptyList<Task>()

    class TaskCalendarViewHolder(row: View) : RecyclerView.ViewHolder(row){
        val taskView = row.findViewById<TextView>(R.id.taskName)
        val timeView = row.findViewById<TextView>(R.id.textHour)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskCalendarViewHolder {



        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_calendar_done, parent, false)
        val holder = TaskCalendarViewHolder(layout)
        return holder
    }

    override fun onBindViewHolder(holder: TaskCalendarViewHolder, position: Int) {
        val currentTask = taskList[position]
        holder.taskView.text = currentTask.name
        holder.timeView.text = currentTask.hour

    }

    fun setData(task: List<Task>){
        this.taskList = task
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
    fun clear(){
        this.taskList = emptyList<Task>()
        notifyDataSetChanged()
    }
}
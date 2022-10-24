package com.example.organizd.db

import androidx.lifecycle.LiveData
import java.util.*

class TaskRepository(private val taskDao: TaskDao) {

     fun readDayDoTask(day: String): LiveData<List<Task>> = taskDao.readDayDoTasks(day)

    fun readDayDoneTask(day: String): LiveData<List<Task>> = taskDao.readDayDoneTasks(day, true)

    fun readSpecificTask(id: Int): LiveData<Task> = taskDao.readSpecificTask(id)

    suspend fun addTask(task: Task){
        taskDao.addTask(task)
    }

    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }
}
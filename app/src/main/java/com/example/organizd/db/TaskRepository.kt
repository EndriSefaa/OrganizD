package com.example.organizd.db

import androidx.lifecycle.LiveData
import java.util.*

class TaskRepository(private val taskDao: TaskDao) {

     fun readDayTask(day: String): LiveData<List<Task>> = taskDao.readDayTasks(day)

    suspend fun addTask(task: Task){
        taskDao.addTask(task)
    }
}
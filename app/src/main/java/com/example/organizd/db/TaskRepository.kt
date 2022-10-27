package com.example.organizd.db

import androidx.lifecycle.LiveData
import java.util.*

class TaskRepository(private val taskDao: TaskDao) {

     fun readDayDoTask(day: String): LiveData<List<Task>> = taskDao.readDayDoTasks(day, false)

    fun readDayDoneTask(day: String): LiveData<List<Task>> = taskDao.readDayDoneTasks(day, true)

    fun readAllDayTask(day: String): LiveData<List<Task>> = taskDao.readAllDayTasks(day)

    fun readSpecificTask(id: Int): Task = taskDao.readSpecificTask(id)

    suspend fun addTask(task: Task){
        taskDao.addTask(task)
    }

    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task){
        taskDao.deleteTask(task)
    }
}
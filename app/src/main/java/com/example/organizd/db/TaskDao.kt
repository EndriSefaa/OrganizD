package com.example.organizd.db

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*


@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: Task)

    @Query("SELECT * FROM task_table WHERE date = :date AND completed = :completed ORDER BY hour ASC")
    fun  readDayDoTasks(date: String, completed: Boolean): LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE date = :date AND completed = :completed  ORDER BY  hour ASC")
    fun  readDayDoneTasks(date: String, completed: Boolean): LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE id = :id")
    fun  readSpecificTask(id: Int): Task

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)


}
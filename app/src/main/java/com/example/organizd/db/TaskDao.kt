package com.example.organizd.db

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*


@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: Task)

    @Query("SELECT * FROM task_table WHERE date = :date  ORDER BY  id ASC")
    fun  readDayTasks(date: String): LiveData<List<Task>>


}
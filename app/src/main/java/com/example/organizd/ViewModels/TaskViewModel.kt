package com.example.organizd.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.organizd.db.Task
import com.example.organizd.db.TaskDatabase
import com.example.organizd.db.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.collections.emptyList as emptyList

class TaskViewModel(application: Application, date: String): AndroidViewModel(application) {

    var redAllDoData: LiveData<List<Task>>? =  null
    var redAllDoneData: LiveData<List<Task>>? = null
    var allTasks : LiveData<List<Task>>? = null
    private val repository: TaskRepository

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)

        val job = CoroutineScope(Dispatchers.IO).launch {
            redAllDoData = repository.readDayDoTask(date)
            redAllDoneData = repository.readDayDoneTask(date)
            allTasks = repository.readAllDayTask(date)
        }
        runBlocking {
            job.join()
        }
    }

    fun addTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(task)
        }
    }

    fun modifyTask(date: String){
        allTasks=  null
        val job = viewModelScope.launch(Dispatchers.IO) {
            allTasks = repository.readAllDayTask(date)
        }
        runBlocking {
            job.join()
        }
    }
}
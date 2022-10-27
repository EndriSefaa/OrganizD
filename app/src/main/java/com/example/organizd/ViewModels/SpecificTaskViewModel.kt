package com.example.organizd.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.organizd.db.Task
import com.example.organizd.db.TaskDatabase
import com.example.organizd.db.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SpecificTaskViewModel(application: Application, id: Int): AndroidViewModel(application) {

    var specificTask: Task? = Task(0,"","","",false)
    private val repository: TaskRepository

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        val job = CoroutineScope(Dispatchers.IO).launch {
            specificTask = repository.readSpecificTask(id)
        }
        runBlocking {
            job.join()
        }

    }

    fun update(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)
        }
    }

    fun delete(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
        }
    }
}
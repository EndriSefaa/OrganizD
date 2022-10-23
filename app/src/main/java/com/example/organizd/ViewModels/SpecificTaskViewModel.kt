package com.example.organizd.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.organizd.db.Task
import com.example.organizd.db.TaskDatabase
import com.example.organizd.db.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SpecificTaskViewModel(application: Application, id: Int): AndroidViewModel(application) {

    val specificTask: Task
    private val repository: TaskRepository

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        specificTask = repository.readSpecificTask(id)
    }

    fun update(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)
        }
    }
}
package com.example.organizd.ViewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class SpecificTaskViewModelFactory(private var application: Application, private var id: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SpecificTaskViewModel::class.java)) {
            return SpecificTaskViewModel(application , id) as T
        }
        throw IllegalArgumentException("ViewModelClass Not found")
    }
}
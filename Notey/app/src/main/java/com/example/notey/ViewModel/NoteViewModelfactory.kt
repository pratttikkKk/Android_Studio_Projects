package com.example.notey.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notey.Repository.NotesRepository

class NoteViewModelfactory(private val repository: NotesRepository) : ViewModelProvider.Factory{

    //if your ViewModel requires additional parameters,
    //such as 'repository' or a 'context' , you need to create
    // a 'viewmodelProvider'.factory' to handle the instantiation

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException("unknown view model class")
    }
}
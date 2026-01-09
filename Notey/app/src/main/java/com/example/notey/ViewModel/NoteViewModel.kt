package com.example.notey.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notey.Repository.NotesRepository
import com.example.notey.roomdb.Note
import kotlinx.coroutines.launch

//view model stores & manage UI related data
//separating the UI related logic from
//UI controller (composable/Activity/Frag)

class NoteViewModel(private val repository: NotesRepository) : ViewModel(){   // INJECTED REPOSITORY
    val allNotes : LiveData<List<Note>> = repository.allNotes

    fun insert(note: Note)=
        viewModelScope.launch {
            repository.insertNote(note)
        }

}
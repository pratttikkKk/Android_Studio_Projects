package com.example.notesapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotesViewModel : ViewModel() {

    private val repo = ServiceLocator.repository

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                _notes.value = repo.fetchNotes()
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
            } finally {
                _loading.value = false
            }
        }
    }

    fun add(text: String) {
        if (text.isBlank()) return
        viewModelScope.launch {
            try {
                val created = repo.addNote(text.trim())
                _notes.value = listOf(created) + _notes.value
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun update(id: String, text: String) {
        if (text.isBlank()) return
        viewModelScope.launch {
            try {
                val updated = repo.editNote(id, text.trim())
                _notes.value = _notes.value.map { if (it.id == id) updated else it }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun delete(id: String) {
        viewModelScope.launch {
            try {
                repo.removeNote(id)
                _notes.value = _notes.value.filterNot { it.id == id }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}

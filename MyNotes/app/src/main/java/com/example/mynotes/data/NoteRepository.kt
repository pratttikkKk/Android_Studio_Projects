package com.example.notesapp.data

class NoteRepository(private val api: NoteApi) {
    suspend fun fetchNotes(): List<Note> = api.getNotes()
    suspend fun addNote(text: String): Note = api.createNote(CreateNoteRequest(text))
    suspend fun editNote(id: String, text: String): Note = api.updateNote(id, UpdateNoteRequest(text))
    suspend fun removeNote(id: String) { api.deleteNote(id) }
}

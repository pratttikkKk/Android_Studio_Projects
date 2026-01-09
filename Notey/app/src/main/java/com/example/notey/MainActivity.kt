package com.example.notey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.notey.Repository.NotesRepository
import com.example.notey.ViewModel.NoteViewModel
import com.example.notey.ViewModel.NoteViewModelfactory
import com.example.notey.roomdb.NotesDb
import com.example.notey.ui.theme.NoteyTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.graphics.toColorInt
import com.example.notey.Screens.DisplayNotesList
import com.example.notey.roomdb.Note

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database= NotesDb.getInstance(applicationContext)
        val repository= NotesRepository(database.notesDao)
        val viewModelfactory = NoteViewModelfactory(repository)
        val noteViewModel = ViewModelProvider(
            this,
            viewModelfactory
        )[NoteViewModel::class.java]
        val note1= Note(0,"This is the demo note",
            "Welcome my friend this is the demo description",
            "#f59597".toColorInt() )
        noteViewModel.insert(note1)

        setContent {
            NoteyTheme{
                val notes by noteViewModel.allNotes.observeAsState(emptyList())
                DisplayNotesList(notes= notes) }
        }
    }
}


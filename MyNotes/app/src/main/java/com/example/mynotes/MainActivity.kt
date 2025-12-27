package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.notesapp.ui.NotesScreen
import com.example.notesapp.ui.theme.NotesTheme

class MainActivity : ComponentActivity() {
    // 'override' is necessary because you are modifying a method from the parent class.
    override fun onCreate(savedInstanceState: Bundle?) {
        // 'super.onCreate' calls the original implementation from ComponentActivity,
        // which is crucial for the app to work correctly.
        super.onCreate(savedInstanceState)

        // This is the correct setContent function from the Jetpack Compose library.
        setContent {
            NotesTheme {
                NotesScreen()
            }
        }
    }
}

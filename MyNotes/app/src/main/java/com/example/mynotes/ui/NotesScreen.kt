package com.example.notesapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notesapp.NotesViewModel
import com.example.notesapp.data.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(vm: NotesViewModel = viewModel()) {
    val notes by vm.notes.collectAsState()
    val loading by vm.loading.collectAsState()
    val error by vm.error.collectAsState()

    var newText by remember { mutableStateOf("") }
    var editNote: Note? by remember { mutableStateOf(null) }
    var editText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Notes") })
        }
    ) { pad ->
        Column(
            modifier = Modifier
                .padding(pad)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Add form
            OutlinedTextField(
                value = newText,
                onValueChange = { newText = it },
                label = { Text("New note") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = {
                    vm.add(newText)
                    newText = ""
                },
                enabled = newText.isNotBlank()
            ) {
                Text("Add")
            }

            Spacer(Modifier.height(16.dp))

            if (loading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            if (error != null) {
                Text(
                    text = "Error: $error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Notes list
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(notes, key = { it.id }) { note ->
                    NoteRow(
                        note = note,
                        onEdit = {
                            editNote = note
                            editText = note.text
                        },
                        onDelete = { vm.delete(note.id) }
                    )
                }
            }
        }

        // Edit dialog
        if (editNote != null) {
            AlertDialog(
                onDismissRequest = { editNote = null },
                title = { Text("Edit note") },
                text = {
                    OutlinedTextField(
                        value = editText,
                        onValueChange = { editText = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        vm.update(editNote!!.id, editText)
                        editNote = null
                    }) { Text("Save") }
                },
                dismissButton = {
                    TextButton(onClick = { editNote = null }) { Text("Cancel") }
                }
            )
        }
    }
}

@Composable
private fun NoteRow(
    note: Note,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = note.text,
                modifier = Modifier.weight(1f),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.width(8.dp))
            TextButton(onClick = onEdit) { Text("Edit") }
            TextButton(onClick = onDelete) { Text("Delete") }
        }
    }
}

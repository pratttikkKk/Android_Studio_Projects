package com.example.notey.roomdb
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    // every time the id val will be auto generated.
    // PrimaryKey is used to specify the primary key
    val id: Int=0,
    val title: String,
    val description : String,
    val Color : Int  // stores color as argb integer
)
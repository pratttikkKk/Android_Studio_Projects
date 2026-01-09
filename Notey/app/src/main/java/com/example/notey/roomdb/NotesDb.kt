package com.example.notey.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class NotesDb : RoomDatabase(){
    abstract val notesDao : NoteDao
    //only one instance of the database exists , avoiding
    //unnecessary overhead associated with repeated DB creation
    //companion object : define a static singleton instance of this DB class
    //@volatile : prevents any possible race conditions in multithreading
    companion object{
        @Volatile
        private var INSTANCE : NotesDb?=null
        fun getInstance(context : Context): NotesDb{
            synchronized (this){
                var instance= INSTANCE
                if (instance == null){

                    instance = Room.databaseBuilder(

                        context = context.applicationContext,
                        NotesDb::class.java,
                        name = "Notes_db"

                    ).build()

                }
                INSTANCE=instance
                return instance
            }
        }
    }

}
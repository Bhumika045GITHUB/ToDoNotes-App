package com.mindorks.todonotesapp

import android.app.Application
import com.mindorks.todonotesapp.db.NotesDatabase

class NotesApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    fun getsNotesDb():NotesDatabase{
        return NotesDatabase.getInstance(this)
    }
}
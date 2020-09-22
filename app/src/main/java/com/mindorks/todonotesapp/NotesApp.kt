package com.mindorks.todonotesapp

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.mindorks.todonotesapp.db.NotesDatabase

class NotesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(getApplicationContext());
    }

    fun getsNotesDb():NotesDatabase{
        return NotesDatabase.getInstance(this)
    }
}
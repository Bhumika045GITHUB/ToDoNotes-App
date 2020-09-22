package com.mindorks.todonotesapp.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mindorks.todonotesapp.NotesApp

class MyWorker(val context : Context, val workerParameters : WorkerParameters) : Worker(context, workerParameters) {
    override fun doWork(): Result {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getsNotesDb().notesDao()
        notesDao.deleteNotes(true)
        return Result.success()
    }
}

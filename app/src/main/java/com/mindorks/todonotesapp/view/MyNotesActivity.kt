package com.mindorks.todonotesapp.view

import android.app.Activity
import android.app.DownloadManager
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Insets.add
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mindorks.todonotesapp.NotesApp
//import com.mindorks.todonotesapp.AppConstant.FULL_NAME
import com.mindorks.todonotesapp.R
import com.mindorks.todonotesapp.adapter.NotesAdapter
import com.mindorks.todonotesapp.clicklisteners.ItemClickListener
import com.mindorks.todonotesapp.db.Notes
import com.mindorks.todonotesapp.utils.AppConstant
import com.mindorks.todonotesapp.utils.PrefConstant
import com.mindorks.todonotesapp.workmanager.MyWorker
import kotlinx.android.synthetic.main.activity_my_notes.*
import java.util.concurrent.TimeUnit

public class MyNotesActivity : AppCompatActivity() {
    lateinit var fullName: String
    lateinit var fabAddNotes: FloatingActionButton
    val TAG = "MyNotesActivity"
    lateinit var sharedPreferences: SharedPreferences
    lateinit var recyclerView: RecyclerView
    var notesList = ArrayList<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)
        bindViews()
        setupSharedPreferences()
        getIntentData()
        getDatafromDatabase()
        setupRecyclerView()
        clickListeners()
        setupWorkManager()
    }

    private fun setupWorkManager() {
        val constraint = Constraints.Builder()
                .build()
        val request = PeriodicWorkRequest
                .Builder(MyWorker::class.java, 1, TimeUnit.MINUTES)
                .setConstraints(constraint)
                .build()
        WorkManager.getInstance().enqueue(request)
    }
//        supportActionBar?.title = fullName

    private fun clickListeners() {
        supportActionBar?.title = fullName
        fabAddNotes.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v : View?) {
               val intent = Intent(this@MyNotesActivity, AddNotesActivity:: class.java)
                startActivityForResult(intent, Companion.ADD_NOTES_CODE)
            }
        })
    }

    private fun getDatafromDatabase() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getsNotesDb().notesDao()
        notesList.addAll(notesDao.getAll())
    }

    private fun addNotesToDo(notes: Notes) {
        // insert notes in DB
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getsNotesDb().notesDao()
        notesDao.insert(notes)
    }
    private fun setupRecyclerView() {
        val itemClickListener = object : ItemClickListener {
            override fun onClick(notes: Notes) {
                val intent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                intent.putExtra(AppConstant.TITLE, notes.title)
                val putExtra = intent.putExtra(AppConstant.DESCRIPTION, notes.description)
                startActivity(intent)
            }
            override fun onUpdate(notes: Notes) {
//                    update the values
                val notesApp = applicationContext as NotesApp
                val notesDao = notesApp.getsNotesDb().notesDao()
                notesDao.updateNotes(notes)
            }
        }
        val notesAdapter = NotesAdapter(notesList, itemClickListener)
        val LinearLayoutManager = LinearLayoutManager(this@MyNotesActivity)
        LinearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = LinearLayoutManager
        recyclerView.adapter = notesAdapter
    }
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (resultCode == Activity.RESULT_OK) {
                val title = data?.getStringExtra(AppConstant.TITLE)
                val description = data?.getStringExtra(AppConstant.DESCRIPTION)
                val imagePath = data?.getStringExtra(AppConstant.IMAGE_PATH)

                val notesApp = applicationContext as NotesApp
                val notesDao = notesApp.getsNotesDb().notesDao()
                val notes = Notes(title = title!!, description = description!!, imagePath = imagePath!!)
                notesList.add(notes)
                notesDao.insert(notes)
                recyclerViewNotes.adapter?.notifyItemChanged(notesList.size-1)
            }
        }
    private fun getIntentData() {
        val intent = intent
        if(intent.hasExtra(AppConstant.FULL_NAME)) {
            fullName = intent.getStringExtra(AppConstant.FULL_NAME).toString()
        }
        if(!this::fullName.isInitialized) {
            fullName = sharedPreferences.getString(PrefConstant.FULL_NAME, "").toString()
        }
    }

    private fun setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE)
    }

    private fun bindViews() {
        fabAddNotes = findViewById(R.id.fabAddNotes)
        recyclerView = findViewById(R.id.recyclerViewNotes)

    }

    companion object {
        const val ADD_NOTES_CODE = 100
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item?.itemId == R.id.blog) {
            Log.d(TAG, "Click Successful")
            val intent = Intent(this@MyNotesActivity, BlogActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}

private fun Any.show() {


}

private fun Any.hide() {

}

private fun AlertDialog.Builder.Create() {

}

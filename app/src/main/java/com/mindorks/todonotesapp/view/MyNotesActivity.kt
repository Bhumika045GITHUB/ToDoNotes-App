package com.mindorks.todonotesapp.view

import android.app.ProgressDialog.show
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AbsListView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mindorks.todonotesapp.NotesApp
//import com.mindorks.todonotesapp.AppConstant.FULL_NAME
import com.mindorks.todonotesapp.R
import com.mindorks.todonotesapp.adapter.NotesAdapter
import com.mindorks.todonotesapp.clicklisteners.ItemClickListener
import com.mindorks.todonotesapp.db.Notes
import com.mindorks.todonotesapp.utils.AppConstant
import com.mindorks.todonotesapp.utils.PrefConstant

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

        supportActionBar?.title = fullName
        fabAddNotes.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                setupDialogbBox()
            }

        }
        )
    }

    private fun getDatafromDatabase() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getsNotesDb().notesDao()
        notesList.addAll(notesDao.getAll())
    }

    private fun setupDialogbBox() {
        val view = LayoutInflater.from(this@MyNotesActivity).inflate(R.layout.add_notes_dialog_layout, null)
        val editTextTitle = view.findViewById<EditText>(R.id.editTextTitle)
        val editTextDescription = view.findViewById<EditText>(R.id.editTextDescription)
        val buttonSubmit = view.findViewById<Button>(R.id.buttonSubmit)
        val dialog = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create()
       buttonSubmit.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                val title = editTextTitle.text.toString()
                val description = editTextDescription.text.toString()
                if(title.isNotEmpty() && description.isNotEmpty()) {
                    val notes = Notes(title = title, description = description)
                    notesList.add(notes)
                    addNotesToDo(notes)
                }
                else {
                    Toast.makeText(this@MyNotesActivity, "Title or Decription cannot be empty", Toast.LENGTH_SHORT).show()
                }
                setupRecyclerView()
                dialog.hide()
            }
        })
            dialog.show()
    }

    private fun addNotesToDo(notes: Notes) {
        // insert notes in DB
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getsNotesDb().notesDao()
        notesDao.insert(notes)

    }

    private fun setupRecyclerView() {
            val itemClickListener = object  : ItemClickListener {
                override fun onClick(notes: Notes) {
                    val intent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                    intent.putExtra(AppConstant.TITLE, notes.title)
                    intent.putExtra(AppConstant.DESCRIPTION, notes.description)
                    startActivity(intent)
                }

                override fun onUpdate(notes: Notes) {
//                    update the values
                    val notesApp = applicationContext as NotesApp
                    val notesDao = notesApp.getsNotesDb().notesDao()
                    notesDao.updateNotes(notes)
                }

            }
            val notesAdapter = NotesAdapter(notesList, itemClickListener )
            val LinearLayoutManager = LinearLayoutManager(this@MyNotesActivity)
            LinearLayoutManager.orientation = RecyclerView.VERTICAL
            recyclerView.layoutManager = LinearLayoutManager
            recyclerView.adapter = notesAdapter

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
}

private fun Any.show() {

}

private fun Any.hide() {

}

private fun AlertDialog.Builder.Create() {

}

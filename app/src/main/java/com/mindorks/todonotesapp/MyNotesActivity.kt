package com.mindorks.todonotesapp

import android.app.ProgressDialog.show
import android.content.Context.MODE_PRIVATE
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
import com.mindorks.todonotesapp.AppConstant.FULL_NAME
import com.mindorks.todonotesapp.adapter.NotesAdapter
import com.mindorks.todonotesapp.clicklisteners.ItemClickListener
import com.mindorks.todonotesapp.model.Notes

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
        supportActionBar?.title = fullName
        fabAddNotes.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                setupDialogbBox()
            }

        }
        )
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
                    val notes = Notes(title, description)
                    notesList.add(notes)
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

    private fun setupRecyclerView() {
            val itemClickListener = object  : ItemClickListener {
                override fun onClick(notes: Notes) {
                    val intent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                    intent.putExtra(AppConstant.TITLE, notes.title)
                    intent.putExtra(AppConstant.DESCRIPTION, notes.description)
                    startActivity(intent)
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
           fullName = intent.getStringExtra(AppConstant.FULL_NAME).toString()
            if(fullName.isEmpty()) {
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

private fun AlertDialog.Builder.Create() {
}

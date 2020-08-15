package com.mindorks.todonotesapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mindorks.todonotesapp.adapter.NotesAdapter;
import com.mindorks.todonotesapp.clicklisteners.ItemClickListener;
import com.mindorks.todonotesapp.model.Notes;

import java.util.ArrayList;


public class MyNotesActivity extends AppCompatActivity {

    String fullName;
    FloatingActionButton fabAddNotes;
    SharedPreferences sharedPreferences;
    String TAG = "MyNotesActivity";
    RecyclerView recyclerViewNotes;
    ArrayList<Notes> notesList = new ArrayList<>();
    TextView textViewTitle, textViewDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        fabAddNotes = findViewById(R.id.fabAddNotes);


        final Intent intent = getIntent();
        fullName = intent.getStringExtra("full_name");

        fabAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupDialogBox();
            }
        });

        getSupportActionBar().setTitle(fullName);

    }

    private void setupDialogBox() {
        View view = LayoutInflater.from(MyNotesActivity.this).inflate(R.layout.add_notes_dialog_layout, null);
        final EditText editTextTitle = view.findViewById(R.id.editTextTitle);
        final EditText editTextDescription = view.findViewById(R.id.editTextDescription);
        Button buttonSubmit = view.findViewById(R.id.buttonSubmit);
        //Dailog
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();
                Notes notes = new Notes();
                notes.setTitle(title);
                notes.setDescription(description);
                notesList.add(notes);
                setupRecyclerView();
                dialog.hide();
//                rcv recylerview -> ADAPTER -> LIST
            }
        });
        dialog.show();

    }

    private void setupRecyclerView() {

//        interface concept
        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onClick() {

            }
        };

        NotesAdapter notesAdapter = new NotesAdapter(notesList, itemClickListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyNotesActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewNotes.setLayoutManager(linearLayoutManager);
        recyclerViewNotes.setAdapter(notesAdapter);
    }
}
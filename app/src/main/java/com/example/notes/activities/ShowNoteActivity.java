package com.example.notes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notes.DB.MainViewModel;
import com.example.notes.DB.Note;
import com.example.notes.R;
import com.example.notes.adapter.NotesAdapter;

import java.util.ArrayList;

public class ShowNoteActivity extends AppCompatActivity {

    private EditText editTextAddTitle, editTextAddDescription;
    private Button button;
    private MainViewModel viewModel;
    private NotesAdapter adapter;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        editTextAddTitle = findViewById(R.id.editTextAddTitle);
        editTextAddDescription = findViewById(R.id.editTextAddDescription);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        id = intent.getIntExtra("id",0);

        editTextAddTitle.setText(title);
        editTextAddDescription.setText(description);

    }

    public void onClickSaveNote(View view) {
        String title = editTextAddTitle.getText().toString();
        String description = editTextAddDescription.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("newTitle",title);
        intent.putExtra("newDescription",description);
        intent.putExtra("newId",id);
        Note note = adapter.getNotes().get(id);
        viewModel.updateNote(note);
        setResult(RESULT_OK,intent);
        finish();

    }
}

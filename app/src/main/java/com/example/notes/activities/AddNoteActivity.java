package com.example.notes.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notes.DB.MainViewModel;
import com.example.notes.DB.Note;
import com.example.notes.DB.NotesDatabase;
import com.example.notes.R;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextAddTitle, editTextAddDescription;
    private MainViewModel viewModel;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        button = findViewById(R.id.button);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        editTextAddTitle = findViewById(R.id.editTextAddTitle);
        editTextAddDescription = findViewById(R.id.editTextAddDescription);
    }

    public void onClickAddNote(View view) {
        String title = editTextAddTitle.getText().toString().trim();
        String description = editTextAddDescription.getText().toString().trim();

        if (isFilled(title, description)) {
            Note note = new Note(title, description);
            viewModel.insertNote(note);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            button.setEnabled(false);
        } else {
            Toast.makeText(this, R.string.fill_in_all_fields, Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isFilled(String title, String description) {
        return (!title.isEmpty() && !description.isEmpty()) || (!title.isEmpty() || !description.isEmpty());
    }
}

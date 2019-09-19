package com.example.notes.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.notes.DB.MainViewModel;
import com.example.notes.DB.Note;
import com.example.notes.R;
import com.example.notes.adapter.NotesAdapter;
import com.example.notes.adapter.OnNoteClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerview;
    private final ArrayList<Note> notes = new ArrayList<>();
    private Button button;
    private NotesAdapter adapter;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.hide();
        }

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        recyclerview = findViewById(R.id.recyclerViewNotes);
        button = findViewById(R.id.buttonAdd);

        getData();
        adapter = new NotesAdapter(notes);
        recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerview.setAdapter(adapter);
        adapter.setOnNoteClickListener(new OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                Intent intent = new Intent(MainActivity.this,ShowNoteActivity.class);
                Note note = adapter.getNotes().get(position);
                intent.putExtra("title",note.getTitle());
                intent.putExtra("description",note.getDescription());
                intent.putExtra("id",note.getId());
                startActivityForResult(intent,1);
            }

            @Override
            public void onLongClick(int position) {
                remove(position);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(intent);
            }
        });

    }

    private void remove(int position) {
        Note note = adapter.getNotes().get(position);
        viewModel.deleteNote(note);
    }

    private void getData() {
        LiveData<List<Note>> notesFromDB = viewModel.getNotes();
        notesFromDB.observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notesFromLiveData) {
                adapter.setNotes(notesFromLiveData);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null){
            return;
        }
        String title = data.getStringExtra("newTitle");
        String description = data.getStringExtra("newDescription");
        int id = data.getIntExtra("newId",0);

        Note note = adapter.getNotes().get(id);
        note.setTitle(title);
        note.setDescription(description);

    }
}

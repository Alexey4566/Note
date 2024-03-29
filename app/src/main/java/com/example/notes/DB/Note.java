package com.example.notes.DB;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey (autoGenerate = true)
    private int id;
    private String title;
    private String description;


    public Note(int id,String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
    @Ignore
    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }



    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

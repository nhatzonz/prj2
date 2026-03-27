package com.example.prj2.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class Movie {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "genre")
    public String genre;

    @ColumnInfo(name = "duration")
    public int duration;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "rating")
    public float rating;

    @ColumnInfo(name = "release_year")
    public int releaseYear;

    public Movie() {}

    public Movie(String title, String genre, int duration, String description, float rating, int releaseYear) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.description = description;
        this.rating = rating;
        this.releaseYear = releaseYear;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public int getDuration() { return duration; }
    public String getDescription() { return description; }
    public float getRating() { return rating; }
    public int getReleaseYear() { return releaseYear; }
}

package com.example.prj2.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "showtimes",
    foreignKeys = {
        @ForeignKey(
            entity = Movie.class,
            parentColumns = "id",
            childColumns = "movie_id",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = Theater.class,
            parentColumns = "id",
            childColumns = "theater_id",
            onDelete = ForeignKey.CASCADE
        )
    },
    indices = {
        @Index("movie_id"),
        @Index("theater_id")
    }
)
public class Showtime {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "movie_id")
    public int movieId;

    @ColumnInfo(name = "theater_id")
    public int theaterId;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "time")
    public String time;

    @ColumnInfo(name = "price")
    public long price;

    @ColumnInfo(name = "available_seats")
    public int availableSeats;

    public Showtime() {}

    public Showtime(int movieId, int theaterId, String date, String time, long price, int availableSeats) {
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.date = date;
        this.time = time;
        this.price = price;
        this.availableSeats = availableSeats;
    }

    public int getId() { return id; }
    public int getMovieId() { return movieId; }
    public int getTheaterId() { return theaterId; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public long getPrice() { return price; }
    public int getAvailableSeats() { return availableSeats; }
}

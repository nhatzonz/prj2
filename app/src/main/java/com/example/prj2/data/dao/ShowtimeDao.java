package com.example.prj2.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.prj2.data.entity.Showtime;

import java.util.List;

@Dao
public interface ShowtimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Showtime showtime);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Showtime> showtimes);

    @Update
    void update(Showtime showtime);

    @Delete
    void delete(Showtime showtime);

    @Query("SELECT * FROM showtimes ORDER BY date ASC, time ASC")
    List<Showtime> getAllShowtimes();

    @Query("SELECT * FROM showtimes WHERE id = :id LIMIT 1")
    Showtime getShowtimeById(int id);

    @Query("SELECT * FROM showtimes WHERE movie_id = :movieId ORDER BY date ASC, time ASC")
    List<Showtime> getShowtimesByMovie(int movieId);

    @Query("SELECT * FROM showtimes WHERE theater_id = :theaterId ORDER BY date ASC, time ASC")
    List<Showtime> getShowtimesByTheater(int theaterId);

    @Query("UPDATE showtimes SET available_seats = available_seats - 1 WHERE id = :showtimeId AND available_seats > 0")
    void decrementAvailableSeats(int showtimeId);

    @Query("SELECT COUNT(*) FROM showtimes")
    int count();
}

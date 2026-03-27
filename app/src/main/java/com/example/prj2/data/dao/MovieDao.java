package com.example.prj2.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.prj2.data.entity.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Movie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Movie> movies);

    @Update
    void update(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("SELECT * FROM movies ORDER BY title ASC")
    List<Movie> getAllMovies();

    @Query("SELECT * FROM movies WHERE id = :id LIMIT 1")
    Movie getMovieById(int id);

    @Query("SELECT * FROM movies WHERE title LIKE '%' || :query || '%' ORDER BY title ASC")
    List<Movie> searchMovies(String query);

    @Query("SELECT COUNT(*) FROM movies")
    int count();
}

package com.example.prj2.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.prj2.data.entity.Theater;

import java.util.List;

@Dao
public interface TheaterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Theater theater);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Theater> theaters);

    @Update
    void update(Theater theater);

    @Delete
    void delete(Theater theater);

    @Query("SELECT * FROM theaters ORDER BY name ASC")
    List<Theater> getAllTheaters();

    @Query("SELECT * FROM theaters WHERE id = :id LIMIT 1")
    Theater getTheaterById(int id);

    @Query("SELECT COUNT(*) FROM theaters")
    int count();
}

package com.example.prj2.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.prj2.data.entity.Ticket;

import java.util.List;

@Dao
public interface TicketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Ticket ticket);

    @Update
    void update(Ticket ticket);

    @Delete
    void delete(Ticket ticket);

    @Query("SELECT * FROM tickets ORDER BY booking_date DESC")
    List<Ticket> getAllTickets();

    @Query("SELECT * FROM tickets WHERE id = :id LIMIT 1")
    Ticket getTicketById(int id);

    @Query("SELECT * FROM tickets WHERE user_id = :userId ORDER BY booking_date DESC")
    List<Ticket> getTicketsByUser(int userId);

    @Query("SELECT * FROM tickets WHERE showtime_id = :showtimeId")
    List<Ticket> getTicketsByShowtime(int showtimeId);

    @Query("SELECT seat_number FROM tickets WHERE showtime_id = :showtimeId")
    List<String> getBookedSeatsByShowtime(int showtimeId);

    @Query("SELECT COUNT(*) FROM tickets WHERE showtime_id = :showtimeId AND seat_number = :seatNumber")
    int isSeatBooked(int showtimeId, String seatNumber);
}

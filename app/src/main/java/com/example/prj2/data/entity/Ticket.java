package com.example.prj2.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "tickets",
    foreignKeys = {
        @ForeignKey(
            entity = Showtime.class,
            parentColumns = "id",
            childColumns = "showtime_id",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = User.class,
            parentColumns = "id",
            childColumns = "user_id",
            onDelete = ForeignKey.CASCADE
        )
    },
    indices = {
        @Index("showtime_id"),
        @Index("user_id")
    }
)
public class Ticket {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "showtime_id")
    public int showtimeId;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "seat_number")
    public String seatNumber;

    @ColumnInfo(name = "booking_date")
    public String bookingDate;

    @ColumnInfo(name = "total_price")
    public long totalPrice;

    @ColumnInfo(name = "status")
    public String status;

    public Ticket() {}

    public Ticket(int showtimeId, int userId, String seatNumber, String bookingDate, long totalPrice, String status) {
        this.showtimeId = showtimeId;
        this.userId = userId;
        this.seatNumber = seatNumber;
        this.bookingDate = bookingDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public int getId() { return id; }
    public int getShowtimeId() { return showtimeId; }
    public int getUserId() { return userId; }
    public String getSeatNumber() { return seatNumber; }
    public String getBookingDate() { return bookingDate; }
    public long getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
}

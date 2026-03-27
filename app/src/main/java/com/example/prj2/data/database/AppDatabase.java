package com.example.prj2.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.prj2.data.dao.MovieDao;
import com.example.prj2.data.dao.ShowtimeDao;
import com.example.prj2.data.dao.TheaterDao;
import com.example.prj2.data.dao.TicketDao;
import com.example.prj2.data.dao.UserDao;
import com.example.prj2.data.entity.Movie;
import com.example.prj2.data.entity.Showtime;
import com.example.prj2.data.entity.Theater;
import com.example.prj2.data.entity.Ticket;
import com.example.prj2.data.entity.User;

@Database(
    entities = {User.class, Movie.class, Theater.class, Showtime.class, Ticket.class},
    version = 1,
    exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract MovieDao movieDao();
    public abstract TheaterDao theaterDao();
    public abstract ShowtimeDao showtimeDao();
    public abstract TicketDao ticketDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "cinema_database"
                    )
                    .allowMainThreadQueries()
                    .build();
                }
            }
        }
        return INSTANCE;
    }
}

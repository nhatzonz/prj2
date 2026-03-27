package com.example.prj2.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "theaters")
public class Theater {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "total_seats")
    public int totalSeats;

    public Theater() {}

    public Theater(String name, String address, int totalSeats) {
        this.name = name;
        this.address = address;
        this.totalSeats = totalSeats;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public int getTotalSeats() { return totalSeats; }
}

package com.example.currency3.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.currency3.pojo.Currency;

@Database(entities = {Currency.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoCurrency daoCurrency ();
}

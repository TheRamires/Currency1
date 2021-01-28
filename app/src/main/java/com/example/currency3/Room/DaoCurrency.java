package com.example.currency3.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.currency3.pojo.Currency;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface DaoCurrency {
    @Insert
    void insert(List<Currency> currencies);

    @Query("SELECT * FROM Currency")
    Maybe<List<Currency>> queryCurency();
}

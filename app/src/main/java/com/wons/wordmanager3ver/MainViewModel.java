package com.wons.wordmanager3ver;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.room.Room;
import androidx.room.RoomDatabase;

public class MainViewModel extends ViewModel {
    public static MainDatabase database;
    public static MyDao dao;

    public void buildDataBase(Context context) {
        database = Room.databaseBuilder(context, MainDatabase.class, "MainDataBase").allowMainThreadQueries().build();
        dao = database.getDao();
    }
}

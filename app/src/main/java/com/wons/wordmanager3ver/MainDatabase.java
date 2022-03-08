package com.wons.wordmanager3ver;

import androidx.room.Database;
import androidx.room.RoomDatabase;

//@Database(entities = {} , version = 1);
public abstract class MainDatabase extends RoomDatabase {
    abstract MyDao getDao();
}

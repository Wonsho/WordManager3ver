package com.wons.wordmanager3ver;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.wons.wordmanager3ver.datavalues.Setting;
import com.wons.wordmanager3ver.datavalues.UserInfo;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordInfo;
import com.wons.wordmanager3ver.datavalues.WordList;

@Database(entities = {Setting.class, UserInfo.class, Word.class, WordInfo.class, WordList.class}, version = 1)
public abstract class MainDatabase extends RoomDatabase {
    abstract MyDao getDao();
}

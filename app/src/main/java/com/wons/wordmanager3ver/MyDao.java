package com.wons.wordmanager3ver;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.wons.wordmanager3ver.datavalues.Setting;

@Dao
public interface MyDao {
    @Insert
    void insertUserSetting(Setting setting);
    @Update
    void updateUserSetting(Setting setting);
    @Query("SELECT * FROM setting WHERE settingCode = :settingCode")
    Setting getSetting(int settingCode);
}

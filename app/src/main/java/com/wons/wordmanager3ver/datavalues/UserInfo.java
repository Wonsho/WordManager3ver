package com.wons.wordmanager3ver.datavalues;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserInfo {
    @PrimaryKey
    @NonNull
    private int userId;
    private int userGradeInt;
    private int expInt;
    private int languageCodeSettingInt;

    public void setLanguageCodeSettingInt(int languageCodeSettingInt) {
        this.languageCodeSettingInt = languageCodeSettingInt;
    }

    public int getLanguageCodeSettingInt() {
        return languageCodeSettingInt;
    }

    public int getExpInt() {
        return expInt;
    }

    public int getUserGradeInt() {
        return userGradeInt;
    }

    public void setExpInt(int expInt) {
        this.expInt = expInt;
    }

    public void setUserGradeInt(int userGradeInt) {
        this.userGradeInt = userGradeInt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}


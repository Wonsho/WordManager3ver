package com.wons.wordmanager3ver.datavalues;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FlagUserLevelData {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;
    private int languageCode;
    private String date;
    private int levelOfDate;

    public FlagUserLevelData(int languageCode, String date, int levelOfDate) {
        this.levelOfDate = levelOfDate;
        this.date = date;
        this.languageCode = levelOfDate;
    }

    public String getDate() {
        return date;
    }

    public int getLevelOfDate() {
        return levelOfDate;
    }

    public void setLanguageCode(int languageCode) {
        this.languageCode = languageCode;
    }

    public int getLanguageCode() {
        return languageCode;
    }

    public void setLevelOfDate(int levelOfDate) {
        this.levelOfDate = levelOfDate;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

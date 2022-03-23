package com.wons.wordmanager3ver.datavalues;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class TodayWordList {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;
    private int listCode;
    private int listLanguageCode;
    public boolean passOrNo;

    public TodayWordList() {}

    public TodayWordList(int languageCode, int listCode, boolean passOrNo) {
        this.listCode = listCode;
        this.passOrNo = passOrNo;
        this.listLanguageCode = languageCode;
    }

    public int getListCode() {
        return listCode;
    }

    public void setListCode(int listCode) {
        this.listCode = listCode;
    }

    public int getListLanguageCode() { return listLanguageCode; }

    public void setListLanguageCode(int listLanguageCode) { this.listLanguageCode = listLanguageCode; }
}

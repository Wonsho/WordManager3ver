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
    public String listName;
    public int listGrade;
    private int listCode;
    private int listLanguageCode;
    public boolean passOrNo;
    public int listWordCount;

    public TodayWordList() {}

    public TodayWordList(int languageCode, int listCode, boolean passOrNo, String listName, int listGrade, int listWordCount) {
        this.listCode = listCode;
        this.listName = listName;
        this.listGrade = listGrade;
        this.passOrNo = passOrNo;
        this.listWordCount = listWordCount;
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

package com.wons.wordmanager3ver.datavalues;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WordList {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int listCodeInt;
    private int languageCode;
    public String listName;
    private int listGradeInt;
    private int wordCountInt;
    public String insertDate;

    public WordList(String listName, int languageCode, String insertDate) {
        this.listName = listName;
        this.languageCode = languageCode;
        this.listGradeInt = 0;
        this.wordCountInt = 0;
        this.insertDate = insertDate;
    }


    public int getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(int languageCode) {
        this.languageCode = languageCode;
    }

    public int getListGrade() {
        return listGradeInt;
    }

    public void setListGrade(int listGradeInt) {
        this.listGradeInt = listGradeInt;
    }

    public void setWordCountInt(int wordCountInt) {
        this.wordCountInt = wordCountInt;
    }

    public int getWordCountInt() {
        return wordCountInt;
    }

    public int getListCodeInt() {
        return listCodeInt;
    }

    public void setListCodeInt(int listCodeInt) {
        this.listCodeInt = listCodeInt;
    }

    public int getListGradeInt() {
        return listGradeInt;
    }

    public void setListGradeInt(int listGradeInt) {
        this.listGradeInt = listGradeInt;
    }
}

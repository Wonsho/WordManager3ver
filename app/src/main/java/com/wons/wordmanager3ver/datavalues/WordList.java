package com.wons.wordmanager3ver.datavalues;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WordList {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int listCodeInt;
    public String listName;
    private int listGradeInt;
    private int wordCountInt;
    public String insertDate;

    WordList(String listName) {
        this.listName = listName;
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
}

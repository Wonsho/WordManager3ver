package com.wons.wordmanager3ver.datavalues;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

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
        String title = listName.trim();
        char[] cString = title.toCharArray();
        ArrayList<String> strArr = new ArrayList<>();
        for (int i = 0; i < cString.length; i++) {
            if (cString[i] == ' ') {
                if (cString[i + 1] != ' ') {
                    strArr.add(String.valueOf(cString[i]));
                }
            } else {
                strArr.add(String.valueOf(cString[i]));
            }
        }
        StringBuilder builder = new StringBuilder();
        for (String s : strArr) {
            builder.append(s);
        }
        this.listName = builder.toString();
        this.languageCode = languageCode;
        this.listGradeInt = -1;
        this.wordCountInt = 0;
        this.insertDate = insertDate;
    }


    public int getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(int languageCode) {
        this.languageCode = languageCode;
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

    public void addWordCount() {
        this.wordCountInt++;
    }

    public void discountWordCount() {
        if (this.wordCountInt > 0) {
            wordCountInt--;
        }
    }
}

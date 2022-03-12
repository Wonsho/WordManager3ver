package com.wons.wordmanager3ver.datavalues;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Word {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int wordId;
    private int languageCode;
    private String wordTitle;
    private int wordListCodeInt;

    public Word(int languageCode, String wordTitle, int wordListCodeInt) {
        this.languageCode = languageCode;
        String title = wordTitle.trim();
        char[] cString = title.toCharArray();
        ArrayList<String> strArr = new ArrayList<>();
        for(int i = 0 ; i <cString.length ; i++) {
            if(cString[i] == ' ') {
                if(cString[i+1] != ' ') {
                    strArr.add(String.valueOf(cString[i]));
                }
            } else {
                strArr.add(String.valueOf(cString[i]));
            }
        }
        StringBuilder builder = new StringBuilder();
        for(String s : strArr) {
            builder.append(s);
        }
        this.wordTitle = builder.toString();
        this.wordListCodeInt = wordListCodeInt;
    }
    public int getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(int languageCode) {
        this.languageCode = languageCode;
    }

    public void setWordTitle(String wordTitle) {
        this.wordTitle = wordTitle;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public void setWordListCodeInt(int wordListCodeInt) {
        this.wordListCodeInt = wordListCodeInt;
    }

    public int getWordId() {
        return wordId;
    }

    public int getWordListCodeInt() {
        return wordListCodeInt;
    }

    public String getWordTitle() {
        return wordTitle;
    }
}

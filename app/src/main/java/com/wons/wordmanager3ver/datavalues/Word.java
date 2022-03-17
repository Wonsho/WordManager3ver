package com.wons.wordmanager3ver.datavalues;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.wons.wordmanager3ver.tool.Tools;

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
        this.wordTitle = new Tools().removeOverSpace(wordTitle);
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

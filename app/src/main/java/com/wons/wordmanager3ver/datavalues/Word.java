package com.wons.wordmanager3ver.datavalues;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Word {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int wordId;
    private int languageCode;
    private String wordEnglish;
    private int wordListCodeInt;

    Word(int languageCode, String wordEnglish, int wordListCodeInt) {
        this.languageCode = languageCode;
        this.wordEnglish = wordEnglish;
        this.wordListCodeInt = wordListCodeInt;
    }
    public int getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(int languageCode) {
        this.languageCode = languageCode;
    }

    public void setWordEnglish(String wordEnglish) {
        this.wordEnglish = wordEnglish;
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

    public String getWordEnglish() {
        return wordEnglish;
    }
}

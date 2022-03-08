package com.wons.wordmanager3ver.datavalues;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Word {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int wordId;
    private String wordEnglish;
    private int wordInfoInt;
    private int wordListCodeInt;

    public void setWordEnglish(String wordEnglish) {
        this.wordEnglish = wordEnglish;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public void setWordInfoInt(int wordInfoInt) {
        this.wordInfoInt = wordInfoInt;
    }

    public void setWordListCodeInt(int wordListCodeInt) {
        this.wordListCodeInt = wordListCodeInt;
    }

    public int getWordId() {
        return wordId;
    }

    public int getWordInfoInt() {
        return wordInfoInt;
    }

    public int getWordListCodeInt() {
        return wordListCodeInt;
    }

    public String getWordEnglish() {
        return wordEnglish;
    }
}

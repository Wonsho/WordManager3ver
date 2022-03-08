package com.wons.wordmanager3ver.datavalues;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WordInfo {

    @PrimaryKey
    @NonNull
    private String wordEnglish;
    public String wordMemo;
    public String wordKorean;
    private int testedTimes;
    private int correctTimes;

    WordInfo(String wordEnglish, String wordKorean, String wordMemo) {
        this.wordEnglish = wordEnglish;
        this.wordKorean = wordKorean;
        this.wordMemo = wordMemo;
        this.testedTimes = 0;
        this.correctTimes = 0;
    }

    public void addCorrectCount() {
        this.correctTimes = correctTimes++;
    }

    public void addTestedCount() {
        this.testedTimes = testedTimes++;
    }

    @NonNull
    public String getWordEnglish() {
        return wordEnglish;
    }

    public void setWordEnglish(@NonNull String wordEnglish) {
        this.wordEnglish = wordEnglish;
    }

}

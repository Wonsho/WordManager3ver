package com.wons.wordmanager3ver.datavalues;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WordInfo {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int infoId;
    private String wordEnglish;
    private int languageCode;
    private String wordMemo;
    public String wordKorean;
    private int testedTimes;
    private int correctTimes;

    public WordInfo(String wordEnglish, String wordKorean, int languageCode) {
        this.wordEnglish = wordEnglish.toUpperCase();
        this.wordKorean = wordKorean;
        this.wordMemo = "";
        this.testedTimes = 0;
        this.correctTimes = 0;
        this.languageCode = languageCode;
    }

    public int getInfoId() {
        return infoId;
    }

    public void setInfoId(int infoId) {
        this.infoId = infoId;
    }

    public int getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(int languageCode) {
        this.languageCode = languageCode;
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

    public int getTestedTimes() {
        return testedTimes;
    }

    public int getCorrectTimes() {
        return correctTimes;
    }

    public void setTestedTimes(int testedTimes) {
        this.testedTimes = testedTimes;
    }

    public void setCorrectTimes(int correctTimes) {
        this.correctTimes = correctTimes;
    }

    public int getCorrectPercentage() {
        return (int)((double) correctTimes / (double) testedTimes) * 100;
    }
    public void addTestedTimes() {
        this.testedTimes++;
    }
    public void addCorrectTimes() {
        this.correctTimes++;
    }

    public String getWordMemo() {
        return wordMemo;
    }

    public void setWordMemo(String wordMemo) {
        this.wordMemo = wordMemo;
    }
}

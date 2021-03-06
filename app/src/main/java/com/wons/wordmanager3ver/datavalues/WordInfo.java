package com.wons.wordmanager3ver.datavalues;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.wons.wordmanager3ver.tool.Tools;

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
    private boolean todayTestResult;

    public WordInfo(String wordEnglish, String wordKorean, int languageCode) {
        this.wordEnglish = new Tools().removeOverSpace(wordEnglish.toUpperCase());
        this.wordKorean = wordKorean;
        this.todayTestResult = false;
        this.wordMemo = "";
        this.testedTimes = 0;
        this.correctTimes = 0;
        this.languageCode = languageCode;
    }

    public void setTodayTestResult(boolean todayTestResult) {
        this.todayTestResult = todayTestResult;
    }

    public void setTestResult(boolean result) {
        this.todayTestResult = result;
    }

    public boolean getTodayTestResult() {
        return this.todayTestResult;
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
        Log.e("add", "pass");
        this.correctTimes += 1;
    }

    public void addTestedCount() {
        Log.e("add2", "pass");
        this.testedTimes += 1;
    }

    @NonNull
    public String getWordEnglish() {
        return wordEnglish;
    }

    public void setWordEnglish(@NonNull String wordEnglish) {
        this.wordEnglish = new Tools().removeOverSpace(wordEnglish.toUpperCase());
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
        if(this.testedTimes == 0) {
            return -1;
        }
        return (int) (((double) this.correctTimes / (double) this.testedTimes) * 100.0);
    }

    public String getWordMemo() {
        return wordMemo;
    }

    public void setWordMemo(String wordMemo) {
        this.wordMemo = wordMemo;
    }
}

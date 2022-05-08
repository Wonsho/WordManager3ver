package com.wons.wordmanager3ver.datavalues;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TestWordResult {

    @PrimaryKey
    @NonNull
    private int wordId; // -> 원래의 단어 아이디 공유
    private String wordTitle;
    private int listCodeOfWord;
    public String inputWord;
    private boolean testResult;

    public TestWordResult(int wordId, String wordTitle, int listCodeOfWord, String inputWord) {
        this.wordId = wordId;
        this.inputWord = inputWord;
        this.wordTitle = wordTitle;
        this.listCodeOfWord = listCodeOfWord;
        this.testResult = false;
    }

    public int getWordId() {
        return wordId;
    }

    public int getListCodeOfWord() {
        return listCodeOfWord;
    }

    public String getWordTitle() {
        return wordTitle;
    }

    public boolean getTestResult() {
        return testResult;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public void setListCodeOfWord(int listCodeOfWord) {
        this.listCodeOfWord = listCodeOfWord;
    }

    public void setTestResult(boolean testResult) {
        this.testResult = testResult;
    }

    public void setWordTitle(String wordTitle) {
        this.wordTitle = wordTitle;
    }
}

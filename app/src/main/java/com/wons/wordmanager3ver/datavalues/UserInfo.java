package com.wons.wordmanager3ver.datavalues;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserInfo {
    @PrimaryKey
    @NonNull
    private int languageCodeSettingInt;
    private int userGradeInt;
    private int lv;
    private int expInt;
    private int savedWord;
    private int savedWordList;


    UserInfo(int userGradeInt, int languageCodeSettingInt) {
        this.userGradeInt = userGradeInt;
        this.lv = 1;
        this.expInt = 0;
        this.languageCodeSettingInt = languageCodeSettingInt;
        this.savedWord = 0;
        this.savedWordList = 0;
    }

    public void setLanguageCodeSettingInt(int languageCodeSettingInt) {
        this.languageCodeSettingInt = languageCodeSettingInt;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public void addLevel() {
        this.lv = lv++;
    }

    public void addExp() {
        this.expInt = expInt + 10;
    }

    public void reduceExp() {
        if (expInt != 0) {
            this.expInt = expInt - 10;
        }
    }

    public int getSavedWord() {
        return savedWord;
    }

    public int getSavedWordList() {
        return savedWordList;
    }

    public void setSavedWord(int savedWord) {
        this.savedWord = savedWord;
    }

    public void setSavedWordList(int savedWordList) {
        this.savedWordList = savedWordList;
    }

    public int getLanguageCodeSettingInt() {
        return languageCodeSettingInt;
    }

    public int getExpInt() {
        return expInt;
    }

    public int getUserGradeInt() {
        return userGradeInt;
    }

    public void setExpInt(int expInt) {
        this.expInt = expInt;
    }

    public void setUserGradeInt(int userGradeInt) {
        this.userGradeInt = userGradeInt;
    }

}


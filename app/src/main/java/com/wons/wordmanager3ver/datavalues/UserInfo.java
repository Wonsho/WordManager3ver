package com.wons.wordmanager3ver.datavalues;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserInfo {
    @PrimaryKey
    @NonNull
    private int languageCode;
    private int userGradeInt;
    private int lv;
    private int expInt;
    private int savedWord;
    private int savedWordList;


    public UserInfo(int languageCode) {
        this.userGradeInt = 0;
        this.lv = 1;
        this.expInt = 0;
        this.languageCode = languageCode;
        this.savedWord = 0;
        this.savedWordList = 0;
    }

    public void setLanguageCode(int languageCode) {
        this.languageCode = languageCode;
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
        if(this.expInt == 100) {
            this.expInt = 0;
            this.lv++;
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

    public int getLanguageCode() {
        return languageCode;
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


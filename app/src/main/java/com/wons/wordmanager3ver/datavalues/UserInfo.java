package com.wons.wordmanager3ver.datavalues;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.wons.wordmanager3ver.tool.Tools;

@Entity
public class
UserInfo {
    @PrimaryKey
    @NonNull
    private int languageCode;
    private int lv;
    private int expInt;
    private String startDay;

    public UserInfo(int languageCode) {
        this.lv = 1;
        this.expInt = 0;
        this.languageCode = languageCode;
        this.startDay = new Tools().getNoWDate();
    }

    public void setLanguageCode(int languageCode) {
        this.languageCode = languageCode;
    }

    public int getLv() {
        return lv;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public void addLevel() {
        this.lv = lv++;
    }

    public void addExp(int valueOfExp) {
        this.expInt += valueOfExp;
        if(this.expInt >= 100) {
            this.lv += this.expInt/100;
            this.expInt = this.expInt%100;
        }
    }

    public int getLanguageCode() {
        return languageCode;
    }

    public int getExpInt() {
        return expInt;
    }

    public void setExpInt(int expInt) {
        this.expInt = expInt;
    }

}


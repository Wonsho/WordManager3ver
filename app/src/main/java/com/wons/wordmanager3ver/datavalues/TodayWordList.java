package com.wons.wordmanager3ver.datavalues;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class TodayWordList {
    @PrimaryKey
    @NonNull
    public int id = 0;
    private int listCode;
    public boolean passOrNo;


    TodayWordList(int listCode, boolean passOrNo) {
        this.listCode = listCode;
        this.passOrNo = passOrNo;
    }

    public int getListCode() {
        return listCode;
    }

    public void setListCode(int listCode) {
        this.listCode = listCode;
    }

}

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
    private String listCode;

    TodayWordList() {
    }

    public String getListCode() {
        return listCode;
    }

    public void setListCode(String listCode) {
        this.listCode = listCode;
    }

    public ArrayList<Integer> getListCodes() {
        String[] strA = listCode.split(",");
        ArrayList<Integer> integers = new ArrayList<>();
        for (String s : strA) {
            integers.add(Integer.parseInt(s.trim()));
        }
        return integers;
    }

    public void setListCodeToString(ArrayList<Integer> listCodes) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < listCodes.size(); i++) {
            if (i == listCodes.size() - 1) {
                builder.append(String.valueOf(listCodes.get(i)));
            } else {
                builder.append(builder.append(String.valueOf(listCodes.get(i))+","));
            }
        }
        this.listCode = builder.toString();
    }
}

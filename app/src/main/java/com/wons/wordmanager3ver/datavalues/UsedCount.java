package com.wons.wordmanager3ver.datavalues;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UsedCount {
    @PrimaryKey
    @NonNull
    public int id;
    public String usedDay;
    public int usedCount;
    public boolean check;

    public UsedCount() {
        this.usedCount = 0;
        this.check = true;
    }

    public void addCount() {

        if (check) {
            usedCount++;
        }

    }

    public boolean checkCount() {
        if (usedCount == 5) {
            if(check) {
                check = false;
                return true;
            }
        }

        return false;
    }
}

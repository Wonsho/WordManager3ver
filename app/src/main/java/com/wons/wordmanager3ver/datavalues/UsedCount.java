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

    public UsedCount() {
        this.usedCount = 0;
    }

    public void addCount() {
        if (this.usedCount < 5) {
            this.usedCount++;
        }
    }
}

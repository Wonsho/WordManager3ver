package com.wons.wordmanager3ver.datavalues;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Setting {
    @PrimaryKey
    @NonNull
    private int settingCode;
    public int settingValue;

    public Setting(int settingCode, int settingValue) {
        this.settingCode = settingCode;
        this.settingValue = settingValue;
    }
    public int getSettingCode() {
        return settingCode;
    }

    public void setSettingCode(int settingCode) {
        this.settingCode = settingCode;
    }
}

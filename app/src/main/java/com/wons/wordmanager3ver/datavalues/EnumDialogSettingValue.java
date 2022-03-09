package com.wons.wordmanager3ver.datavalues;

public enum EnumDialogSettingValue {
    SHOW(1),
    NON(0);

    public int dialogCodeInt;

    EnumDialogSettingValue(int code) {
        this.dialogCodeInt = code;
    }
}

package com.wons.wordmanager3ver.datavalues;

public enum EnumLanguage {
    ENGLISH(0),
    CHINESE(1),
    JAPANESE(2);

    public int languageCodeInt;

    EnumLanguage(int code) {
        this.languageCodeInt = code;
    }
}

package com.wons.wordmanager3ver.datavalues;

public enum EnumLanguage {
    ENGLISH(0),
    CHINESE(1),
    JAPANESE(2);

    public int languageCodeInt;

    EnumLanguage(int code) {
        this.languageCodeInt = code;
    }

    public String getLanguage() {
        switch (languageCodeInt) {
            case 0 : {
                return "English";
            }
            case 1 : {
                return "Chinese";
            }
            case 2 : {
                return "Japanese";
            }
        }
        return null;
    }
}

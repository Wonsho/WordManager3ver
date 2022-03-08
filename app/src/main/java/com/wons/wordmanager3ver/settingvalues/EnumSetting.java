package com.wons.wordmanager3ver.settingvalues;

public enum EnumSetting {
    DIALOG_SETTING(1),
    TTS_SETTING(2),
    LIST_COUNT_SETTING(3),
    LIST_SETTING(4);

    public int settingCodeInt;
    EnumSetting(int code) {
        this.settingCodeInt = code;
    }


}

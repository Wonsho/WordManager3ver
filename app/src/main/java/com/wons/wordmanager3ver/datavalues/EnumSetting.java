package com.wons.wordmanager3ver.datavalues;

public enum EnumSetting {
    DIALOG_SHOW(0),
    TTS_SPEED(1),
    USER_RECOMMEND_TODAY_LIST_COUNT(2),
    USER_RECOMMEND_STYLE(3);

    public int settingCodeId;

    EnumSetting(int code) {
        this.settingCodeId = code;
    }
}

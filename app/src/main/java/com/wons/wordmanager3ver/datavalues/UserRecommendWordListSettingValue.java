package com.wons.wordmanager3ver.datavalues;

public enum UserRecommendWordListSettingValue {
    USER_RECOMMEND_STYLE_RECOMMEND(0),
    USER_RECOMMEND_STYLE_CHOICE(1);

    public int recommendStyleCodeInt;

    UserRecommendWordListSettingValue(int code) {
        this.recommendStyleCodeInt = code;
    }
}

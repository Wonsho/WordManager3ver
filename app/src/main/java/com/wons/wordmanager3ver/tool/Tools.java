package com.wons.wordmanager3ver.tool;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.datavalues.EnumLanguage;
import com.wons.wordmanager3ver.datavalues.EnumSetting;
import com.wons.wordmanager3ver.datavalues.Setting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.Locale;

public class Tools {
    private TextToSpeech tts;

    public String removeOverSpace(String str) {
        String title = str.trim();
        char[] cString = title.toCharArray();
        ArrayList<String> strArr = new ArrayList<>();
        for (int i = 0; i < cString.length; i++) {
            if (cString[i] == ' ') {
                if (cString[i + 1] != ' ') {
                    strArr.add(String.valueOf(cString[i]));
                }
            } else {
                strArr.add(String.valueOf(cString[i]));
            }
        }
        StringBuilder builder = new StringBuilder();
        for (String s : strArr) {
            builder.append(s);
        }
        return builder.toString();
    }


    public String getNoWDate() {
        Date date = new Date();
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        return fm.format(date);
    }

    public TextToSpeech speakTTS(Context context, EnumLanguage language) {
        float speechSpeed = MainViewModel.getSetting(EnumSetting.TTS_SPEED.settingCodeId).settingValue;
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    Log.e("TTS", "Passed");
                    switch (language) {
                        case ENGLISH: {
                            Log.e("TTS2", "Passed");
                            tts.setLanguage(Locale.ENGLISH);
                            tts.setSpeechRate(0.78f);
                            break;
                        }
//                        case CHINESE: {
//                            tts.setLanguage(Locale.CHINESE);
//                            break;
//                        }
//                        case JAPANESE: {
//                            tts.setLanguage(Locale.JAPANESE);
//                            break;
//                        }
                        default: {
                            //todo 나중에 셋팅값 등록
                        }
                    }
                }
            }
        });
        return tts;
    }

    public void speech(String str) {
        MainViewModel.tts.speak(str, TextToSpeech.QUEUE_FLUSH, null);
    }
}

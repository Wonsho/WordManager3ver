package com.wons.wordmanager3ver.tool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Tools {
    public String removeOverSpace(String str) {
        String title = str.trim();
        char[] cString = title.toCharArray();
        ArrayList<String> strArr = new ArrayList<>();
        for(int i = 0 ; i <cString.length ; i++) {
            if(cString[i] == ' ') {
                if(cString[i+1] != ' ') {
                    strArr.add(String.valueOf(cString[i]));
                }
            } else {
                strArr.add(String.valueOf(cString[i]));
            }
        }
        StringBuilder builder = new StringBuilder();
        for(String s : strArr) {
            builder.append(s);
        }
        return builder.toString();
    }


    public String getNoWDate() {
        Date date = new Date();
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        return fm.format(date);
    }
}

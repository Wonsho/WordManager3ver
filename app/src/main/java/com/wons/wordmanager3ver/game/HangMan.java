package com.wons.wordmanager3ver.game;

import java.util.ArrayList;

public class HangMan {
    public String word_title;
    public ArrayList<String> wordToArr;
    private int count;

    HangMan(String word_title) {
        this.word_title = word_title.trim().toUpperCase();
        this.wordToArr = new ArrayList<>();
        this.count = 0;
        init();
    }

    private void init() {

        for(int i=0 ; i<word_title.trim().length() ; i++) {
            if(word_title.charAt(i) == ' ') {
                wordToArr.add(" ");
            } else {
                wordToArr.add("_");
            }
        }
    }

    public int putSpell(String spell) {
        char[] charArr = word_title.toCharArray();

        if(word_title.contains(spell.trim().toUpperCase())) {

            for(int i=0 ; i<charArr.length ; i++) {
                if(String.valueOf(charArr[i]).equals(spell.trim().toUpperCase())) {
                    wordToArr.set(i, String.valueOf(charArr[i]));
                }
            }

            if(!wordToArr.contains("_")) {
                return -1;
            }

        } else {
            this.count += 1;
        }
        return count;
    }

}

package com.wons.wordmanager3ver.game.putspellatblankgame;

import java.util.ArrayList;

public class PutSpellGameData {
    public ArrayList<String> showWordArr;
    public ArrayList<String> spellMenuArr;
    public String wordTitle;
    public String word_korean;

    PutSpellGameData(ArrayList<String> showWordArr, ArrayList<String> spellMenuArr, String wordTitle, String word_korean) {
        this.showWordArr = showWordArr;
        this.spellMenuArr = spellMenuArr;
        this.wordTitle = wordTitle;
        this.wordTitle = word_korean;
    }

}

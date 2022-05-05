package com.wons.wordmanager3ver.gamefragment.game.makeword;

import android.annotation.SuppressLint;

import com.wons.wordmanager3ver.gamefragment.game.GameCode;

import java.util.ArrayList;
import java.util.Random;

public class MakeWordGame {
    private String originWordTitle;
    private ArrayList<String> originWordArr;
    private ArrayList<String> inputWordArr;
    private ArrayList<String> inputWordCopyArr;
    private ArrayList<String> spellMenuArr;
    private ArrayList<String> spellMenuCopyArr;
    private int inputIndex;
    private int life;
    private boolean check = true;
    public AccessGameData accessGameData;
    public GameAction gameAction;

    MakeWordGame(String wordTitle) {
        String word = wordTitle.trim();
        gameAction = new GameAction();
        accessGameData = new AccessGameData();
        initData(word);
    }

    class AccessGameData {

        public String getOriginWord() {
            return originWordTitle;
        }

        public ArrayList<String> getInputArr() {
            return inputWordArr;
        }

        public ArrayList<String> getSpellMenuArr() {
            return spellMenuArr;
        }

        public int getLife() {
            return life;
        }

        // this two method ( getWrongIndex and check )is using for check word spelling is correct or not
        // at first do check check is true or not when before setView
        // if !check -->
        //get nowIndex - 1
        // if wordArr.get(nowIndex - 1).equals(" ") --> -1 one more
        //don't save here index state
        // and then change Text Color that index spell is to red
        public int getWrongIndex() {

            if (inputIndex == 0) {
                return -1;
            }

            int wrongIndex = inputIndex - 1;

            if (originWordArr.get(wrongIndex).equals(" ")) {
                wrongIndex -= 1;
            }

            return wrongIndex;
        }

        public boolean check() {
            return check;
        }

    }

    class GameAction {

        public void restart() {
            life = getLife(originWordTitle);
            inputIndex = 0;
            inputWordArr = new ArrayList<>();
            inputWordArr.addAll(inputWordCopyArr);
            spellMenuArr = new ArrayList<>();
            spellMenuArr.addAll(spellMenuCopyArr);
            check = true;
        }

        public void onClickBackBtn() {

            if (inputIndex == 0) {
                return;
            }

            inputIndex = inputIndex - 1;

            if (originWordArr.get(inputIndex).equals(" ")) {
                inputIndex = inputIndex - 1;
            }

            String backSpell = inputWordArr.get(inputIndex);
            inputWordArr.set(inputIndex, "^");
            spellMenuArr.add(backSpell);
            check = true;

        }

        public void onClickReset() {
            check = true;

            inputIndex = 0;
            inputWordArr = new ArrayList<>();
            inputWordArr.addAll(inputWordCopyArr);
            spellMenuArr = new ArrayList<>();
            spellMenuArr.addAll(spellMenuCopyArr);

        }

        //-2 is notion wrong
        public int onClickSpell(String spell) {
            if (!check) {
                return -2;
            }

            int index = inputIndex;
            inputWordArr.set(index, spell);
            spellMenuArr.remove(spellMenuArr.indexOf(spell));

            String originSpell = originWordArr.get(index);

            if(!originSpell.equals(spell)) {
                life -= 1;
                check = false;
            }

            if(life == 0) {
                return GameCode.GAME_OVER;
            }

            if(!inputWordArr.contains("^")) {
                return GameCode.GAME_WIN;
            }

            index = index + 1;

            if(originWordArr.get(index).equals(" ")) {
                index = index + 1;
            }

            inputIndex = index;

            return -1;

        }

    }

    private void initData(String originWordTitle) {
        class MakeOriginArr {

            ArrayList<String> getOriginWordTitle(String word) {
                ArrayList<String> strArr = new ArrayList<>();
                char[] cArr = word.toCharArray();

                for (char c : cArr) {
                    strArr.add(String.valueOf(c));
                }

                return strArr;
            }
        }

        class MakeInputArr {

            ArrayList<String> getInputWordArr(ArrayList<String> originWordArr) {
                ArrayList<String> arr = new ArrayList<>();
                arr.addAll(originWordArr);

                for (int i = 0; i < arr.size(); i++) {
                    if (arr.get(i).equals(" ")) {

                    } else {
                        arr.set(i, "^");
                    }
                }

                return arr;
            }
        }

        class MakeSpellMenuArr {

            ArrayList<String> getSpellMenuArr(ArrayList<String> wordArr) {
                ArrayList<String> arr = new ArrayList<>();

                for (String s : wordArr) {
                    if (!s.equals(" ")) {
                        arr.add(s);
                    }
                }

                ArrayList<String> mixedArr = new ArrayList<>();

                while (true) {

                    if (arr.size() == 0) {
                        break;
                    }

                    int random = new Random().nextInt(arr.size());
                    String spell = arr.get(random);
                    mixedArr.add(spell);
                    arr.remove(random);
                }

                return mixedArr;
            }

        }

        MakeOriginArr makeOriginArr = new MakeOriginArr();
        MakeInputArr makeInputArr = new MakeInputArr();
        MakeSpellMenuArr makeSpellMenuArr = new MakeSpellMenuArr();


        this.originWordTitle = originWordTitle;
        this.life = getLife(originWordTitle);
        this.inputIndex = 0;
        this.originWordArr = makeOriginArr.getOriginWordTitle(originWordTitle);
        this.inputWordArr = makeInputArr.getInputWordArr(this.originWordArr);
        ArrayList<String> inputCopy = new ArrayList<>();
        inputCopy.addAll(this.inputWordArr);
        this.inputWordCopyArr = inputCopy;
        this.spellMenuArr = makeSpellMenuArr.getSpellMenuArr(this.originWordArr);
        ArrayList<String> spellMenuCopy = new ArrayList<>();
        spellMenuCopy.addAll(this.spellMenuArr);
        this.spellMenuCopyArr = spellMenuCopy;


    }

    int getLife(String originWord) {
        String[] strArr = originWord.split(" ");
        int spaceCount = strArr.length - 1;
        int wordLengthRemovedSpace = originWord.length() - spaceCount;
        @SuppressLint("DefaultLocale")
        int life = Integer.parseInt(String.format(
                "%.0f"
                , ((double) wordLengthRemovedSpace * 0.3)
        ));

        if(wordLengthRemovedSpace == 1) {
            return 1;
        }
        return life;
    }


}

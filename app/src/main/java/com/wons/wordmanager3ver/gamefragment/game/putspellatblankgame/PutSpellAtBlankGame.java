package com.wons.wordmanager3ver.gamefragment.game.putspellatblankgame;

import android.util.Log;

import com.wons.wordmanager3ver.gamefragment.game.GameCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PutSpellAtBlankGame {
    private String originWord;
    private String originWordKorean;
    public GameData gameData;

    PutSpellAtBlankGame(String originWord, String originWordKorean) {
        Log.e("init", originWord);
        this.originWord = originWord;
        this.originWordKorean = originWordKorean;
        gameData = new GameData();
    }

    public String getOriginWord() {
        return this.originWord;
    }

    public String getOriginWordKorean() {
        return this.originWordKorean;
    }

    class GameData {
        private int inputIndex;
        private ArrayList<Integer> indexOfShowWordArr;
        private ArrayList<String> showWordArr;
        private ArrayList<String> showWordArrCopy;
        private ArrayList<String> spellMenuArr;
        private ArrayList<String> spellMenuArrCopy;
        public ApproachData approachData;

        GameData() {
            approachData = new ApproachData();
        }

        class ApproachData {
            public ChangeData changeData;
            private InitData initData;

            ApproachData() {
                this.changeData = new ChangeData();
                this.initData = new InitData();
                initData();
            }

            public void initData() {
                initData.setInputIndex();
                initData.setPutIndexShowArr();
                initData.setShowWordArr();
                initData.setSpellMenuArr();
                initData.copyData();
                Log.e("initData", "passed");
            }

            public ArrayList<Integer> getPutIndexOfShowWordArr() {
                return indexOfShowWordArr;
            }

            public ArrayList<String> getShowWordArr() {
                return showWordArr;
            }

            public ArrayList<String> getSpellMenuArr() {
                return spellMenuArr;
            }

            class ChangeData {

                public void resetData() {
                    initData.setInputIndex();
                    ArrayList<String> arrShowWord = new ArrayList<>();
                    ArrayList<String> arrSpellMenu = new ArrayList<>();
                    arrShowWord.addAll(showWordArrCopy);
                    arrSpellMenu.addAll(spellMenuArrCopy);

                    showWordArr = arrShowWord;
                    spellMenuArr = arrSpellMenu;
                }

                public int inputSpell(String spell) {

                    if (inputIndex >= indexOfShowWordArr.size()) {
                        Log.e("inputSpell", "inputIndex >= indexOfShowWordArr.size()");
                        return -2;
                    }

                    class Check {

                        private int checkWord() {
                            StringBuilder builder = new StringBuilder();

                            for (String s : showWordArr) {
                                builder.append(s);
                            }

                            if (originWord.toUpperCase().equals(builder.toString())) {
                                return GameCode.GAME_WIN;
                            } else {
                                return GameCode.GAME_OVER;
                            }
                        }
                    }

                    int nowIndex = indexOfShowWordArr.get(inputIndex);
                    showWordArr.set(nowIndex, spell);
                    Log.e("spell", spell);
                    spellMenuArr.remove(spellMenuArr.indexOf(spell));
                    inputIndex += 1;

                    if (inputIndex == indexOfShowWordArr.size()) {
                        return new Check().checkWord();
                    }

                    return -1;
                }

                public void toBack() {

                    if (inputIndex == 0) {
                        return;
                    }

                    inputIndex -= 1;
                    int nowIndex = indexOfShowWordArr.get(inputIndex);
                    String spell = showWordArr.get(nowIndex);
                    showWordArr.set(nowIndex, "^");
                    spellMenuArr.add(spell);
                }

            }

            private class InitData {

                private void setInputIndex() {
                    inputIndex = 0;
                }

                private void setPutIndexShowArr() {

                    class MakeRandomSize {
                        private int getRandomCountSize(String word) {
                            int spaceCount = 0;
                            char[] charArr = word.toCharArray();

                            for (char c : charArr) {
                                if (c == ' ') {
                                    spaceCount++;
                                }
                            }
                            double randomSize = word.length() - spaceCount;
                            Log.e("wordLength", String.valueOf(word.length()));

                            if (randomSize == 1.0) {
                                return 1;
                            } else {
                                return Integer.parseInt(String.format("%.0f", (randomSize * 0.3)));
                            }
                        }
                    }

                    String word = originWord.toUpperCase();
                    char[] charArr = word.toCharArray();
                    int randomCount = new MakeRandomSize().getRandomCountSize(word);
                    Log.e("randomCount", String.valueOf(randomCount));
                    int count = 0;
                    ArrayList<Integer> indexArr = new ArrayList<>();

                    while (true) {

                        if (randomCount == count) {
                            break;
                        }

                        int randomNum = new Random().nextInt(word.length());

                        if (charArr[randomNum] == ' ' || indexArr.contains(randomNum))
                            continue;

                        indexArr.add(randomNum);
                        count++;
                    }

                    Collections.sort(indexArr);
                    indexOfShowWordArr = indexArr;
                }

                private void setShowWordArr() {
                    String word = originWord.toUpperCase();
                    ArrayList<Integer> indexArr = indexOfShowWordArr;
                    char[] charArr = word.toCharArray();
                    ArrayList<String> wordArr = new ArrayList<>();

                    for (char c : charArr) {
                        wordArr.add(String.valueOf(c));
                    }

                    for (int i : indexArr) {
                        wordArr.set(i, "^");
                    }


                    showWordArr = wordArr;
                }

                private void setSpellMenuArr() {

                    class Mix {
                        private ArrayList<String> mixArr(ArrayList<String> arr) {
                            ArrayList<String> strArr = arr;
                            ArrayList<String> mixedArr = new ArrayList<>();

                            while (true) {

                                if (strArr.size() == 0) {
                                    break;
                                }

                                int randomNum = new Random().nextInt(strArr.size());
                                String spell = strArr.get(randomNum);

                                strArr.remove(strArr.indexOf(spell));
                                mixedArr.add(spell);
                            }
                            return mixedArr;
                        }
                    }
                    ArrayList<Integer> indexArr = indexOfShowWordArr;
                    ArrayList<String> _spellMenuArr = new ArrayList<>();
                    String word = originWord.toUpperCase();

                    for (int i : indexArr) {
                        _spellMenuArr.add(String.valueOf(word.charAt(i)));
                    }

                    int addRandomSpellCount = _spellMenuArr.size();

                    for (int i = 0; i < addRandomSpellCount; i++) {
                        _spellMenuArr.add(String.valueOf(
                                (char) (new Random().nextInt(26) + 65)
                        ));
                    }

                    spellMenuArr = new Mix().mixArr(_spellMenuArr);
                }

                private void copyData() {
                    Log.e("copy", "passed");

                    ArrayList<String> showWordCopy = new ArrayList<>();
                    ArrayList<String> spellMenuCopy = new ArrayList<>();


                    showWordCopy.addAll(showWordArr);
                    spellMenuCopy.addAll(spellMenuArr);

                    showWordArrCopy = showWordCopy;
                    spellMenuArrCopy = spellMenuCopy;
                }

            }
        }
    }
}

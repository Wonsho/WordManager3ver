package com.wons.wordmanager3ver.game;

import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class ForCard {
    public final int WORD_TITLES = 0;
    public final int WORD_KOREAN = 1;
    public ArrayList<Word> wordArr;
    public ArrayList<WordInfo> wordInfoArr;

    ForCard(ArrayList<Word> wordArr, ArrayList<WordInfo> wordInfoArr) {
        this.wordArr = wordArr;
        this.wordInfoArr = wordInfoArr;
    }

    public HashMap<Integer, ArrayList<String>> getForCardDataByIndex(int index) {
        String nowTitle = wordArr.get(index).getWordTitle();
        String nowWordKorean = wordInfoArr.get(index).wordKorean;

        HashMap<Integer, ArrayList<String>> map = new HashMap<>();
        map.put(WORD_KOREAN, new ArrayList<>(Arrays.asList(nowWordKorean)));
        map.put(WORD_TITLES, makeForCard(nowTitle));

        return map;
    }


    private ArrayList<String> makeForCard(String wordTitle) {
        class MakeRandom {

            String[] vowel = {"a", "e", "i", "o", "u"}; //5
            String[] consonant = {"q", "w", "r", "t", "y", "p", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m"}; // 21
            String[] vowelUp = {"A", "E", "I", "O", "U"};

            ArrayList<String> vowelArr = new ArrayList<>(Arrays.asList(vowel));
            ArrayList<String> consonantArr = new ArrayList<>(Arrays.asList(consonant));
            ArrayList<String> vowelUpArr = new ArrayList<>(Arrays.asList(vowelUp));

            private String changeWord(int wordSize, int randomCount, ArrayList<String> wordToArr) {


                // 랜덤으로 들어가야 할 스펠링이 대문자냐 소문자냐 ?
                // 스페이스면 넣지 않는다
                // 단어의 사이즈가 2 이상일 경우 앞 뒤 자리를 바꾸지 않는다
                ArrayList<String> strArr = wordToArr;

                if (wordSize == 1) {
                    String s = wordToArr.get(0);
                    if (vowelArr.contains(s)) {
                        // 소문자 모음
                        while (true) {
                            int random = getVowelRandomNum();
                            String pickSpell = vowelArr.get(random);

                            if(!pickSpell.equals(s)) {
                                strArr.set(0, pickSpell);
                                break;
                            }
                        }

                    } else if (consonantArr.contains(s)) {
                        // 소문자 자음
                        while (true) {
                            int randomNum = getConsonantRandomNum();
                            String pickSpell = consonantArr.get(randomNum);

                            if(!pickSpell.equals(s)) {
                                strArr.set(0, pickSpell);
                                break;
                            }
                        }
                    } else if (vowelUpArr.contains(s)) {
                        // 대문자 모음

                        while (true) {
                            int randomNum = getVowelRandomNum();
                            String pickSpell = vowelUpArr.get(randomNum);

                            if(!pickSpell.equals(s)) {
                                strArr.set(0, pickSpell);
                                break;
                            }
                        }
                    } else {
                        //대문자 자음
                        while (true) {
                            int randomNum = getConsonantRandomNum();
                            String pickSpell = consonantArr.get(randomNum).toUpperCase();

                            if(!pickSpell.equals(s)) {
                                strArr.set(0,pickSpell);
                                break;
                            }
                        }
                    }

                } else if (wordSize == 2) {
                    int randomC = new Random().nextInt(2) + 1;
                    int count = 0;


                } else {

                }
            }

            private int getVowelRandomNum() {
                return new Random().nextInt(5);
            }

            private int getConsonantRandomNum() {
                return new Random().nextInt(21);
            }

            private ArrayList<String> inputCorrectWord(ArrayList<String> wordsArr, String wordTitle) {
                int randomCount = new Random().nextInt(4);
                ArrayList<String> arr = wordsArr;
                arr.set(randomCount, wordTitle);

                return arr;
            }
        }

        MakeRandom random = new MakeRandom();
        ArrayList<String> wordArr = new ArrayList<>();
        char[] cArr = wordTitle.trim().toCharArray();
        int spaceCount = 0;

        for (char c : cArr) {
            wordArr.add(String.valueOf(c));

            if (c == 'c') {
                spaceCount++;
            }
        }

        for (int i = 0; i < 4; i++) {
            ArrayList<String> word = new ArrayList<>();
            word.addAll(wordArr);
            int wordSizeRemovedSpace = wordTitle.length() - spaceCount;
            int randomCount = 0;

            if (wordSizeRemovedSpace == 1) {
                randomCount = 1;
            } else if (wordSizeRemovedSpace == 2) {
                randomCount = new Random().nextInt(3);
            } else {
                randomCount = new Random().nextInt(wordSizeRemovedSpace - 1);
            }

        }


    }
}

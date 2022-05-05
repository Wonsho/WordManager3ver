package com.wons.wordmanager3ver.gamefragment.game.fourcard;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class ForCard {
    public ArrayList<String> wordArr;  // 넘겨받은 오늘의 단어들
    public HashMap<Integer, ArrayList<String>> forCardMap;  // 인덱스에 맞는 단어 4개


    ForCard(ArrayList<String> wordArr) {
        this.wordArr = wordArr;
        makeForCardMap();
    }

    private void makeForCardMap() {
        HashMap<Integer, ArrayList<String>> cardMap = new HashMap<>();

        for (int i = 0; i < wordArr.size(); i++) {
            cardMap.put(i, makeForCard(wordArr.get(i)));
        }

        this.forCardMap = cardMap;
    }

    private ArrayList<String> makeForCard(String wordTitle) {
        class Utils {

            String[] vowels = {"a", "e", "i", "o", "u"};
            String[] consonant = {"q", "w", "r", "t", "y", "p", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m"};
            String[] vowelsUp = {"A", "E", "I", "O", "U"};
            String[] consonantsUp = {"Q", "W", "R", "T", "Y", "P", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"};

            String[] vowels2 = {"a", "e", "i", "o", "u"};
            String[] consonant2 = {"w", "r", "t", "y", "p", "s", "d", "f", "g", "h", "k", "l", "c", "v", "b", "n", "m"};
            String[] vowelsUp2 = {"A", "E", "I", "O", "U"};
            String[] consonantsUp2 = {"W", "R", "T", "Y", "P", "S", "D", "F", "G", "H","K", "L", "C", "V", "B", "N", "M"};

            //todo 다른 기호 일때

            ArrayList<String> vowelArr = new ArrayList<>(Arrays.asList(vowels));
            ArrayList<String> vowelsUpArr = new ArrayList<>(Arrays.asList(vowelsUp));
            ArrayList<String> consonantArr = new ArrayList<>(Arrays.asList(consonant));
            ArrayList<String> consonantUpArr = new ArrayList<>(Arrays.asList(consonantsUp));

            ArrayList<String> vowelArr2 = new ArrayList<>(Arrays.asList(vowels2));
            ArrayList<String> vowelsUpArr2 = new ArrayList<>(Arrays.asList(vowelsUp2));
            ArrayList<String> consonantArr2 = new ArrayList<>(Arrays.asList(consonant2));
            ArrayList<String> consonantUpArr2 = new ArrayList<>(Arrays.asList(consonantsUp2));


            public int wordRemovedSpaceLength(String word) {
                String[] arr = word.split(" ");
                int spaceCount = arr.length - 1;

                return word.length() - spaceCount;
            }

            public ArrayList<String> makeWordToArr(String wordTitle) {
                char[] cArr = wordTitle.toCharArray();
                ArrayList<String> strArr = new ArrayList<>();

                for (char c : cArr) {
                    strArr.add(String.valueOf(c));
                }

                return strArr;
            }

            public String makeArrToString(ArrayList<String> wordArr) {
                StringBuilder builder = new StringBuilder();

                for (String s : wordArr) {
                    builder.append(s);
                }

                return builder.toString();
            }

            @SuppressLint("DefaultLocale")
            public String makeRandomWord(String wordTitle) {
                ArrayList<String> wordArr = makeWordToArr(wordTitle);
                int wordLengthRemovedSpace = wordRemovedSpaceLength(wordTitle);
                int randomCount = 0;

                if (wordLengthRemovedSpace == 1) {
                    randomCount = 1;
                } else if (wordLengthRemovedSpace == 2) {
                    randomCount = new Random().nextInt(2) + 1;
                } else {
                    double n = (double) wordLengthRemovedSpace;
                    randomCount = new Random().nextInt(Integer.parseInt(String.format("%.0f", n * 0.2))) + 1;
                    Log.e("randomCount", String.valueOf(randomCount));
                }

                    for (int i = 0; i < randomCount; i++) {
                        int randomPlace;

                        while (true) {
                            randomPlace = new Random().nextInt(wordArr.size());
                            if (!wordArr.get(randomPlace).equals(" ")) {
                                break;
                            }
                        }

                        if (vowelArr.contains(wordArr.get(randomPlace))) {
                            String randomSpell = vowelArr2.get(new Random().nextInt(5));
                            wordArr.set(randomPlace, randomSpell);
                        } else if (vowelsUpArr.contains(wordArr.get(randomPlace))) {
                            String randomSpell = vowelsUpArr2.get(new Random().nextInt(5));
                            wordArr.set(randomPlace, randomSpell);
                        } else if (consonantArr.contains(wordArr.get(randomPlace))) {
                            String randomSpell = consonantArr2.get(new Random().nextInt(17));
                            wordArr.set(randomPlace, randomSpell);
                        } else if (consonantUpArr.contains(wordArr.get(randomPlace))) {
                            String randomSpell = consonantUpArr2.get(new Random().nextInt(17));
                            wordArr.set(randomPlace, randomSpell);
                        } else {
                        }
                    }

                return makeArrToString(wordArr);
            }

            public ArrayList<String> changeOneToCorrect(ArrayList<String> fourCard) {
                int num = new Random().nextInt(4);
                ArrayList<String> arr = fourCard;
                arr.set(num, wordTitle);

                return arr;
            }
        }

        Utils utils = new Utils();
        ArrayList<String> fourCardArr = new ArrayList<>();

        for (int i = 0; i < 4; i++) {

            while (true) {
                String word = utils.makeRandomWord(wordTitle);
                if (!word.equals(wordTitle)) {
                    fourCardArr.add(word);
                    break;
                }
            }
        }

        ArrayList<String> arr = utils.changeOneToCorrect(fourCardArr);

        return arr;
    }


}

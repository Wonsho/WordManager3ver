package com.wons.wordmanager3ver.game.fourcard;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class ForCard {

    public final int WORD_TITLES = 0;
    public final int WORD_KOREAN = 1;
    public ArrayList<String> wordArr;  // 넘겨받은 오늘의 단어들
    public HashMap<Integer, ArrayList<String>> forCardMap;  // 인덱스에 맞는 단어 4개

    //todo 랜덤 단어 4개를 만들고 그중 랜덤으로 맞는 단어르 넣는다.
    // 랜덤 단어를 뽑을때 기존 원래의 단어와 맞으면 안됨
    // 랜덤단어에서 랜덤 스펠링을 뽑을때 대문자 소문자 맞게 바꿈
    // 랜덤 단어만들때 원래의 단어에서 앞뒤는 바꾸지 않는다
    // 만약 띄어쓰기를 제외한 단어의 사이즈가 1~2일때는 제외
    // 단어에서 뽑은 랜덤카운트는 단어의 앞뒤를 제외한 크기 <단 1~2일때는 단어 사이즈에서 랜덤 돌림
    // 랜덤 카운트에서 랜덤으로 바꿀 스펠링 위치는 앞뒤를 제와한 랜덤 위치로 정한다 <단 1~2일경우 제외
    // 단어들을 받을때 단어 순서는 랜덤으로 가져온다
    // 사지선다는 목숨을 게임 오버를 하며 옳지 않는 답을 할시 빨간색으로 알려주며 목숨이 한개 까인다
    // 만약 숫자일 경우 제외*/

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
            String[] vowelsUp = {"q", "w", "r", "t", "y", "p", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m"};
            String[] consonant = {"A", "E", "I", "O", "U"};
            String[] consonantsUp = {"Q", "W", "R", "T", "Y", "P", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"};

            ArrayList<String> vowelArr = new ArrayList<>(Arrays.asList(vowels));
            ArrayList<String> vowelsUpArr = new ArrayList<>(Arrays.asList(vowelsUp));
            ArrayList<String> consonantArr = new ArrayList<>(Arrays.asList(consonant));
            ArrayList<String> consonantUpArr = new ArrayList<>(Arrays.asList(consonantsUp));

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

            public String makeRandomWord(String wordTitle) {
                ArrayList<String> wordArr = makeWordToArr(wordTitle);
                int wordLengthRemovedSpace = wordRemovedSpaceLength(wordTitle);


                for (int i = 0; i < wordLengthRemovedSpace; i++) {
                    int randomPlace = 0;

                    while (true) {
                        randomPlace = new Random().nextInt(wordArr.size());
                        if (!wordArr.get(randomPlace).equals(" ")) {
                            break;
                        }
                    }

                    if (vowelArr.contains(wordArr.get(randomPlace))) {
                        String randomSpell = vowelArr.get(new Random().nextInt(5));
                        wordArr.set(randomPlace, randomSpell);
                    } else if (vowelsUpArr.contains(wordArr.get(randomPlace))) {
                        String randomSpell = vowelsUpArr.get(new Random().nextInt(5));
                        wordArr.set(randomPlace, randomSpell);
                    } else if (consonantArr.contains(wordArr.get(randomPlace))) {
                        String randomSpell = consonantArr.get(new Random().nextInt(21));
                        wordArr.set(randomPlace, randomSpell);
                    } else if (consonantUpArr.contains(wordArr.get(randomPlace))) {
                        String randomSpell = consonantUpArr.get(new Random().nextInt(21));
                        wordArr.set(randomPlace, randomSpell);
                    } else {
                        Log.e("makeRandom", "Other");
                        wordArr.set(randomPlace, "0");
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
                if(!word.equals(wordTitle)) {
                    fourCardArr.add(word);
                    break;
                }
            }
        }

        ArrayList<String> arr = utils.changeOneToCorrect(fourCardArr);

        return arr;
    }


}

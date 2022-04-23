package com.wons.wordmanager3ver.game;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ForCard {
    public final int WORD_TITLES = 0;
    public final int WORD_KOREAN = 1;
    public ArrayList<String> wordArr;
    public HashMap<Integer, ArrayList<String>> forCardMap;

    ForCard(ArrayList<String> wordArr) {
        this.wordArr = wordArr;
        makeForCard();
    }

    private void makeForCard() {
        class Utils {
            public final int VOWEL = 0;
            public final int VOWEL_UP = 1;
            public final int CONSONANT = 2;
            public final int CONSONANT_UP = 3;
            String[] vowels = {"a", "e", "i", "o", "u"};//5
            String[] vowelsUP = {"A", "E", "I", "O", "U"};
            String[] consonant = {"q", "w", "r", "t", "y", "p", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m"};//21
            String[] consonantUP = {"Q", "W", "R", "T", "Y", "P", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"};

            ArrayList<String> vowelArr = new ArrayList<>(Arrays.asList(vowels));
            ArrayList<String> vowelUpArr = new ArrayList<>(Arrays.asList(vowelsUP));
            ArrayList<String> consonantArr = new ArrayList<>(Arrays.asList(consonant));
            ArrayList<String> consonantArrUP = new ArrayList<>(Arrays.asList(consonantUP));

            private int makeRandomCount(int lengthRemovedSpace) {
                if (lengthRemovedSpace == 1) {
                    return 1;
                } else if (lengthRemovedSpace == 2) {
                    return new Random().nextInt(2);
                } else {
                    return new Random().nextInt(lengthRemovedSpace - 2);
                }
            }

            private int getWordLengthRemovedSpace(String word) {
                char[] cArr = word.toCharArray();
                int count = 0;

                for (char c : cArr) {
                    if (c != ' ') {
                        count++;
                    }
                }

                return count;
            }

            private ArrayList<String> getWordToArr(String word) {
                ArrayList<String> wordArr = new ArrayList<>();
                char[] cArr = word.toCharArray();

                for (char c : cArr) {
                    wordArr.add(String.valueOf(c));
                }

                return wordArr;
            }

            private String makeWordToRandomChange(String wordTitle) {
                class Check {

                    private int checkSpell(String spell) {
                        if (vowelArr.contains(spell)) {
                            return VOWEL;
                        } else if (vowelUpArr.contains(spell)) {
                            return VOWEL_UP;
                        } else if (consonantArr.contains(spell)) {
                            return CONSONANT;
                        } else if (consonantArrUP.contains(spell)) {
                            return CONSONANT_UP;
                        } else {
                            //error
                            return -1;
                        }
                    }

                    private String getRandomSpell(String spell) {
                        int spellType = checkSpell(spell);

                        switch (spellType) {
                            case VOWEL: {
                                while (true) {
                                    int randomN = new Random().nextInt(5);
                                    String randomSpell = vowelArr.get(randomN);

                                    if (!randomSpell.equals(spell)) {
                                        return randomSpell;
                                    }
                                }
                            }

                            case VOWEL_UP: {
                                while (true) {
                                    int randomN = new Random().nextInt(5);
                                    String randomSpell = vowelUpArr.get(randomN);

                                    if(!randomSpell.equals(spell)) {
                                        return randomSpell;
                                    }

                                }
                            }

                            case CONSONANT: {
                                while (true) {
                                    int randomN = new Random().nextInt(21);
                                    String randomSpell = consonantArr.get(randomN);

                                    if(!randomSpell.equals(spell)) {
                                        return randomSpell;
                                    }
                                }
                            }

                            case CONSONANT_UP: {
                                while (true) {
                                    int randomN = new Random().nextInt(21);
                                    String randomSpell = consonantArrUP.get(randomN);

                                    if (!randomSpell.equals(spell)) {
                                        return randomSpell;
                                    }
                                }
                            }

                            default: {
                                Log.e("getRandomSpell", "Error");
                                return null;
                            }
                        }
                    }

                    private String makeWordArrToString(ArrayList<String> wordArr) {
                        StringBuilder builder = new StringBuilder();

                        for (String s : wordArr) {
                            builder.append(s);
                        }

                        return builder.toString();
                    }
                }

                Check check = new Check();
                ArrayList<String> wordArr = getWordToArr(wordTitle);
                int removedSpaceLength = getWordLengthRemovedSpace(wordTitle);
                int randomCount = makeRandomCount(removedSpaceLength);

                if (removedSpaceLength == 1) {
                    String spell = wordArr.get(0);
                    String randomSpell = check.getRandomSpell(spell);
                    wordArr.set(0, randomSpell);
                } else if (removedSpaceLength == 2) {

                    while(true) {

                        for(int i = 0; i<randomCount ; i++) {
                            int randomPlace = new Random().nextInt(wordArr.size());
                            wordArr.set(randomPlace, check.getRandomSpell(wordArr.get(randomPlace)));
                        }

                        if(!check.makeWordArrToString(wordArr).equals(wordTitle)) {
                            break;
                        }

                    }

                } else {
                    int randomN =

                }

                return check.makeWordArrToString(wordArr);
            }

            private ArrayList<String> makeOnCardToCorrect(ArrayList<String> wordFourCard, String correctWordTitle) {
                int random = new Random().nextInt(4);
                ArrayList<String> wordArr = wordFourCard;
                wordArr.set(random, correctWordTitle);
                return wordArr;
            }
        }
        Utils utils = new Utils();
        HashMap<Integer, ArrayList<String>> forCardMap = new HashMap<>();

        for (int i = 0; i < wordArr.size(); i++) {
            String wordTitle = wordArr.get(i);
            ArrayList<String> arr = new ArrayList<>();

            for (int j = 0; j < 4; j++) {
                arr.add(utils.makeWordToRandomChange(wordTitle));
            }

            forCardMap.put(i, utils.makeOnCardToCorrect(arr, wordArr.get(i)));
        }

        this.forCardMap = forCardMap;
    }


}

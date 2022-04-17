package com.wons.wordmanager3ver.game.putspellatblankgame;

import android.annotation.SuppressLint;
import android.util.Log;

import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordInfo;
import com.wons.wordmanager3ver.game.GameCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PutSpellAtBlankGame {
    private Word word;
    private WordInfo wordInfo;
    private int nowIndex;
    private ArrayList<Integer> indexArr;
    private ArrayList<String> showWordArr;
    private ArrayList<String> spellMenuArr;
    private InitClassData initClassData;
    public PutSpell putSpell;
    public Delete delete;
    public GetDATA getDATA;

    PutSpellAtBlankGame(Word word, WordInfo wordInfo) {
        this.word = word;
        this.wordInfo = wordInfo;
        this.nowIndex = 0;
        this.initClassData = new InitClassData();
        this.putSpell = new PutSpell();
        this.delete = new Delete();
        this.getDATA = new GetDATA();

        Log.e("_spellSize", String.valueOf(spellMenuArr.size()));
        Log.e("_showWordSize", String.valueOf(showWordArr.size()));
        Log.e("_indexSize", String.valueOf(indexArr.size()));
    }

    //todo 단어의 사이즈를 참고 0.3배 의 랜덤 자리 뽑기
    // 랜덤 스펠링은 뽑은 스펠링의 2배 */
    class InitClassData {

        private int wordSize; // -> 띄어쓰기없앤 단어 사이즈
        private String originWord; // -> 띄어쓰기 존재 , -> 대문자 아님
        private int randomNumCount;

        InitClassData() {
            this.originWord = word.getWordTitle().trim();
            getWordSizeRemovedSpace();
            setRandomNumCount();
            setIndexArr();
            setShowWordArr();
            setSpellMenuArr();

            Log.e("init", String.valueOf(this.wordSize));
            Log.e("init2", String.valueOf(this.originWord));
            Log.e("init3", String.valueOf(this.randomNumCount));

        }

        private void getWordSizeRemovedSpace() {
            String originWord = this.originWord;
            String[] strArr = originWord.split(" ");
            StringBuilder builder = new StringBuilder();

            for (String s : strArr) {
                builder.append(s.trim());
            }

            this.wordSize = builder.toString().length();
        }

        private void setRandomNumCount() {
            double size = (double) this.wordSize;
            double count = size * 0.3;
            Log.e("randomC", String.valueOf(count));

            int randomNumSize = Integer.parseInt(String.format("%.0f", count));

            this.randomNumCount = randomNumSize;
        }

        private void setIndexArr() {

            int randomNumCount = this.randomNumCount;

            if (indexArr == null) {
                indexArr = new ArrayList<>();
            }

            ArrayList<Integer> arr = new ArrayList<>();

            while (true) {

                if (randomNumCount == 0) {
                    break;
                }

                int randomIndex = new Random().nextInt(this.wordSize);
                String word = this.originWord;

                if (arr.contains(randomIndex) || word.charAt(randomIndex) == ' ')
                    continue;

                arr.add(randomIndex);
                randomNumCount -= 1;
            }

            indexArr = arr;
        }

        //빈칸은 ^ 로 표기
        private void setShowWordArr() {

            if (showWordArr == null) {
                showWordArr = new ArrayList<>();
            }

            String originWord = this.originWord.trim().toUpperCase();

            ArrayList<String> wordArr = new ArrayList<>();
            char[] charArr = originWord.toCharArray();

            for (char c : charArr) {
                wordArr.add(String.valueOf(c));
            }

            for (int i : indexArr) {
                wordArr.set(i, "^");
            }

            showWordArr = wordArr;

        }

        private void setSpellMenuArr() {

            if (spellMenuArr == null) {
                spellMenuArr = new ArrayList<>();
            }

            String originWord = this.originWord.trim().toUpperCase();
            ArrayList<Integer> index = indexArr;

            ArrayList<String> indexSpell = new ArrayList<>();

            for (int i : index) {
                indexSpell.add(String.valueOf(originWord.charAt(i)));
            }

            int randomSpellCount = index.size();
            ArrayList<String> randomSpellArr = new ArrayList<>();

            for (int i = 0; i < randomSpellCount; i++) {
                randomSpellArr.add(String.valueOf((char) (new Random().nextInt(26) + 65)));
            }

            ArrayList<String> finalArr = new ArrayList<>();
            finalArr.addAll(indexSpell);
            finalArr.addAll(randomSpellArr);

            ArrayList<String> mixedArr = new ArrayList<>();

            while (true) {

                if (finalArr.size() == 0) {
                    break;
                }

                int random = new Random().nextInt(finalArr.size());
                mixedArr.add(finalArr.get(random));
                finalArr.remove(random);
            }

            spellMenuArr = mixedArr;
        }

    }

    class PutSpell {

        //GameCode return --> 해당 사항 없을시 -1 return
        public int putSpell(String spell) {

            int spellIndex = indexArr.get(nowIndex);

            ArrayList<String> wordSpellArr = showWordArr;
            ArrayList<String> spellMenu = spellMenuArr;

            wordSpellArr.set(spellIndex, spell.toUpperCase());
            spellMenu.remove(spellMenu.indexOf(spell.toUpperCase()));

            showWordArr = wordSpellArr;
            spellMenuArr = spellMenu;

            increaseNowIndex();

            if (nowIndex == indexArr.size()) {
                return checkWord();
            }

            return -1;
        }

        private int checkWord() {
            StringBuilder builder = new StringBuilder();
            String originWord;
            String selectedWord;

            for (String s : showWordArr) {
                builder.append(s);
            }

            selectedWord = builder.toString();
            originWord = word.getWordTitle().trim().toUpperCase();

            if (originWord.equals(selectedWord.toUpperCase())) {
                return GameCode.GAME_WIN;
            } else {
                return GameCode.GAME_OVER;
            }

        }

        private void increaseNowIndex() {
            int index = nowIndex;
            index += 1;
            nowIndex = index;
        }

    }

    class Delete {

        public void deleteOneSpell() {

            if (nowIndex == 0) {
                return;
            }

            decreaseNowIndex();
            int index = indexArr.get(nowIndex);

            String needRemoveSpell = String.valueOf(word.getWordTitle().trim().toUpperCase().charAt(index));

            ArrayList<String> spellMenu = spellMenuArr;
            spellMenu.add(needRemoveSpell);
            spellMenuArr = spellMenu;

            ArrayList<String> showWord = showWordArr;
            showWord.set(index, "^");
            showWordArr = showWord;


        }

        public void resetAllSpell() {
            nowIndex = 0;
            initClassData.setShowWordArr();
            initClassData.setSpellMenuArr();
        }


        private void decreaseNowIndex() {

            if (nowIndex == 0) {
                return;
            }

            int index = nowIndex;
            index -= 1;
            nowIndex = index;
        }
    }

    class GetDATA {

        public PutSpellGameData getGameData() {
            PutSpellGameData data = new PutSpellGameData(
                    showWordArr,
                    spellMenuArr,
                    word.getWordTitle().trim(),
                    wordInfo.wordKorean
            );

            return data;
        }
    }

}

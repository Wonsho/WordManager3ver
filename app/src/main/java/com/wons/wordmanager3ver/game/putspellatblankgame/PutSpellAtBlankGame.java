package com.wons.wordmanager3ver.game.putspellatblankgame;

import android.util.Log;

import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordInfo;
import com.wons.wordmanager3ver.game.GameCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


//todo 기능들 :
// -알파벳 들어옴
// - 알파벳 지우기
// - 원래의 알파벳으로 덮어씌우기(같은 단어로 다시 시작할 시)
// - 알파벳이 다 차면 원래의 단어와 체크 하기*/
public class PutSpellAtBlankGame {
    public String originWord;
    public String originWordKorean;
    public Data data;

    PutSpellAtBlankGame(String originWord, String originWordKorean) {
        this.originWord = originWord;
        this.originWordKorean = originWordKorean;
        this.data = new Data();
    }


    class Data {
        private int inputCount; // 누른 횟수
        private String originWordToUp;
        private ArrayList<Integer> indexArr; // 누른 횟수에 따른 자리값 참조 데이터
        private ArrayList<String> showWordArr; // 빈칸을 가진체로 보여주는 단어 데이터
        private ArrayList<String> showWordArrCopy; // 초기화 했을때 덮어 씌울 초반 데이터
        private ArrayList<String> spellMenuArr; // 누를 스펠링이 있는 데이터
        private ArrayList<String> spellMenuArrCopy; // 누를 스펠링이 있는 데이터를 씌울 초반 데이터
        public ChangeData changeData;

        Data() {
            this.originWordToUp = originWord.trim().toUpperCase();
            this.changeData = new ChangeData();
            initData();
        }

        public ArrayList<String> getShowWordArr() {
            return this.showWordArr;
        }

        public ArrayList<String> getSpellMenuArr() {
            return this.spellMenuArr;
        }

        private void initData() {
            this.inputCount = 0;
            setInputCount();
            setIndexArr();
            setShowWordArr();
            setSpellMenuArr();
            showDataLog();
        }

        private void showDataLog() {
            show("inputCount", String.valueOf(inputCount));

            for (int i : indexArr) {
                show("inputIndex", String.valueOf(i));
            }

            for (String s : showWordArr) {
                show("showWord", s);
            }

            for (String s : spellMenuArr) {
                show("spellMenu", s);
            }

        }

        private void show(String title, String value) {
            Log.e(title, value);
        }

        private void setSpellMenuArr() {
            String word = this.originWordToUp;
            ArrayList<Integer> indexArr = this.indexArr;
            ArrayList<String> pickSpellArr = new ArrayList<>();

            for (int i : indexArr) {
                pickSpellArr.add(String.valueOf(word.charAt(i)));
            }

            this.spellMenuArr = addMoreSpellAndMix(pickSpellArr);
            this.spellMenuArrCopy = addMoreSpellAndMix(pickSpellArr);

        }

        private ArrayList<String> addMoreSpellAndMix(ArrayList<String> spellArr) {
            int needPickCount = spellArr.size();
            ArrayList<String> randomSpell = new ArrayList<>();

            for (int i = 0; i < needPickCount; i++) {
                randomSpell.add(String.valueOf((char) (new Random().nextInt(26) + 65)));
            }

            ArrayList<String> needMixSpellArr = new ArrayList<>();
            needMixSpellArr.addAll(spellMenuArr);
            needMixSpellArr.addAll(randomSpell);
            ArrayList<String> spellMenuArr = new ArrayList<>();
            while (true) {

                if (needMixSpellArr.size() == 0) {
                    break;
                }

                int randomNum = new Random().nextInt(needMixSpellArr.size());
                String pickedSpell = needMixSpellArr.get(randomNum);
                spellMenuArr.add(pickedSpell);
                needMixSpellArr.remove(needMixSpellArr.indexOf(pickedSpell));
            }

            return spellMenuArr;
        }

        private void setShowWordArr() {
            ArrayList<Integer> indexArr = this.indexArr;
            String word = this.originWordToUp;
            ArrayList<String> wordArr = new ArrayList<>();

            char[] cArr = word.toCharArray();

            for (int i = 0; i < cArr.length; i++) {
                if (cArr[i] == ' ') {
                    wordArr.add(" ");
                } else if (indexArr.contains(i)) {
                    wordArr.add("^");
                } else {
                    wordArr.add(String.valueOf(cArr[i]));
                }
            }

            this.showWordArr = wordArr;
            this.showWordArrCopy = wordArr;
        }

        private void setInputCount() {
            this.inputCount = 0;
        }

        private void setIndexArr() {
            int randomCount = getCountOfRandom(getSizeOfRemovedSpace(this.originWordToUp));
            int count = 0;
            ArrayList<Integer> arrOfIndex = new ArrayList<>();

            while (true) {

                if (count == randomCount) {
                    break;
                }

                int randomNum = new Random().nextInt(originWord.length());

                if (this.originWordToUp.charAt(randomNum) == ' ' || arrOfIndex.contains(randomNum))
                    continue;

                arrOfIndex.add(randomNum);
                count++;
            }

            Collections.sort(arrOfIndex);

            this.indexArr = arrOfIndex;
        }

        private int getCountOfRandom(int wordSizeRemovedSpace) {
            double size = (double) wordSizeRemovedSpace;

            if (size == 1) {
                return 1;
            } else {
                size = size * 0.3;
                return Integer.parseInt(String.format("%.0f", size));
            }

        }

        private int getSizeOfRemovedSpace(String word) {
            String[] strArr = word.split(" ");
            StringBuilder builder = new StringBuilder();

            for (String s : strArr) {
                builder.append(s);
            }

            return builder.toString().length();
        }

        class ChangeData {
            //todo 스펠링이 들어올때
            // 스펠링 지울때 */

            public int inputSpell(String spell) {
                if (inputCount >= indexArr.size()) {
                    Log.e("inputSpell", "finish");
                    return -2;
                }
                int nowSpellIndex = indexArr.get(inputCount);
                ArrayList<String> wordArr = showWordArr;
                wordArr.set(nowSpellIndex, spell);
                showWordArr = wordArr;
                inputCount += 1;

                if (inputCount == indexArr.size()) {
                    Log.e("inputDone", "finish");
                    return checkWord();
                }

                return -1;
            }

            private int checkWord() {
                StringBuilder builder = new StringBuilder();

                for (String s : showWordArr) {
                    builder.append(s.toUpperCase());
                }

                if(originWord.equals(builder.toString())) {
                    return GameCode.GAME_WIN;
                } else  {
                    return GameCode.GAME_OVER;
                }
            }

            public void inputReset() {
                inputCount = 0;
                showWordArr = showWordArrCopy;
                spellMenuArr = spellMenuArrCopy;
            }

            public void inputBack() {

                if (inputCount == 0) {
                    Log.e("inputBack", "inputCount is 0");
                    return;
                }

                inputCount -= 1;
                int nowSpellCount = indexArr.get(inputCount);
                String needRemoveSpell = showWordArr.get(nowSpellCount);
                showWordArr.set(nowSpellCount, "^");
                spellMenuArr.add(needRemoveSpell);
            }

        }

    }
}

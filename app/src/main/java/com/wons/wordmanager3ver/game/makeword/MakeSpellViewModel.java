package com.wons.wordmanager3ver.game.makeword;

import android.mtp.MtpConstants;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.game.gameCode.GameCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MakeSpellViewModel extends ViewModel {
    private MyDao dao = MainViewModel.dao;

    //todo 단순 데이터 -> 램덤으로 골라진 단어
    private MutableLiveData<Word> baseWord; // 처음 선택된 단어

    //todo View용도 -> 고른 데이터
    private MutableLiveData<ArrayList<String>> showWord;

    //todo View용도 -> 골라야 되는 데이터
    private MutableLiveData<ArrayList<String>> choiceSpell;  // (골라야 되는 데이터 -> 바뀜)스펠링 선택하면 하나 하나 삭제

    //todo 단순 데이터 -> 고른 데이터 저장
    private MutableLiveData<ArrayList<String>> pickedSpell; // (고른뒤 추가 되는 데이터 -> 바낌 )고른 스펠링을 보여주는 정보 --> 나중에 이 둘을 비교 원래의 단어와 같을경우 승리

    public void initData(int gameCode) {

        if (gameCode == GameCode.START && (baseWord == null || baseWord.getValue() == null)) {
            return;
        }

        if (this.baseWord == null) {
            this.baseWord = new MutableLiveData<>();
        }

        if (this.showWord == null) {
            this.showWord = new MutableLiveData<>();
        }

        if (this.choiceSpell == null) {
            this.choiceSpell = new MutableLiveData<>();
        }

        if (this.pickedSpell == null) {
            this.pickedSpell = new MutableLiveData<>();
        }

        choiceRandomWord();
    }

    private void choiceRandomWord() {
        TodayWordList[] todayWordLists = dao.getAllTodayListByLanguageCode(
                MainViewModel.getUserInfo().getLanguageCode()
        );
        ArrayList<Word> words = new ArrayList<>();

        for (TodayWordList todayWordList : todayWordLists) {
            words.addAll(new ArrayList<>(Arrays.asList(dao.getAllWordByLanguageByListCode(
                    MainViewModel.getUserInfo().getLanguageCode(),
                    todayWordList.getListCode()
            ))));
        }

        int randomNum = new Random().nextInt(words.size());
        this.baseWord.setValue(words.get(randomNum));
        makeShowWord();
    }

    private void makeShowWord() {
        ArrayList<String> strArr = new ArrayList<>();
        String word = this.baseWord.getValue().getWordTitle().trim().toUpperCase();
        char[] wordToChars = word.toCharArray();


        //todo if .equls " " --> 빈칸
        // .equals "^" --> 텍스트만 빈것 */
        for (char strChar : wordToChars) {
            if (String.valueOf(strChar).equals(" ")) {
                strArr.add(" ");
            } else {
                strArr.add("^");
            }
        }
        this.showWord.setValue(strArr);
        makeChoiceSpell();
    }

    private void makeChoiceSpell() {
        ArrayList<String> strArr = new ArrayList<>();
        String word = this.baseWord.getValue().getWordTitle().trim().toUpperCase();
        char[] wordToChars = word.toCharArray();

        for (char c : wordToChars) {
            if (String.valueOf(c).equals(" ")) continue;
            strArr.add(String.valueOf(c));
        }

        ArrayList<String> choiceSpell = new ArrayList<>();

        while (true) {

            if (strArr.size() == 0) {
                break;
            }

            int randomNum = new Random().nextInt(strArr.size());
            choiceSpell.add(strArr.get(randomNum));
            strArr.remove(randomNum);

        }
        this.choiceSpell.setValue(choiceSpell);
    }


    //todo null 이면 -1 , 칸을 다채웠으면 1, 아니면 0
    public int inputSpell(String spell) {
        if (spell.isEmpty()) {
            return -1;
        }
        ArrayList<String> pickedSpellArr = this.pickedSpell.getValue();
        pickedSpellArr.add(spell.trim().toUpperCase());
        this.pickedSpell.setValue(pickedSpellArr);


        changedShowWord();

        String[] strings = this.baseWord.getValue().getWordTitle().trim().toUpperCase().split(" ");
        StringBuilder stringBuilder = new StringBuilder();

        for(String s : strings) {
            stringBuilder.append(s.trim());
        }

        String word = stringBuilder.toString();

        if(this.pickedSpell.getValue().size() == word.length()) {
            return 1;
        } else {
            return 0;
        }

    }

    private void changedShowWord() {
        ArrayList<String> showWord = this.showWord.getValue();
        int index = 0;
        if (this.pickedSpell.getValue() == null || this.pickedSpell == null) {
            Log.e("changedShowWord", "is null");
            return;
        }

        ArrayList<String> pickSpellArr = this.pickedSpell.getValue();

        while (true) {
            try {
                if (showWord.get(index).equals(" ")) continue;
                String putSpell = pickSpellArr.get(index);
                showWord.set(index, putSpell);
                index++;
            } catch (Exception e) {
                break;
            }
        }

        this.showWord.setValue(showWord);
    }

    public void onClickReplaceBtn() {
        makeShowWord();
        this.pickedSpell.setValue(new ArrayList<>());
    }

    public void onClickBackBtn() {
        if(this.pickedSpell.getValue().size() == 0) {
            return;
        }


    }

    public int checkAnswer() {
        String word = this.baseWord.getValue().getWordTitle().trim().toUpperCase();
        StringBuilder stringBuilder = new StringBuilder();
        String[] strings = word.split(" ");

        for(String s : strings) {
            stringBuilder.append(s.trim());
        }

        String removedSpaceWord = stringBuilder.toString().toUpperCase();

        StringBuilder stringBuilder1 = new StringBuilder();

        for(String s : this.pickedSpell.getValue()) {
            stringBuilder1.append(s.trim().toUpperCase());
        }

        String pickedWord = stringBuilder1.toString();

        if(removedSpaceWord.equals(pickedWord)) {
            return GameCode.GAME_WIN;
        } else {
            return GameCode.GAME_OVER;
        }

    }

    public String getWordKorean() {
        return dao.getWordInfo(
                this.baseWord.getValue().getWordTitle().trim().toUpperCase(),
                MainViewModel.getUserInfo().getLanguageCode()
                ).wordKorean;
    }
}

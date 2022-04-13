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

    private final int DELETE = 0;
    private final int ADD = 1;

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
    }

    public void startGame(int gameCode) {

        switch (gameCode) {
            case GameCode.START: {
                if (baseWord == null || baseWord.getValue() == null) {
                    return;
                }
                choiceRandomWord();
                makeShowWord();
                makeChoiceSpell();
                break;
            }

            case GameCode.RESTART_OTHER_WORD: {
                choiceRandomWord();
                makeShowWord();
                makeChoiceSpell();
                this.pickedSpell.setValue(new ArrayList<>());
                break;
            }

            case GameCode.RESTART_SAME_WORD: {
                makeShowWord();
                makeChoiceSpell();
                this.pickedSpell.setValue(new ArrayList<>());
                break;
            }
        }

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

    // 만약 pickSpell의 사이즈가 0이면 새로 만들고 아니면 pick을 바탕으로 만들기
    private void makeShowWord() {

        if(this.pickedSpell.getValue().size() == 0) {
            initShowWord();
        } else {
            initShowWord();
            ArrayList<String> pickArr = this.pickedSpell.getValue();
            ArrayList<String> showWord = this.showWord.getValue();

            int spaceCount = 0;

            for(int i=0 ; i<pickArr.size() ; i++) {
                int index = i + spaceCount;

                if(showWord.get(index).equals(" ")) {
                    spaceCount++;
                    continue;
                }

                showWord.set(index, pickArr.get(i));
            }
            this.showWord.setValue(showWord);
        }
    }

    private void initShowWord() {
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

    // 들어온 값이 없으면 -1, 선택을 다했으면 1, 아니면 0
    public int inputSpell(String spell) {

        if(spell.isEmpty()) {
            return -1;
        }

        ArrayList<String> pickArr = this.pickedSpell.getValue();
        pickArr.add(spell.trim().toUpperCase());
        this.pickedSpell.setValue(pickArr);
        makeShowWord();
        changeChoiceSpell(DELETE, spell);

        String originWord = this.baseWord.getValue().getWordTitle().trim().toUpperCase();
        StringBuilder builder = new StringBuilder();
        String[] strings = originWord.split(" ");

        for(String s : strings) {
            builder.append(s.trim());
        }

        String removedSpaceOriginWord = builder.toString();

        if(removedSpaceOriginWord.length() == this.pickedSpell.getValue().size()) {
            return 1;
        } else {
            return 0;
        }
    }

    private void changeChoiceSpell(int action, String spell) {
        ArrayList<String> choiceSpell = this.choiceSpell.getValue();

        if(action == DELETE) {
            //todo pick의 마지막껄 가져와서 같은 값 지우기
            int index = choiceSpell.indexOf(spell);
            choiceSpell.remove(index);
        }

        if(action == ADD) {
            choiceSpell.add(spell.trim());
        }

        this.choiceSpell.setValue(choiceSpell);
    }

    public void onclickBackBtn() {

        if(this.pickedSpell.getValue().size() == 0) {
            return;
        }
        ArrayList<String> pikArr = this.pickedSpell.getValue();
        String spell = pickedSpell.getValue().get(pikArr.size()-1);
        pikArr.remove(pikArr.size()-1);
        this.pickedSpell.setValue(pikArr);
        makeShowWord();
        changeChoiceSpell(ADD, spell);
    }

    public void onClickReplaceBtn() {
        startGame(GameCode.RESTART_SAME_WORD);
    }

    public String getWordKorean() {
        return dao.getWordInfo(
                this.baseWord.getValue().getWordTitle().trim().toUpperCase(),
                MainViewModel.getUserInfo().getLanguageCode()
                ).wordKorean;
    }
}

package com.wons.wordmanager3ver.game.makeword;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.datavalues.Word;

import java.util.ArrayList;

public class MakeSpellViewModel extends ViewModel {
    MutableLiveData<Word> baseWord; // 처음 선택된 단어
    MutableLiveData<ArrayList<String>> showWord; // 스펠링 선택할때 조합
    MutableLiveData<ArrayList<String>> choiceSpell;  // 스펠링 선택하면 하나 하나 삭제

    public void initData() {

    }
}

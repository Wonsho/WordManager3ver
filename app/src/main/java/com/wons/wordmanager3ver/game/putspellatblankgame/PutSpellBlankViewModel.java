package com.wons.wordmanager3ver.game.putspellatblankgame;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordInfo;
import com.wons.wordmanager3ver.game.GameCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PutSpellBlankViewModel extends ViewModel {
    private MyDao dao = MainViewModel.dao;
    public MutableLiveData<PutSpellAtBlankGame> gameLiveData;



}

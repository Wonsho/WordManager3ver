package com.wons.wordmanager3ver.game.fourcard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;

public class FourCardViewModel extends ViewModel {
    private MyDao dao = MainViewModel.dao;
    private MutableLiveData<ForCard> gameLiveData;
    private MutableLiveData<Integer> life;

}

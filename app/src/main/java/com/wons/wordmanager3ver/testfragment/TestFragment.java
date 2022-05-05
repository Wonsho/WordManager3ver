package com.wons.wordmanager3ver.testfragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wons.wordmanager3ver.databinding.FragmentTestBinding;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.testfragment.testword.TestActivity;

import java.util.ArrayList;

public class TestFragment extends Fragment {

    private FragmentTestBinding binding;
    private TestFragmentViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(TestFragmentViewModel.class);
        binding = FragmentTestBinding.inflate(inflater, container,false );
        onClick();
        setWordQuantityText();

        return binding.getRoot();
    }


    private void onClick() {
        binding.btnTest.setOnClickListener(v -> {

            boolean check = true;

            ArrayList<TodayWordList> todayWordLists = viewModel.getTodayWordList();
            for(TodayWordList todayWordList : todayWordLists) {
                check = todayWordList.passOrNo;
                if(viewModel.getWordCountOfTodayWordList(todayWordList) == 0) {
                    Toast.makeText(getActivity(), "단어가 없는 단어장이 있습니다", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if(check) {
                Toast.makeText(getActivity(), "모든 시험을 합격하셨습니다\n새로고침 해주세요", Toast.LENGTH_SHORT).show();
                return;
            }

            startActivity(new Intent(getActivity(), TestActivity.class));
        });
    }

    private void setWordQuantityText() {
        int quantity = 3;
        binding.tvWordCount.setText(String.valueOf(quantity));
    }

}
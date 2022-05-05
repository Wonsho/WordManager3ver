package com.wons.wordmanager3ver.studyfragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wons.wordmanager3ver.databinding.FragmentStudyBinding;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.studyfragment.studyword.StudyActivity;

import java.util.ArrayList;

public class StudyFragment extends Fragment {
    private FragmentStudyBinding binding;
    private StudyFragmentViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudyBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(StudyFragmentViewModel.class);
        onClick();
        setWordQuantity();
        return binding.getRoot();
    }

    private void onClick() {

        binding.btnTest.setOnClickListener(v -> {
            ArrayList<TodayWordList> todayWordLists = viewModel.getTodayWordList();
            for(TodayWordList todayWordList : todayWordLists) {
                if(viewModel.getWordCountOfTodayWordList(todayWordList) == 0) {
                    Toast.makeText(getActivity(), "단어가 없는 단어장이 있습니다", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            startActivity(new Intent(getActivity(), StudyActivity.class));
        });
    }

    private void setWordQuantity() {
        int quantity = viewModel.getTodayWordQuantity();
        binding.tvWordCount.setText(String.valueOf(quantity));
    }

    @Override
    public void onStart() {
        super.onStart();
        setWordQuantity();
    }

}
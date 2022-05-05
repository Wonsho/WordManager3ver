package com.wons.wordmanager3ver.mainutils.dialogutils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.datavalues.UserRecommendWordListSettingValue;

public class DialogUtilsInHomeFragment {
    int listRecommendCode;
    public AlertDialog getDialogForTodayWordList(Context context, int savedCount,int savedRecommendValue, CallBackInHomeFragment callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_setting_today_wordlist, null);
        ImageView tv_minus = view.findViewById(R.id.btn_minus);
        ImageView tv_plus = view.findViewById(R.id.btn_plus);
        TextView tv_num = view.findViewById(R.id.tv_num);
        tv_num.setText(String.valueOf(savedCount).trim());
        RadioGroup rg = view.findViewById(R.id.rg);
        tv_minus.setOnClickListener(v -> {
            if (Integer.parseInt(tv_num.getText().toString().trim()) == 1) {

            } else {
                tv_num.setText(String.valueOf(Integer.parseInt(tv_num.getText().toString().trim()) - 1));
            }
        });
        tv_plus.setOnClickListener(v -> {
            if(Integer.parseInt(tv_num.getText().toString().trim()) == MainViewModel.getAllWordListByLanguageCode(MainViewModel.getUserInfo().getLanguageCode()).size()) {
                Toast.makeText(context, "저장된 단어장 갯수를 초과할수 없습니다", Toast.LENGTH_SHORT).show();
            } else {
                tv_num.setText(String.valueOf(Integer.parseInt(tv_num.getText().toString().trim()) + 1));
            }
        });

        this.listRecommendCode = savedRecommendValue;
        Log.e("dialog", String.valueOf(savedRecommendValue));

        RadioButton rb_choice = rg.findViewById(R.id.rb_choice);
        RadioButton rb_recommend = rg.findViewById(R.id.rb_recommend);

        if(listRecommendCode == UserRecommendWordListSettingValue.USER_RECOMMEND_STYLE_RECOMMEND.recommendStyleCodeInt) {
            rb_recommend.setChecked(true);
        } else {
            rb_choice.setChecked(true);
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_recommend: {
                        listRecommendCode = UserRecommendWordListSettingValue.USER_RECOMMEND_STYLE_RECOMMEND.recommendStyleCodeInt;
                        break;
                    }
                    case R.id.rb_choice: {
                        listRecommendCode = UserRecommendWordListSettingValue.USER_RECOMMEND_STYLE_CHOICE.recommendStyleCodeInt;
                        break;
                    }
                }
            }
        });
        builder.setView(view);
        builder.setTitle("오늘의 단어장 셋팅");
        builder.setPositiveButton("셋팅완료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callback.callback(listRecommendCode, Integer.parseInt(tv_num.getText().toString().trim()));
            }
        });
        return builder.create();
    }
}

package com.vnwarriors.tastyclarify.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vnwarriors.tastyclarify.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateRecipes extends AppCompatActivity {
    @BindView(R.id.cbTerm)
    AppCompatCheckBox cbTerm;
//    @BindView(R.id.rbServe)
//    RangeBar rbServe;
    @BindView(R.id.tvServe)
    TextView tvServe;
    @BindView(R.id.sbServe)
    AppCompatSeekBar sbServe;
    @BindView(R.id.llIngredient)
    LinearLayout llIngredient;

    //    https://github.com/oli107/material-range-bar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipes);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
//        init serve
        tvServe.setText(String.valueOf(3));
//        rbServe.setSeekPinByIndex(2);
        onRangeBarServeChange();
//        init
        String termText = cbTerm.getText().toString().trim();
        cbTerm.setText(Html.fromHtml(termText + " <font color=\"#64c960\">" + "Term &amp; Conditions" + "</font>"));

    }

    private void onRangeBarServeChange() {
        sbServe.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvServe.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}

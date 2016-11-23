package com.vnwarriors.tastyclarify.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vnwarriors.tastyclarify.R;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.ivAddGredient)
    ImageView ivAddIngredient;
    @BindView(R.id.etGredient)
    EditText etGredient;

    @BindView(R.id.cbMan)
    AppCompatCheckBox cbMan;
    @BindView(R.id.cbWoman)
    AppCompatCheckBox cbWoman;

    List<String> ingredient = new ArrayList<>();

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
        onRangeBarServeChange();

//        init term
        String termText = cbTerm.getText().toString().trim();
        cbTerm.setText(Html.fromHtml(termText + " <font color=\"#64c960\">" + "Term &amp; Conditions" + "</font>"));


//        init ingredient
        onAddIngredientClick();

//        init gender
        onGenderClick();
    }

    private void onGenderClick() {
        cbMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbWoman.setChecked(false);
            }
        });

        cbWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbMan.setChecked(false);
            }
        });
    }

    private String Ingredient = "rlIngredientNo";
    private String characterSpe = "- ";

    private void onAddIngredientClick() {
        ivAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredientText = etGredient.getText().toString().trim();
                ingredient.add(ingredientText);
                etGredient.setText("");

                View view = LayoutInflater.from(v.getContext()).inflate(R.layout.gredient_cancel, llIngredient, false);
                TextView tvIngredient = (TextView) view.findViewById(R.id.tvIngredient);
                tvIngredient.setText(characterSpe + ingredientText);
                ImageView ivCancelIngredient = (ImageView) view.findViewById(R.id.ivCancelIngredient);
                ivCancelIngredient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        llIngredient.removeView(view);
                    }
                });

                llIngredient.addView(view);

                RelativeLayout relativeLayout = (RelativeLayout) view;

            }
        });
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

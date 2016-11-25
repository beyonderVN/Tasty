package com.vnwarriors.tastyclarify.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vnwarriors.tastyclarify.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateRecipesActivity extends AppCompatActivity {
    @BindView(R.id.cbTerm)
    AppCompatCheckBox cbTerm;

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

    @BindView(R.id.rbDifficult)
    AppCompatRatingBar rbDifficult;
    @BindView(R.id.sbDifficult)
    AppCompatSeekBar sbDifficult;
    @BindView(R.id.tvDifficultLevel)
    TextView tvDifficultLevel;

    Map<String, String> ingredient = new HashMap<>();

    //    https://github.com/oli107/material-range-bar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipes);

        ButterKnife.bind(this);

        initView();
    }
    private DatabaseReference mFirebaseDatabaseReference;
    private void initView() {
//        init serve
        tvServe.setText(String.valueOf(3));
        sbServe.setProgress(3);
        onRangeBarServeChange();

//        init term
        String termText = cbTerm.getText().toString().trim();
        cbTerm.setText(Html.fromHtml(termText + " <font color=\"#64c960\">" + "Term &amp; Conditions" + "</font>"));

//        init Difficult
        rbDifficult.setProgress(1);
        sbDifficult.setProgress(4);
        onSeekBarChange();

//        init ingredient
        onAddIngredientClick();

//        init gender
        onGenderClick();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        findViewById(R.id.btnSendRecipe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for (PostModel postModel: CloneDataUtils.getRateList("recipes.json",CreateRecipesActivity.this)
//                     ) {
//                    mFirebaseDatabaseReference.child("posts").push().setValue(postModel);
//                }

            }
        });
    }

    private void onSeekBarChange() {
        sbDifficult.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int rate = progress / 4;
                switch (rate) {
                    case 1:
                        tvDifficultLevel.setText("Easy");
                        break;
                    case 2:
                        tvDifficultLevel.setText("Medium");
                        break;
                    case 3:
                        tvDifficultLevel.setText("Hard");
                        break;
                }
                rbDifficult.setProgress(rate);
                Log.d("SeekBat", String.valueOf(progress));
                Log.d("RatingBar", String.valueOf(rbDifficult.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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
                String key = keyIngredient();
                Log.d("keyIngre", key);
                Log.d("textIngre", ingredientText);
                ingredient.put(key, ingredientText);
                Log.d("Ingredient", String.valueOf(ingredient.size()));
                etGredient.setText("");

                View view = LayoutInflater.from(v.getContext()).inflate(R.layout.gredient_cancel, llIngredient, false);
                view.setTag("key");
                TextView tvIngredient = (TextView) view.findViewById(R.id.tvIngredient);
                tvIngredient.setText(characterSpe + ingredientText);
                ImageView ivCancelIngredient = (ImageView) view.findViewById(R.id.ivCancelIngredient);
                ivCancelIngredient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        llIngredient.removeView(view);
                        ingredient.remove(key);
                        Log.d("Ingredient", String.valueOf(ingredient.size()));
                    }
                });

                llIngredient.addView(view);

//                RelativeLayout relativeLayout = (RelativeLayout) view;

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

    public static String keyIngredient() {
        Random random = new Random();
        int paras1 = random.nextInt(100);
        int paras2 = random.nextInt(100);
        String key = paras1 + "-" + paras2;

        return key;
    }
}

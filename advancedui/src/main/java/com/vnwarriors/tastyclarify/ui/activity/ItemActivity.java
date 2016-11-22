package com.vnwarriors.tastyclarify.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.dynamic.LifecycleDelegate;
import com.squareup.picasso.Picasso;
import com.vnwarriors.advancedui.appcore.common.recyclerviewhelper.PlaceHolderDrawableHelper;
import com.vnwarriors.tastyclarify.R;
import com.vnwarriors.tastyclarify.model.PostModel;
import com.vnwarriors.tastyclarify.ui.adapter.BaseAdapter;
import com.vnwarriors.tastyclarify.ui.adapter.viewmodel.BaseVM;
import com.vnwarriors.tastyclarify.ui.delegate.DragDismissDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemActivity extends AppCompatActivity {

    @BindView(R.id.ivCover)
    ImageView ivCover;
    @BindView(R.id.tvIngredients)
    TextView tvIngredients;
    @BindView(R.id.tvPreparation)
    TextView tvPreparation;
    List<BaseVM> sectionList = new ArrayList<>();
    BaseAdapter baseAdapter;

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvPersons)
    TextView tvPersons;
    @BindView(R.id.tvDifficult)
    TextView tvDifficult;
    @BindView(R.id.tvCookTime)
    TextView tvCookTime;
    @BindView(R.id.tvPreparationTime)
    TextView tvPreparationTime;

    List<LifecycleDelegate> lifecycleDelegates = new ArrayList<>();

    { // Initializer block
        lifecycleDelegates.add(new DragDismissDelegate(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_item);
        setContentView(R.layout.activity_item);
        for (LifecycleDelegate lifecycleDelegate : lifecycleDelegates) {
            lifecycleDelegate.onCreate(savedInstanceState);
        }

        ButterKnife.bind(this);

        setupUI();

    }

    private void setupUI() {
        PostModel post = (PostModel) getIntent().getSerializableExtra("POST");

        Picasso.with(this)
                .load(post.getTipImage().getUrl())
                .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable())
                .resize(400, (int) (400 * post.getTipImageRatio()))
                .into(ivCover);
        tvIngredients.setText(post.getTipIngredients().replace("#i","- "));
        tvPreparation.setText(post.getTipDescription().replace("#p","- "));

        tvName.setText(post.getTipName());
        tvPersons.setText(post.getTipPersons()+" People");
        switch (post.getTipDifficulty()){
            case 1 :
                tvDifficult.setText("Easy");
                break;
            case 2 :
                tvDifficult.setText("Medium");
                break;
            case 3 :
                tvDifficult.setText("Hard");
                break;
        }
        String[] strings = post.getTipTime().split("(#tp)|(#tc)");
        int[] ints = {0,0,0,0};
        int i=0;
        for(String str:strings){
            if(str.length()>0){
                int time = Integer.parseInt(str.trim());
                ints[i]=time;
            }
            i++;

        }
        tvPreparationTime.setText(ints[1]+" min.");
        tvCookTime.setText(ints[2]+" min.");
//        createData();
//        setupAdapter();
//        setupRecyclerView();
    }


    @Override
    protected void onResume() {
        super.onResume();
        for (LifecycleDelegate lifecycleDelegate : lifecycleDelegates) {
            lifecycleDelegate.onResume();
        }

    }

    @Override
    protected void onPause() {

        for (LifecycleDelegate lifecycleDelegate : lifecycleDelegates) {
            lifecycleDelegate.onPause();
        }
        super.onPause();
    }
}

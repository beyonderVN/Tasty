package com.vnwarriors.tastyclarify.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.android.gms.dynamic.LifecycleDelegate;
import com.vnwarriors.tastyclarify.R;
import com.vnwarriors.tastyclarify.ui.adapter.BaseAdapter;
import com.vnwarriors.tastyclarify.ui.adapter.viewmodel.BaseVM;
import com.vnwarriors.tastyclarify.ui.adapter.viewmodel.SimpleHorizontalVM;
import com.vnwarriors.tastyclarify.ui.adapter.viewmodel.SimpleVerticalVM;
import com.vnwarriors.tastyclarify.ui.delegate.DragDismissDelegate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemActivity extends AppCompatActivity {

//    @Nullable
//    @BindView(R.id.activity_detail)
//    ElasticDragDismissFrameLayout draggableFrame;

    @BindView(R.id.rvItemList)
    RecyclerView rvItemList;
    @BindView(R.id.tvHeaderSection)
    TextView tvHeaderSection;

//    private ElasticDragDismissFrameLayout.SystemChromeFader chromeFader;

    List<BaseVM> sectionList = new ArrayList<>();
    BaseAdapter baseAdapter;

    List<LifecycleDelegate> lifecycleDelegates = new ArrayList<>();

    { // Initializer block
        lifecycleDelegates.add(new DragDismissDelegate(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        for (LifecycleDelegate lifecycleDelegate : lifecycleDelegates){
            lifecycleDelegate.onCreate(savedInstanceState);
        }

        ButterKnife.bind(this);

        setupUI();
        findViewById(R.id.rvItemList).setFocusable(false);
        findViewById(R.id.tvHeaderSection).requestFocus();
    }

    private void setupUI() {
        SimpleHorizontalVM simpleHorizontalVM = (SimpleHorizontalVM) getIntent().getSerializableExtra("SimpleHorizontalVM");
        tvHeaderSection.setText(simpleHorizontalVM.getTittle());
        tvHeaderSection.setBackgroundColor(getResources().getColor(simpleHorizontalVM.getColor()));

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        createData();
        setupAdapter();
        rvItemList.setHasFixedSize(true);
        rvItemList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        rvItemList.setAdapter(baseAdapter);
    }

    private void setupAdapter() {
        baseAdapter = new BaseAdapter(this, sectionList);
    }

    private void createData() {
        String[] sectionHeaders = {"MARTERIAL HIGHLIGHTS", "ANDROID PATTERNS", "BONUS"};
        List<String[]> list = new ArrayList<>();

        String[] titles1 = {"Toolbar", "Marterial Tabs", "Float Action Button", "Marterial Dailogs", "Toolbar", "Marterial Tabs", "Float Action Button", "Marterial Dailogs"};
        String[] titles2 = {"App Intro", "Login", "Swipeable Layout", "Bubbles", "Toolbar", "Marterial Tabs", "Float Action Button", "Marterial Dailogs"};
        String[] titles3 = {"Sweet Sheet", "Text Surface", "Share View", "Toolbar", "Marterial Tabs", "Float Action Button", "Marterial Dailogs"};
        list.add(titles1);
        list.add(titles2);
        list.add(titles3);

        Random r = new Random();
        int[] ints = {R.color.toan,R.color.ly,
                R.color.hoa,
                R.color.anh,
                R.color.BlueViolet,
                R.color.orange,
                R.color.Chocolate,
                R.color.Aquamarine,
                R.color.DarkGreen,
                R.color.DarkGoldenrod
        };


        for (int j = 0; j < titles1.length; j++) {
            int i1 = (r.nextInt(ints.length-1));
            sectionList.add(new SimpleVerticalVM(titles1[j],ints[i1]));
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        for (LifecycleDelegate lifecycleDelegate : lifecycleDelegates){
            lifecycleDelegate.onResume();
        }
//      draggableFrame.addListener(chromeFader);
    }

    @Override
    protected void onPause() {
//      draggableFrame.removeListener(chromeFader);
        for (LifecycleDelegate lifecycleDelegate : lifecycleDelegates){
            lifecycleDelegate.onPause();
        }
        super.onPause();
    }
}

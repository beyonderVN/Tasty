package com.vnwarriors.tastyclarify.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.squareup.picasso.Picasso;
import com.vnwarriors.advancedui.appcore.common.recyclerviewhelper.PlaceHolderDrawableHelper;
import com.vnwarriors.tastyclarify.R;
import com.vnwarriors.tastyclarify.model.ItemDetailViewModel;
import com.vnwarriors.tastyclarify.model.PostModel;
import com.vnwarriors.tastyclarify.ui.adapter.BaseAdapter;
import com.vnwarriors.tastyclarify.ui.adapter.IngredientAdapter;
import com.vnwarriors.tastyclarify.ui.adapter.PrepareAdapter;
import com.vnwarriors.tastyclarify.ui.adapter.viewmodel.BaseVM;
import com.vnwarriors.tastyclarify.ui.delegate.DragDismissDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemActivity extends AppCompatActivity {

    @BindView(R.id.ivCover)
    ImageView ivCover;
    //    @BindView(R.id.tvIngredients)
//    TextView tvIngredients;
//    @BindView(R.id.tvPreparation)
//    TextView tvPreparation;
    List<BaseVM> sectionList = new ArrayList<>();
    BaseAdapter baseAdapter;

    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.tvPersons)
    TextView tvPersons;
    @BindView(R.id.tvDifficult)
    TextView tvDifficult;
    @BindView(R.id.tvCookTime)
    TextView tvCookTime;
    @BindView(R.id.tvPreparationTime)
    TextView tvPreparationTime;

    @BindView(R.id.rvIngredientList)
    RecyclerView rvIngredientList;
    @BindView(R.id.rvPreparationList)
    RecyclerView rvPreparationList;

    @BindView(R.id.nsScrollView)
    NestedScrollView nsScrollView;

    private PostModel mPost;

    private IngredientAdapter ingredientAdapter;
    private PrepareAdapter prepareAdapter;

    private List<ItemDetailViewModel> ingredientList;
    private List<ItemDetailViewModel> prepareList;

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    LinearLayoutManager mLayoutManagerVerticalIngredient;
    LinearLayoutManager mLayoutManagerVerticalPrepare;

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
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        // this part is optional
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        ButterKnife.bind(this);

        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setupUI();

    }

    private void setupUI() {
        mPost = (PostModel) getIntent().getSerializableExtra("POST");
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        Picasso.with(this)
                .load(mPost.getTipImage().getUrl())
                .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable())
                .resize(width, (int) (width * mPost.getTipImageRatio()))
                .into(ivCover);
//        tvIngredients.setText(post.getTipIngredients().replace("#i","- "));
//        tvPreparation.setText(post.getTipDescription().replace("#p","- "));

        tvName.setText(mPost.getTipName());
        tvPersons.setText(mPost.getTipPersons() + " People");
        switch (mPost.getTipDifficulty()) {
            case 1:
                tvDifficult.setText("Easy");
                break;
            case 2:
                tvDifficult.setText("Medium");
                break;
            case 3:
                tvDifficult.setText("Hard");
                break;
        }
        String[] strings = mPost.getTipTime().split("(#tp)|(#tc)");
        int[] ints = {0, 0, 0, 0};
        int i = 0;
        for (String str : strings) {
            if (str.length() > 0) {
                int time = Integer.parseInt(str.trim());
                ints[i] = time;
            }
            i++;

        }
        tvPreparationTime.setText(ints[1] + " min.");
        tvCookTime.setText(ints[2] + " min.");
        createData();
        setupAdapter();
    }

    private void sharePostToFacebook() {
        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .setImageUrl(Uri.parse(mPost.getTipImage().getUrl()))
                .setContentTitle(mPost.getTipName())
                .build();
//        ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
//                .putString("og:type", "fitness.course")
//                .putString("og:title", "Sample Course")
//                .putString("og:description", "This is a sample course.")
//                .putString("og:image", "This is a sample course.")
//                .putInt("fitness:duration:value", 100)
//                .putString("fitness:duration:units", "s")
//                .putInt("fitness:distance:value", 12)
//                .putString("fitness:distance:units", "km")
//                .putInt("fitness:speed:value", 5)
//                .putString("fitness:speed:units", "m/s")
//                .build();
//        ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
//                .setActionType("fitness.runs")
//                .putObject("fitness:course", object)
//                .build();
//        ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
//                .setPreviewPropertyName("fitness:course")
//                .setAction(action)
//                .build();

        // Create OG object
//        ShareOpenGraphObject object2 = new ShareOpenGraphObject.Builder()
//                .putString("og:type", "books.book")
//                .putString("og:title", "A Game of Thrones")
//                .putString("og:description", "In the frozen wastes to the north of Winterfell, " +
//                        "sinister and supernatural forces are mustering.")
//                .putString("books:isbn", "0-553-57340-3")
//                .build();
//
//        SharePhoto photo = new SharePhoto.Builder()
//                .setImageUrl(Uri.parse(mPost.getTipImage().getUrl()))
//                .setUserGenerated(true)
//                .build();
//
//        // Create an action
//        ShareOpenGraphAction action2 = new ShareOpenGraphAction.Builder()
//                .setActionType("books.reads")
//                .putObject("book", object2)
//                .putPhoto("image", photo)
//                .putPhoto("image", photo)
//                .build();
//
//        // Create the content
//        ShareOpenGraphContent content2 = new ShareOpenGraphContent.Builder()
//                .setPreviewPropertyName("book")
//                .setAction(action2)
//                .build();

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareDialog.show(ItemActivity.this, linkContent);
//            shareDialog.show(ItemActivity, object);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void createData() {
        prepareIngredientList();
        preparePrepareList();
    }

    private void preparePrepareList() {
        prepareList = new ArrayList<>();
        String prepareString = mPost.getTipDescription();
        boolean mode = prepareString.contains("#e");
        String[] tmp1 = prepareString.split("\n");
        if (mode) {

            int number = 0;
            for (int i = 0; i < tmp1.length; i++) {
                String ingre = tmp1[i];
                int type = 0;
                if (ingre.contains("#e")) {
                    number = 0;
                    ingre = clearString(ingre);
                    ingre = "<font color=\"#e1431c\">" + ingre + "</font>";
                    type = 1;
                } else if (ingre.contains("#p")) {
                    number++;
                    ingre = clearString(ingre);
                    ingre = "<font color=\"#e1431c\">" + number + "</font>." + "\t" + ingre;
                    type = 2;
                }
                ItemDetailViewModel itemDetailViewModel = new ItemDetailViewModel(ingre, type);
                prepareList.add(itemDetailViewModel);
            }
        } else {
            for (int i = 0; i < tmp1.length; i++) {
                String ingre = tmp1[i];
                if (ingre.contains("#p")) {
                    ingre = clearString(ingre);
                    ingre = "<font color=\"#e1431c\">" + (i + 1) + "</font>." + "\t" + ingre;
                }
                ItemDetailViewModel itemDetailViewModel = new ItemDetailViewModel(ingre, 2);
                prepareList.add(itemDetailViewModel);
            }
        }
    }

    private void prepareIngredientList() {
        ingredientList = new ArrayList<>();
        String ingredients = mPost.getTipIngredients();
        boolean mode = ingredients.contains("#e");
        String[] tmp1 = ingredients.split("\n");
        if (mode) {
            for (int i = 0; i < tmp1.length; i++) {
                String ingre = tmp1[i];
                int type = 0;
                if (ingre.contains("#e")) {
                    ingre = clearString(ingre);
                    ingre = "<font color=\"#e1431c\">" + ingre + "</font>";
                    type = 1;
                } else if (ingre.contains("#i")) {
                    ingre = clearString(ingre);
                    type = 2;
                }
                ItemDetailViewModel itemDetailViewModel = new ItemDetailViewModel(ingre, type);
                ingredientList.add(itemDetailViewModel);
            }
        } else {
            for (int i = 0; i < tmp1.length; i++) {
                String ingre = tmp1[i];
                if (ingre.contains("#i")) {
                    ingre = clearString(ingre);
                }
                ItemDetailViewModel itemDetailViewModel = new ItemDetailViewModel(ingre, 2);
                ingredientList.add(itemDetailViewModel);
            }
        }
    }

    private String clearString(String string) {
        if (string.contains("#e")) {
            string = string.replace("#e", "");
        }
        if (string.contains("#i")) {
            string = string.replace("#i", "");
        }
        if (string.contains("#p")) {
            string = string.replace("#p", "");
        }
        return string;
    }

    private void setupAdapter() {

        mLayoutManagerVerticalIngredient =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        mLayoutManagerVertical.set

        ingredientAdapter = new IngredientAdapter(ingredientList);
        rvIngredientList.setLayoutManager(mLayoutManagerVerticalIngredient);
        rvIngredientList.setAdapter(ingredientAdapter);


        mLayoutManagerVerticalPrepare = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        prepareAdapter = new PrepareAdapter(prepareList);
        rvPreparationList.setLayoutManager(mLayoutManagerVerticalPrepare);

        rvPreparationList.setAdapter(prepareAdapter);


//      fix bug layout auto scroll up
        rvIngredientList.setFocusable(false);
        rvIngredientList.setFocusable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                sharePostToFacebook();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
//https://developers.facebook.com/docs/sharing/android
//        https://developers.facebook.com/docs/sharing/opengraph/android
//        https://developers.facebook.com/docs/reference/opengraph#object-type
//        http://stackoverflow.com/questions/31040901/attach-picture-to-action-using-facebook-open-graph-stories-v4
//
//        http://stackoverflow.com/questions/21580766/upload-multiple-photos-as-a-single-post-like-timeline-photos-not-like-album-in-f

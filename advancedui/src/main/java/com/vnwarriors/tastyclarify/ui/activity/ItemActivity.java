package com.vnwarriors.tastyclarify.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.vnwarriors.advancedui.appcore.common.recyclerviewhelper.PlaceHolderDrawableHelper;
import com.vnwarriors.tastyclarify.R;
import com.vnwarriors.tastyclarify.model.Comment;
import com.vnwarriors.tastyclarify.model.ItemDetailViewModel;
import com.vnwarriors.tastyclarify.model.PostModel;
import com.vnwarriors.tastyclarify.ui.adapter.BaseAdapter;
import com.vnwarriors.tastyclarify.ui.adapter.CommentAdapter;
import com.vnwarriors.tastyclarify.ui.adapter.IngredientAdapter;
import com.vnwarriors.tastyclarify.ui.adapter.PrepareAdapter;
import com.vnwarriors.tastyclarify.ui.adapter.viewmodel.BaseVM;
import com.vnwarriors.tastyclarify.ui.delegate.DragDismissDelegate;
import com.vnwarriors.tastyclarify.ui.firebase.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.rvCommentList)
    RecyclerView rvCommentList;

    @BindView(R.id.nsScrollView)
    NestedScrollView nsScrollView;
    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;
    @BindView(R.id.rlProgressLoading)
    RelativeLayout rlProgressLoading;

    private PostModel mPost;

    private IngredientAdapter ingredientAdapter;
    private PrepareAdapter prepareAdapter;

    private List<ItemDetailViewModel> ingredientList;
    private List<ItemDetailViewModel> prepareList;
    private List<Comment> commentList;

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
                .resize(400, (int) (400 * mPost.getTipImageRatio()))
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

        NestedScrollView nestedScrollView = (NestedScrollView) findViewById(R.id.nsScrollView);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int distance = oldScrollY - scrollY;
                if (distance > 5) {
                    hideToolbar();
                }
                if (distance < -10) {
                    showToolbar();
                }
            }
        });

    }

    private void showToolbar() {
        if (toolBar.getVisibility() != View.GONE) {
            toolBar.setVisibility(View.GONE);
        }
    }

    private void hideToolbar() {
        if (toolBar.getVisibility() != View.VISIBLE) {
            toolBar.setVisibility(View.VISIBLE);
        }
    }

    private void sharePostToFacebook() {
        if (selectedImageUri == null) return;
        StorageReference storageRef = storage.getReferenceFromUrl(Util.URL_STORAGE_REFERENCE).child(Util.FOLDER_STORAGE_IMG);
        if (storageRef != null) {
            final String name = DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString();
            StorageReference imageGalleryRef = storageRef.child(name + "_gallery");
            UploadTask uploadTask = imageGalleryRef.putFile(selectedImageUri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("imageFacebook", "onFailure sendFileFirebase " + e.getMessage());
                    return;
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.i("imageFacebook", "onSuccess sendFileFirebase");
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    shareFacebook(downloadUrl);
                }
            });
        } else {
            //IS NULL
        }


    }

    private void shareFacebook(Uri downloadUrl) {
        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("http://www.101cookbooks.com/"))
                .setImageUrl(downloadUrl)
                .setContentTitle(mPost.getTipName())
                .setContentDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque a lobortis....")
                .build();

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            finishBackgroundTranfer();
            ShareDialog.show(ItemActivity.this, linkContent);
        }
    }

    private void shareRecipeFacebook() {
        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("http://www.101cookbooks.com/"))
                .setImageUrl(Uri.parse(mPost.getTipImage().getUrl()))
                .setContentTitle(mPost.getTipName())
                .setContentDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque a lobortis....")
                .build();

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareDialog.show(ItemActivity.this, linkContent);
        }
    }


    private void createData() {
        prepareIngredientList();
        preparePrepareList();
        prepareCommentList();
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
    }private void prepareCommentList() {
        commentList = new ArrayList<>();
        if(mPost.getTipComments()!=null&&mPost.getTipComments().size()>0)commentList.addAll(mPost.getTipComments());
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

        rvCommentList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvCommentList.setAdapter(new CommentAdapter(commentList));
//      fix bug layout auto scroll up
        rvIngredientList.setFocusable(false);
        rvIngredientList.setFocusable(false);
        rvCommentList.setFocusable(false);
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
                shareRecipeFacebook();
                break;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.btnShareRecipe)
    public void onDialogChooseImageType() {
        CharSequence colors[] = new CharSequence[]{"Choose photo gallery", "Take a picture"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Photo type");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("dialogNumber", String.valueOf(which));
                switch (which) {
                    case 0:
                        photoGalleryIntent();
                        break;
                    case 1:
                        photoCameraIntent();
                        break;
                    default:
                        break;
                }
            }
        });
        builder.show();
    }

//    @OnClick(R.id.btnShareRecipe)
    public void customDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.option_photo_layout, null);
        dialogBuilder.setView(dialogView);

        TextView editText = (TextView) dialogView.findViewById(R.id.textext);
        editText.setText("test label");
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
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

    private static final int IMAGE_GALLERY_REQUEST = 1;

    private void photoGalleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture_title)), IMAGE_GALLERY_REQUEST);
    }

    //File
    private File filePathImageCamera;
    private static final int IMAGE_CAMERA_REQUEST = 2;

    private void photoCameraIntent() {
        String nomeFoto = DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString();
        filePathImageCamera = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), nomeFoto + "camera.jpg");
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(filePathImageCamera));
        startActivityForResult(it, IMAGE_CAMERA_REQUEST);
    }

    FirebaseStorage storage = FirebaseStorage.getInstance();
    Uri selectedImageUri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        StorageReference storageRef = storage.getReferenceFromUrl(Util.URL_STORAGE_REFERENCE).child(Util.FOLDER_STORAGE_IMG);

        if (requestCode == IMAGE_GALLERY_REQUEST) {
            if (resultCode == RESULT_OK) {
                selectedImageUri = data.getData();
                sharePostToFacebook();
                startBackgroundTranfer();
            }
        } else if (requestCode == IMAGE_CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (filePathImageCamera != null && filePathImageCamera.exists()) {
                    StorageReference imageCameraRef = storageRef.child(filePathImageCamera.getName() + "_camera");
                    sendFileFirebase(imageCameraRef, filePathImageCamera);
                    startBackgroundTranfer();
                } else {
                    //IS NULL
                }
            }
        }
    }

    private void sendFileFirebase(StorageReference storageReference, final File file) {
        if (storageReference != null) {
            UploadTask uploadTask = storageReference.putFile(Uri.fromFile(file));
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("imageFacebook", "onFailure sendFileFirebase " + e.getMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.i("imageFacebook", "onSuccess sendFileFirebase");
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    shareFacebook(downloadUrl);
                }
            });
        } else {
            //IS NULL
        }

    }

    private void startBackgroundTranfer() {
        rlProgressLoading.setClickable(true);
        pbLoading.setVisibility(View.VISIBLE);
    }

    private void finishBackgroundTranfer() {
        rlProgressLoading.setClickable(false);
        pbLoading.setVisibility(View.GONE);
    }

}
//https://developers.facebook.com/docs/sharing/android
//        https://developers.facebook.com/docs/sharing/opengraph/android
//        https://developers.facebook.com/docs/reference/opengraph#object-type
//        http://stackoverflow.com/questions/31040901/attach-picture-to-action-using-facebook-open-graph-stories-v4
//
//        http://stackoverflow.com/questions/21580766/upload-multiple-photos-as-a-single-post-like-timeline-photos-not-like-album-in-f

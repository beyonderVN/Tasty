package com.vnwarriors.tastyclarify.ui.activity.createrecipe;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.vnwarriors.tastyclarify.R;
import com.vnwarriors.tastyclarify.model.Post;
import com.vnwarriors.tastyclarify.model.TipImage;
import com.vnwarriors.tastyclarify.ui.firebase.util.Util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateRecipesActivity extends AppCompatActivity {
    private static final String TAG = "CreateRecipesActivity";
    @BindView(R.id.parent)
    ViewGroup parent;
    @BindView(R.id.fab)
    FloatingActionButton fab;
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
    @BindView(R.id.ivDish)
    ImageView ivDish;

    @OnClick(R.id.ivDish)
    void chooseImage(View v) {
        photoGalleryIntent();
    }

    Map<String, String> ingredient = new HashMap<>();

    //    https://github.com/oli107/material-range-bar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipes);

        ButterKnife.bind(this);

        initView();
        parent.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        enterReveal();
                        fab.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });

    }

    private void enterReveal() {
        int cx = (int) (fab.getX() + fab.getMeasuredWidth() / 2);
        int cy = (int) (fab.getY() + fab.getMeasuredHeight() / 2);
        int finalRadius = Math.max(parent.getWidth(), parent.getHeight());
        ViewAnimationUtils.createCircularReveal(parent, cx, cy, 0, finalRadius).start();
    }

    @Override
    public void onBackPressed() {
        exitReveal();
    }

    public void exitReveal() {
        int cx = (int) (fab.getX() + fab.getMeasuredWidth() / 2);
        int cy = (int) (fab.getY() + fab.getMeasuredHeight() / 2);
        int startRadius = Math.max(parent.getWidth(), parent.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(parent, cx, cy,
                startRadius, fab.getMeasuredWidth() / 2);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                parent.setVisibility(View.INVISIBLE);
                finishAfterTransition();
                overridePendingTransition(0, 0);
            }
        });
        animator.start();
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
//                for (Post postModel : CloneDataUtils.getRateList("recipes.json", CreateRecipesActivity.this)
//                        ) {
//
//                    Log.d(TAG, "onClick: " + postModel.getTipName());
//                    List<Comment> comments = new ArrayList<Comment>();
//                    User userModel = new User();
//                    userModel.setId("gSUNZWLvLmS5vdu7YTcQlXEDX5p1");
//                    userModel.setName("who");
//                    userModel.setPhoto_profile("http://fanexpodallas.com/wp-content/uploads/550w_soaps_silhouettesm2.jpg");
//                    Comment comment = new Comment();
//                    comment.userModel = userModel;
//                    comment.message = "Yummy!";
//                    comment.createAt = "" + Calendar.getInstance().getTime().getTime();
//                    comment.updateAt = "" + Calendar.getInstance().getTime().getTime();
//                    comments.add(comment);
//                    comment.message = "Delicious!";
//                    comments.add(comment);
//                    postModel.setTipComments(comments);
//                    mFirebaseDatabaseReference.child("posts").push().setValue(postModel, new DatabaseReference.CompletionListener() {
//                        @Override
//                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                            Log.d(TAG, "onComplete: " + (databaseError == null ? "" : databaseError.getMessage()));
//                        }
//                    });
//                }

//                List<Post> postModels = CloneDataUtils.getRateListWithComments("recipes.json", CreateRecipesActivity.this);
//                for (Post postModel : postModels
//                        ) {
//
//                    mFirebaseDatabaseReference.child("posts").push().setValue(postModel, new DatabaseReference.CompletionListener() {
//                        @Override
//                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                            Log.d(TAG, "onComplete: " + (databaseError == null ? "" : databaseError.getMessage()));
//                        }
//                    });
//                }
                 onPost();
            }


        });
    }

    private void onPost() {
        if (!validate()) return;
        StorageReference storageRef = storage.getReferenceFromUrl(Util.URL_STORAGE_REFERENCE).child(Util.FOLDER_STORAGE_IMG);
        if (storageRef != null) {
            final String name = DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString();
            StorageReference imageGalleryRef = storageRef.child(name + "_gallery");
            UploadTask uploadTask = imageGalleryRef.putFile(selectedImageUri);
            uploadTask.addOnFailureListener(e -> {
                Log.e(TAG, "onFailure sendFileFirebase " + e.getMessage());
                return;
            }).addOnSuccessListener(taskSnapshot -> {
                Log.i(TAG, "onSuccess sendFileFirebase");
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                TipImage tipImage = new TipImage("File", "image name", downloadUrl.toString());
                Post post = new Post();
                post.setTipName(idRecipeName.getText().toString());
                post.setTipImage(tipImage);
                post.setCreatedAt(Calendar.getInstance().getTime().getTime() + "");
                int[] checkedCatelogues = {R.id.cbAppetizer, R.id.cbDessert, R.id.cbFirstCourse, R.id.cbMainCourse, R.id.cbSideDish,
                        R.id.cbVegetarian, R.id.cbCheap, R.id.cbPizza};
                post.setTipCategories("");
                for (int i = 0; i < checkedCatelogues.length; i++) {
                    if (((AppCompatCheckBox) findViewById(checkedCatelogues[i])).isChecked()) {
                        post.setTipCategories(String.valueOf(i));
                    }
                }
                post.setTipPersons(sbServe.getProgress());
                post.setTipDifficulty(sbDifficult.getProgress());
                EditText etPreparationTime = (EditText) findViewById(R.id.etPreparationTime);
                EditText etCookingTime = (EditText) findViewById(R.id.etCookingTime);
                post.setTipTime("#tp" + etPreparationTime.getText().toString() + "#tc" + etCookingTime.getText().toString() + "");
                String ingredients = "";
                for (String key : ingredient.keySet()
                        ) {
                    ingredients = ingredients + ingredient.get(key) + "\n";
                }
                post.setTipIngredients(ingredients);
                EditText etPreparation = (EditText) findViewById(R.id.etPreparation);
                post.setTipDescription(etPreparation.getText().toString());
                post.setObjectId("");
                post.setTipDairy(false);
                post.setTipHot(false);
                post.setTipImageRatio((double) ivDish.getMeasuredHeight() / (double) ivDish.getMeasuredWidth());
                post.setTipOven(false);
                post.setTipPortion("");
                post.setTipPublished(1);
                post.setTipSeasons("");
                post.setTipZzz("");
                post.setUpdatedAt(Calendar.getInstance().getTime().getTime() + "");
                DatabaseReference.CompletionListener completionListener
                        = (databaseError, databaseReference) -> {
                            if (databaseError != null) {
                                Log.d(TAG, "DatabaseReference.CompletionListener: " + databaseError.getMessage());
                            }
                            Toast.makeText(CreateRecipesActivity.this, "Your recipe has been shared!\nThanks for your contribution", Toast.LENGTH_LONG).show();
                        };
                mFirebaseDatabaseReference.child("posts").push().setValue(post, completionListener);
            });
        } else {
            //IS NULL
        }

    }

    @BindView(R.id.idRecipeName)
    EditText idRecipeName;

    private boolean validate() {
        if (idRecipeName.getText().equals("")) {
            Toast.makeText(this, "Recipe Name is null", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (selectedImageUri == null) {
            Toast.makeText(this, "selected Image is null", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
        cbMan.setOnClickListener(v -> cbWoman.setChecked(false));

        cbWoman.setOnClickListener(v -> cbMan.setChecked(false));
    }

    private String Ingredient = "rlIngredientNo";
    private String characterSpe = "- ";

    private void onAddIngredientClick() {
        ivAddIngredient.setOnClickListener(v -> {
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
            ivCancelIngredient.setOnClickListener(v1 -> {
                llIngredient.removeView(view);
                ingredient.remove(key);
                Log.d("Ingredient", String.valueOf(ingredient.size()));
            });

            llIngredient.addView(view);

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

    private static final int IMAGE_GALLERY_REQUEST = 1;

    private void photoGalleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture_title)), IMAGE_GALLERY_REQUEST);
    }

    FirebaseStorage storage = FirebaseStorage.getInstance();
    Uri selectedImageUri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        StorageReference storageRef = storage.getReferenceFromUrl(Util.URL_STORAGE_REFERENCE).child(Util.FOLDER_STORAGE_IMG);

        if (requestCode == IMAGE_GALLERY_REQUEST) {
            if (resultCode == RESULT_OK) {
                selectedImageUri = data.getData();
                Picasso.with(this).load(selectedImageUri).into(ivDish);
            }
        }
    }
}

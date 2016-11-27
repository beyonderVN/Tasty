package com.vnwarriors.tastyclarify.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.vnwarriors.tastyclarify.R;
import com.vnwarriors.tastyclarify.model.PostModel;
import com.vnwarriors.tastyclarify.model.TipImage;
import com.vnwarriors.tastyclarify.ui.firebase.model.FileModel;
import com.vnwarriors.tastyclarify.ui.firebase.util.Util;

import java.io.File;
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
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "onFailure sendFileFirebase " + e.getMessage());
                    return;
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.i(TAG, "onSuccess sendFileFirebase");
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    FileModel fileModel = new FileModel("img", downloadUrl.toString(), name, "");
                    TipImage tipImage =  new TipImage("File","image name",downloadUrl.toString());
                    PostModel postModel = new PostModel();
                    postModel.setTipName(idRecipeName.getText().toString());
                    postModel.setTipImage(tipImage);
                    postModel.setCreatedAt(Calendar.getInstance().getTime().getTime() + "");
                    int[] checkedCatelogues = {R.id.cbAppetizer,R.id.cbDessert,R.id.cbFirstCourse,R.id.cbMainCourse,R.id.cbSideDish,
                    R.id.cbVegetarian,R.id.cbCheap,R.id.cbPizza};
                    postModel.setTipCategories("");
                    for (int i = 0; i < checkedCatelogues.length; i++) {
                        if(((AppCompatCheckBox) findViewById(checkedCatelogues[i])).isChecked()){
                            postModel.setTipCategories(String.valueOf(i));
                        }
                    }
                    postModel.setTipPersons(sbServe.getProgress());
                    postModel.setTipDifficulty(sbDifficult.getProgress());
                    EditText etPreparationTime = (EditText) findViewById(R.id.etPreparationTime);
                    EditText etCookingTime = (EditText) findViewById(R.id.etCookingTime);
                    postModel.setTipTime("#tp"+etPreparationTime.getText().toString()+"#tc"+etCookingTime.getText().toString()+"");
                    String ingredients ="";
                    for (String key: ingredient.keySet()
                         ) {
                        ingredients = ingredients + ingredient.get(key) + "\n";
                    }
                    postModel.setTipIngredients(ingredients);
                    EditText etPreparation = (EditText) findViewById(R.id.etPreparation);
                    postModel.setTipDescription(etPreparation.getText().toString());
                    postModel.setObjectId("");
                    postModel.setTipDairy(false);
                    postModel.setTipHot(false);
                    postModel.setTipImageRatio((double)ivDish.getMeasuredHeight()/(double)ivDish.getMeasuredWidth());
                    postModel.setTipOven(false);
                    postModel.setTipPortion("");
                    postModel.setTipPublished(1);
                    postModel.setTipSeasons("");
                    postModel.setTipZzz("");
                    postModel.setUpdatedAt(Calendar.getInstance().getTime().getTime() + "");
                    com.google.firebase.database.DatabaseReference.CompletionListener completionListener
                            = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                Log.d(TAG, "DatabaseReference.CompletionListener: "+databaseError.getMessage());
                            }

                        }
                    };
                    mFirebaseDatabaseReference.child("posts").push().setValue(postModel,completionListener);
                }
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
        }
        if (selectedImageUri!=null) {
            Toast.makeText(this, "selected Image is null", Toast.LENGTH_SHORT).show();
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
//                if (selectedImageUri != null) {
//                    sendFileFirebase(storageRef, selectedImageUri);
//                } else {
//                    //URI IS NULL
//                }
            }
        }
    }

    private void sendFileFirebase(StorageReference storageReference, final Uri file) {
        if (storageReference != null) {
            final String name = DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString();
            StorageReference imageGalleryRef = storageReference.child(name + "_gallery");
            UploadTask uploadTask = imageGalleryRef.putFile(file);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "onFailure sendFileFirebase " + e.getMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.i(TAG, "onSuccess sendFileFirebase");
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    FileModel fileModel = new FileModel("img", downloadUrl.toString(), name, "");
//                    ChatModel chatModel = new ChatModel(userModel, "", Calendar.getInstance().getTime().getTime() + "", fileModel);
//                    mFirebaseDatabaseReference.child(CHAT_REFERENCE).push().setValue(chatModel);
                }
            });
        } else {
            //IS NULL
        }
    }

    private void sendFileFirebase(StorageReference storageReference, final File file) {
        if (storageReference != null) {
            UploadTask uploadTask = storageReference.putFile(Uri.fromFile(file));
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "onFailure sendFileFirebase " + e.getMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.i(TAG, "onSuccess sendFileFirebase");
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    FileModel fileModel = new FileModel("img", downloadUrl.toString(), file.getName(), file.length() + "");
//                    ChatModel chatModel = new ChatModel(userModel, "", Calendar.getInstance().getTime().getTime() + "", fileModel);
//                    mFirebaseDatabaseReference.child(CHAT_REFERENCE).push().setValue(chatModel);
                }
            });
        } else {
            //IS NULL
        }

    }
}

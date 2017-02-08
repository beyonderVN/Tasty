package com.ngohoanglong.monngon.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ngohoanglong.monngon.R;
import com.ngohoanglong.monngon.model.PostModel;
import com.ngohoanglong.monngon.model.TipImage;
import com.ngohoanglong.monngon.ui.firebase.adapter.ClickListenerChatFirebase;
import com.ngohoanglong.monngon.ui.firebase.adapter.PostListAdapter;
import com.ngohoanglong.monngon.ui.firebase.adapter.PostListFirebaseAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class CreateDataActivity extends AppCompatActivity implements ClickListenerChatFirebase {
    private static final String TAG = "CreateDataActivity";
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private DatabaseReference mFirebaseDatabaseReference;
    static final String POST_REFERENCE = "posts";
    private RecyclerView rvListPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        new CreateDataFromJson().execute();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_data);

//        new CreateData().execute();
        bindViews();
//        verificaUsuarioLogado();
//        PostListAdapter postListAdapter = new PostListAdapter(CloneDataUtils.getRateListWithComments("recipes.json", CreateDataActivity.this));
        PostListAdapter postListAdapter = null;
        try {
//            postListAdapter = new PostListAdapter(new CreateData().execute().get());
            postListAdapter = new PostListAdapter(new CreateDataFromJson().execute().get());
            rvListPost.setLayoutManager(staggeredGridLayoutManagerVertical);
            rvListPost.setAdapter(postListAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



    }

    StaggeredGridLayoutManager staggeredGridLayoutManagerVertical;

    private void bindViews() {
        rvListPost = (RecyclerView) findViewById(R.id.messageRecyclerView);
        staggeredGridLayoutManagerVertical =
                new StaggeredGridLayoutManager(
                        2, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        staggeredGridLayoutManagerVertical.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rvListPost.setLayoutManager(staggeredGridLayoutManagerVertical);
//        rvListPost.setHasFixedSize(true);
//        staggeredGridLayoutManagerVertical.setReverseLayout(true);
        staggeredGridLayoutManagerVertical.scrollToPosition(0);
    }

    private void verificaUsuarioLogado() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {

            lerMessagensFirebase();
        }
    }

    private void lerMessagensFirebase() {
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        final PostListFirebaseAdapter firebaseAdapter = new PostListFirebaseAdapter(mFirebaseDatabaseReference.child(POST_REFERENCE), this);
        firebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = firebaseAdapter.getItemCount();
                int ints[] = new int[2];
                ints = staggeredGridLayoutManagerVertical.findLastCompletelyVisibleItemPositions(ints);
                int lastVisiblePosition = ints[0];
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    rvListPost.scrollToPosition(positionStart);
                }
            }
        });
        rvListPost.setLayoutManager(staggeredGridLayoutManagerVertical);
        rvListPost.setAdapter(firebaseAdapter);
    }

    @Override
    public void clickImageChat(View view, int position, String nameUser, String urlPhotoUser, String urlPhotoClick) {

    }

    @Override
    public void clickImageMapChat(View view, int position, String latitude, String longitude) {

    }
//    https://www.buzzfeed.com/tasty/cheatday
//    https://www.buzzfeed.com/tasty/weeknights
//    https://www.buzzfeed.com/tasty/litebites
//    https://www.buzzfeed.com/tasty/junior
//    https://www.buzzfeed.com/tasty/junior
//    https://www.buzzfeed.com/tasty/junior
    String url = "https://www.buzzfeed.com/tasty/cheatday";

    public List<PostModel> getHTML(String url2) {
        Document doc;
        List<String> hrefs = new ArrayList<>();
        List<PostModel> postModels = new ArrayList<>();
        try {

            doc = Jsoup.connect(url2).userAgent("Mozilla").get();
            Log.d(TAG, "getHTML: " + doc.toString());
            Elements es = doc.select(".card--video");
            Log.d(TAG, "get: " + es.first());
            for (Element e : es) {
                String href = "";
                Element e1 = e.select(".link-gray").first();
                if (e1 != null) {
                    href = e1.attr("href");
                }


                if (href.contains("https://www.buzzfeed.com/") && !hrefs.contains(href)) {

                    hrefs.add(href);
                }
//                String title;
//                String urlImg = "";
//                String href = "";
//                if (e == null) {
//                    continue;
//                }
//                Element element = e.select("a.jump_focus").first();
//                if (element == null) {
//                    continue;
//                }
//                title = element.text();
//                href = element.attr("href");
//                urlImg = e.select("div.img-wrap").select("img").first().attr("src");


            }
            for (String href : hrefs
                    ) {
                Log.d(TAG, "getHref: " + href);
                doc = Jsoup.connect(href).userAgent("Mozilla").get();
                es = doc.select(".buzz_superlist_item");
                if (es.size() == 6) {
                    String name = "";
                    String imageUrl = "";
                    String persons = "";
                    String INGREDIENTS = "";
                    String PREPARATION = "";
                    for (int i = 0; i < es.size(); i++) {
                        Element e = es.get(i);
                        if (i == 0) {
                            name = e.select(".subbuzz_name").first().text().substring(3);
                            Log.d(TAG, "subbuzz_name: " + name);
                            imageUrl = e.select("img").attr("rel:bf_image_src");
                            Log.d(TAG, "img: " + imageUrl);
                        }
                        if (i == 2) {
                            INGREDIENTS = e.select("p").html();
                            Log.d(TAG, "INGREDIENTS: " + INGREDIENTS);
                        }
                        if (i == 3) {
                            PREPARATION = e.select("p").html();
                            Log.d(TAG, "PREPARATION: " + PREPARATION);
                        }


                    }
                    PostModel postModel = new PostModel();
                    postModel.setTipName(name);
                    TipImage tipImage = new TipImage("File", "image name", imageUrl);
                    postModel.setTipImage(tipImage);
                    postModel.setCreatedAt(Calendar.getInstance().getTime().getTime() + "");
                    int[] checkedCatelogues = {R.id.cbAppetizer, R.id.cbDessert, R.id.cbFirstCourse, R.id.cbMainCourse, R.id.cbSideDish,
                            R.id.cbVegetarian, R.id.cbCheap, R.id.cbPizza};
                    postModel.setTipCategories("");
                    postModel.setTipCategories(String.valueOf(new Random().nextInt(7)));
                    postModel.setTipPersons(3);
                    postModel.setTipDifficulty(2);

                    postModel.setTipTime("#tp" + "20" + "#tc" + "20" + "");
                    postModel.setTipDescription(PREPARATION);
                    postModel.setTipIngredients(INGREDIENTS);
                    postModel.setObjectId("");
                    postModel.setTipDairy(false);
                    postModel.setTipHot(false);
                    try{
                        postModel.setTipImageRatio(Double.valueOf(imageUrl.substring(imageUrl.length() - 3))  / Double.valueOf(imageUrl.substring(imageUrl.length() - 7,imageUrl.length() - 4)) );
                    }catch (Exception e){
                        postModel.setTipImageRatio(1.0);
                    }

                    postModel.setTipOven(false);
                    postModel.setTipPortion("");
                    postModel.setTipPublished(1);
                    postModel.setTipSeasons("");
                    postModel.setTipZzz("");
                    postModel.setUpdatedAt(Calendar.getInstance().getTime().getTime() + "");
                    postModels.add(postModel);
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
            return postModels;
        }
        return postModels;
    }

    public class CreateData extends AsyncTask<String, String, List<PostModel>> {

        @Override
        protected List<PostModel> doInBackground(String[] params) {

            Log.d(TAG, "CreateData > doInBackground: ");

            return getHTML(url);
        }
    }

    String rootUrl = "https://www.buzzfeed.com/";
    public class CreateDataFromJson extends AsyncTask<String, String, List<PostModel>> {

        @Override
        protected List<PostModel> doInBackground(String... params) {
            List<PostModel> postModels = new ArrayList<>();
            String[] urls = {
                    "https://www.buzzfeed.com/tasty?render_template=0&page=1",
                    "https://www.buzzfeed.com/tasty?render_template=0&page=2",
                    "https://www.buzzfeed.com/tasty?render_template=0&page=3",
                    "https://www.buzzfeed.com/tasty?render_template=0&page=4",
                    "https://www.buzzfeed.com/tasty?render_template=0&page=5"

            };
            for (int j = 0; j < urls.length; j++) {
                String jsonString = getJSON(urls[j]);
                List<String> hrefs = new ArrayList<>();


                Log.d(TAG, "jsonString: "+jsonString);
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray jsonArray = jsonObject.getJSONArray("cards");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jo = jsonArray.getJSONObject(i);
                        if(jo.has("recipeLink")){
                            String href  = jo.getString("recipeLink");
                            if(href!=null&&href!="null"){
                                hrefs.add(href);
                            }

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (String href:hrefs
                        ) {
                    Log.d(TAG, "getHref: " + href);
                    try {
                        Document doc;
                        Elements es;
                        doc = Jsoup.connect(href).userAgent("Mozilla").get();
                        es = doc.select(".buzz_superlist_item");
                        if (es.size() == 6) {
                            String name = "";
                            String imageUrl = "";
                            String persons = "";
                            String INGREDIENTS = "";
                            String PREPARATION = "";
                            for (int i = 0; i < es.size(); i++) {
                                Element e = es.get(i);
                                if (i == 0) {
                                    name = e.select(".subbuzz_name").first().text().substring(3);
                                    Log.d(TAG, "subbuzz_name: " + name);
                                    imageUrl = e.select("img").attr("rel:bf_image_src");
                                    Log.d(TAG, "img: " + imageUrl);
                                }
                                if (i == 2) {
                                    INGREDIENTS = e.select("p").html();
                                    Log.d(TAG, "INGREDIENTS: " + INGREDIENTS);
                                }
                                if (i == 3) {
                                    PREPARATION = e.select("p").html();
                                    Log.d(TAG, "PREPARATION: " + PREPARATION);
                                }


                            }
                            PostModel postModel = new PostModel();
                            postModel.setTipName(name);
                            TipImage tipImage = new TipImage("File", "image name", imageUrl);
                            postModel.setTipImage(tipImage);
                            postModel.setCreatedAt(Calendar.getInstance().getTime().getTime() + "");
                            postModel.setTipCategories("");
                            postModel.setTipCategories(String.valueOf(new Random().nextInt(7)));
                            postModel.setTipPersons(3);
                            postModel.setTipDifficulty(2);

                            postModel.setTipTime("#tp" + "20" + "#tc" + "20" + "");
                            postModel.setTipDescription(PREPARATION);
                            postModel.setTipIngredients(INGREDIENTS);
                            postModel.setObjectId("");
                            postModel.setTipDairy(false);
                            postModel.setTipHot(false);
                            try{
                                postModel.setTipImageRatio(Double.valueOf(imageUrl.substring(imageUrl.length() - 3))  / Double.valueOf(imageUrl.substring(imageUrl.length() - 7,imageUrl.length() - 4)) );
                            }catch (Exception e){
                                postModel.setTipImageRatio(1.0);
                            }

                            postModel.setTipOven(false);
                            postModel.setTipPortion("");
                            postModel.setTipPublished(1);
                            postModel.setTipSeasons("");
                            postModel.setTipZzz("");
                            postModel.setUpdatedAt(Calendar.getInstance().getTime().getTime() + "");
                            postModels.add(postModel);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            return postModels;
        }
    }
    public String getJSON(String url) {
        HttpURLConnection c = null;
        try {
            URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.connect();
            int status = c.getResponseCode();
            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    return sb.toString();
            }

        } catch (Exception ex) {
            return ex.toString();
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    //disconnect error
                }
            }
        }
        return null;
    }


}

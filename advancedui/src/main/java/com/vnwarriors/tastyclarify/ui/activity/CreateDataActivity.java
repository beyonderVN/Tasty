package com.vnwarriors.tastyclarify.ui.activity;

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
import com.vnwarriors.tastyclarify.R;
import com.vnwarriors.tastyclarify.ui.firebase.adapter.ClickListenerChatFirebase;
import com.vnwarriors.tastyclarify.ui.firebase.adapter.PostListAdapter;
import com.vnwarriors.tastyclarify.ui.firebase.adapter.PostListFirebaseAdapter;
import com.vnwarriors.tastyclarify.utils.CloneDataUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateDataActivity extends AppCompatActivity implements ClickListenerChatFirebase {
    private static final String TAG = "CreateDataActivity";
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private DatabaseReference mFirebaseDatabaseReference;
    static final String POST_REFERENCE = "posts";
    private RecyclerView rvListPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        new CreateData().execute();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_data);

//        new CreateData().execute();
        bindViews();
//        verificaUsuarioLogado();
        PostListAdapter postListAdapter = new PostListAdapter(CloneDataUtils.getRateListWithComments("recipes.json", CreateDataActivity.this));
        rvListPost.setLayoutManager(staggeredGridLayoutManagerVertical);
        rvListPost.setAdapter(postListAdapter);


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

    String url = "https://www.buzzfeed.com/tasty";

    public String getHTML() {
        Document doc;
        List<String> hrefs = new ArrayList<>();
        try {

            doc = Jsoup.connect(url).userAgent("Mozilla").get();
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
                            name = e.select(".subbuzz_name").first().text();
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
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }

    public class CreateData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String[] params) {

            Log.d(TAG, "CreateData > doInBackground: ");
            getHTML();
            return null;
        }
    }

}

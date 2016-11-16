package com.vnwarriors.tastyclarify.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vnwarriors.tastyclarify.R;
import com.vnwarriors.tastyclarify.ui.firebase.adapter.ClickListenerChatFirebase;
import com.vnwarriors.tastyclarify.ui.firebase.adapter.PostListFirebaseAdapter;

public class CreateDataActivity extends AppCompatActivity implements ClickListenerChatFirebase {
    private static final String TAG = "CreateDataActivity";
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private DatabaseReference mFirebaseDatabaseReference;
    static final String POST_REFERENCE = "posts";
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView rvListMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_data);

//        new CreateData().execute();
        bindViews();
        verificaUsuarioLogado();
    }
    private void bindViews(){
        rvListMessage = (RecyclerView)findViewById(R.id.messageRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
    }
    private void verificaUsuarioLogado(){
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }else{

            lerMessagensFirebase();
        }
    }
    private void lerMessagensFirebase(){
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        final PostListFirebaseAdapter firebaseAdapter = new PostListFirebaseAdapter(mFirebaseDatabaseReference.child(POST_REFERENCE),this);
        firebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = firebaseAdapter.getItemCount();
                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    rvListMessage.scrollToPosition(positionStart);
                }
            }
        });
        rvListMessage.setLayoutManager(mLinearLayoutManager);
        rvListMessage.setAdapter(firebaseAdapter);
    }

    @Override
    public void clickImageChat(View view, int position, String nameUser, String urlPhotoUser, String urlPhotoClick) {

    }

    @Override
    public void clickImageMapChat(View view, int position, String latitude, String longitude) {

    }
//    String url = "http://www.saporitoricette.com/";
//    public String getHTML(){
//        Document doc;
//
//        try {
//
//            doc = Jsoup.connect(url).userAgent("Mozilla").get();
//
//            Elements es = doc.select("recipe-card-image");
//            Log.d(TAG, "get: "+es.first());
//            for (Element e : es) {
//
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
//
//
//
//            }
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "";
//        }
//        return "";
//    }
//
//    public class CreateData extends AsyncTask<String,String,String>{
//
//        @Override
//        protected String doInBackground(String[] params) {
//
//                Log.d(TAG, "CreateData > doInBackground: ");
//                getHTML();
//            return null;
//        }
//    }
}

package com.vnwarriors.tastyclarify.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
    private RecyclerView rvListPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_data);

//        new CreateData().execute();
        bindViews();
        verificaUsuarioLogado();
    }
    StaggeredGridLayoutManager staggeredGridLayoutManagerVertical;
    private void bindViews(){
        rvListPost = (RecyclerView)findViewById(R.id.messageRecyclerView);
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
}

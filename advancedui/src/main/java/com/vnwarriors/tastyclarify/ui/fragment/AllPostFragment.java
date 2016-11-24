package com.vnwarriors.tastyclarify.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vnwarriors.tastyclarify.R;
import com.vnwarriors.tastyclarify.ui.firebase.adapter.ClickListenerChatFirebase;
import com.vnwarriors.tastyclarify.ui.firebase.adapter.PostListFirebaseAdapter;
import com.vnwarriors.tastyclarify.utils.ColorUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllPostFragment extends Fragment implements ClickListenerChatFirebase {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DatabaseReference mFirebaseDatabaseReference;
    static final String POST_REFERENCE = "posts";
    private RecyclerView rvListPost;
    public AllPostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllPostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllPostFragment newInstance(String param1, String param2) {
        AllPostFragment fragment = new AllPostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_post, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);
        verificaUsuarioLogado();
        bindViews2(view);



    }

    StaggeredGridLayoutManager staggeredGridLayoutManagerVertical;
    private void bindViews(View v){
        rvListPost = (RecyclerView)v.findViewById(R.id.rvPostList);
        staggeredGridLayoutManagerVertical =
                new StaggeredGridLayoutManager(
                        2, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        staggeredGridLayoutManagerVertical.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rvListPost.setLayoutManager(staggeredGridLayoutManagerVertical);
    }
    private void bindViews2(View v){
        RecyclerView rvListCatalogue;
        rvListCatalogue = (RecyclerView)v.findViewById(R.id.rvCatalogueList);
        LinearLayoutManager linearLayoutManager;
        linearLayoutManager =
                new LinearLayoutManager(v.getContext(),LinearLayoutManager.HORIZONTAL,false);
        rvListCatalogue.setLayoutManager(linearLayoutManager);

        String[] mDataset= v.getContext().getResources().getStringArray(R.array.catalogues);
        rvListCatalogue.setAdapter(new CatalogueAdapter(mDataset));
    }
    private void verificaUsuarioLogado(){
            lerMessagensFirebase();
    }
    private void lerMessagensFirebase(){
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        final PostListFirebaseAdapter firebaseAdapter = new PostListFirebaseAdapter(mFirebaseDatabaseReference.child(POST_REFERENCE),this);
        firebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
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

    public static class CatalogueAdapter extends RecyclerView.Adapter<CatalogueAdapter.ViewHolder>{
        private String[] mDataset;
        public CatalogueAdapter() {
        }
        public CatalogueAdapter(String[] myDataset) {
            mDataset = myDataset;
        }
        @Override
        public CatalogueAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_catalogue_item_horizontal, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(CatalogueAdapter.ViewHolder holder, int position) {
            holder.cvWrap.setCardBackgroundColor(ColorUtils.getColorByCatalogue(position));
            holder.tvCatalogue.setText(mDataset[position]);
        }

        @Override
        public int getItemCount() {
            return mDataset.length;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{
            @BindView(R.id.cvWrap)
            CardView cvWrap;
            @BindView(R.id.tvCatalogue)
            TextView tvCatalogue;
            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }


    }
}

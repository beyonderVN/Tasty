package com.vnwarriors.tastyclarify.ui.activity.browser.fragment;


import android.app.Fragment;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vnwarriors.tastyclarify.R;
import com.vnwarriors.tastyclarify.data.DatabaseResult;
import com.vnwarriors.tastyclarify.data.recipe.database.FirebaseRecipeDatabase;
import com.vnwarriors.tastyclarify.data.recipe.repo.RecipeRepo;
import com.vnwarriors.tastyclarify.model.Post;
import com.vnwarriors.tastyclarify.utils.ColorUtils;
import com.vnwarriors.tastyclarify.utils.rx.FirebaseObservableListeners;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllPostFragment extends Fragment  {
    private static final String TAG = "AllPostFragment";
    private DatabaseReference mFirebaseDatabaseReference;
    static final String POST_REFERENCE = "posts";
    private RecyclerView rvListPost;

    RecipeRepo recipeRepo = new RecipeRepo(new FirebaseRecipeDatabase(FirebaseDatabase.getInstance(),new FirebaseObservableListeners()));
    private CompositeSubscription subscriptions = new CompositeSubscription();
    public AllPostFragment() {

    }


    // TODO: Rename and change types and number of parameters
    public static AllPostFragment newInstance(String param1, String param2) {
        AllPostFragment fragment = new AllPostFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

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
        rvListPost.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 20) {
                    hideCatalogue();
                }
                if (dy < -20) {
                    showCatalogue();
                }
            }
        });
        subscriptions.add(recipeRepo.getRecipes("0").subscribe(new Action1<DatabaseResult<List<Post>>>() {
            @Override
            public void call(DatabaseResult<List<Post>> listDatabaseResult) {
                Log.d(TAG, "getRecipes: "+listDatabaseResult.getData().size());
                postListAdapter.setList(listDatabaseResult.getData());
                postListAdapter.notifyDataSetChanged();
            }
        }));
    }

    private void hideCatalogue() {
        if (rvListCatalogue.getVisibility() != View.GONE) {
            rvListCatalogue.setVisibility(View.GONE);
        }
    }

    private void showCatalogue() {
        if (rvListCatalogue.getVisibility() != View.VISIBLE) {
            rvListCatalogue.setVisibility(View.VISIBLE);
        }
    }

    StaggeredGridLayoutManager staggeredGridLayoutManagerVertical;

    private void bindViews(View v) {
        rvListPost = (RecyclerView) v.findViewById(R.id.rvPostList);
        staggeredGridLayoutManagerVertical =
                new StaggeredGridLayoutManager(
                        2, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        staggeredGridLayoutManagerVertical.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rvListPost.setLayoutManager(staggeredGridLayoutManagerVertical);
    }

    RecyclerView rvListCatalogue;
    CatalogueAdapter catalogueAdapter;

    private void bindViews2(View v) {

        rvListCatalogue = (RecyclerView) v.findViewById(R.id.rvCatalogueList);
        LinearLayoutManager linearLayoutManager;
        linearLayoutManager =
                new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvListCatalogue.setLayoutManager(linearLayoutManager);

        String[] mDataset = v.getContext().getResources().getStringArray(R.array.catalogues);
        catalogueAdapter = new CatalogueAdapter(rvListCatalogue,mDataset);
        rvListCatalogue.setAdapter(catalogueAdapter);
    }

    private void verificaUsuarioLogado() {
        lerMessagensFirebase();
    }

    private void lerMessagensFirebase() {
        rvListPost.setLayoutManager(staggeredGridLayoutManagerVertical);
        postListAdapter = new PostListAdapter();
        rvListPost.setAdapter(postListAdapter);
    }

    PostListAdapter postListAdapter;



    public static class CatalogueAdapterItemClickEvent {
        public final int  position;
        public CatalogueAdapterItemClickEvent(int position){
            this.position = position;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe
    public void onCatalogueAdapterItemClickEvent(AllPostFragment.CatalogueAdapterItemClickEvent event) {
        int position = event.position;
        Log.d(TAG, "onCatalogueAdapterItemClickEvent: position: "+position);
//        if (position == -1) {
//            Query query = mFirebaseDatabaseReference.child(POST_REFERENCE);
//            postListAdapter.setQuery(query);
//
//        } else {
//            Query query = mFirebaseDatabaseReference.child(POST_REFERENCE).orderByChild("tipCategories").equalTo(String.valueOf(position));
//            postListAdapter.setQuery(query);
//        }
        catalogueAdapter.setSelectedPosition(event.position+1);
    }
    public static class CatalogueAdapter extends RecyclerView.Adapter<CatalogueAdapter.ViewHolder> {
        private String[] mDataset;
        int lastCheckedPosition;
        RecyclerView recyclerView;
        int[] ics = {R.drawable.ic_recipe,
                R.drawable.ic_appetizer,
                R.drawable.ic_dessert,
                R.drawable.ic_first_course,
                R.drawable.ic_main,
                R.drawable.ic_side_dish,
                R.drawable.ic_salad,
                R.drawable.ic_chip,
                R.drawable.ic_pizza,
        };
        public CatalogueAdapter() {
        }

        public CatalogueAdapter(RecyclerView recyclerView ,String[] myDataset) {
            mDataset = myDataset;
            lastCheckedPosition= 0;
            this.recyclerView = recyclerView;
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
            int color = ColorUtils.getColorByCatalogue(position);
            if(lastCheckedPosition==position){
                holder.cvWrap.setCardElevation(10);
                holder.cvWrap.setCardBackgroundColor(color);
                holder.tvCatalogue.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.white));
                Drawable img = holder.itemView.getContext().getResources().getDrawable(ics[position]);
                img.setColorFilter(holder.itemView.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                holder.tvCatalogue.setCompoundDrawablesWithIntrinsicBounds( img, null, null, null );
            }else {
                holder.cvWrap.setCardElevation(0);
                holder.cvWrap.setCardBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.white));
                holder.tvCatalogue.setTextColor(color);
                Drawable img = holder.itemView.getContext().getResources().getDrawable(ics[position]);
                img.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                holder.tvCatalogue.setCompoundDrawablesWithIntrinsicBounds( img, null, null, null );
            }
            holder.tvCatalogue.setText("  "+mDataset[position]);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelectedPosition(position);
                    EventBus.getDefault().post(new CatalogueAdapterItemClickEvent(position-1));
                }
            });


        }

        public void setSelectedPosition(int position) {
            lastCheckedPosition=position;
            CatalogueAdapter.this.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(position);
        }

        @Override
        public int getItemCount() {
            return mDataset.length;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
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
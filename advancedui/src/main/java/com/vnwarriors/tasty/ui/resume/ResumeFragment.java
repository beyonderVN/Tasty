package com.vnwarriors.tasty.ui.resume;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vnwarriors.tasty.MainApplication;
import com.vnwarriors.tasty.R;
import com.vnwarriors.tasty.ui.resume.adapter.RepoAdapter;import com.vnwarriors.tasty.ui.resume.adapter.viewmodel.RepoVM;
import com.vnwarriors.tasty.ui.resume.adapter.viewmodel.ResumeVisitable;
import com.vnwarriors.tasty.ui.resume.github.model.GitRepo;
import com.vnwarriors.tasty.ui.resume.github.model.UserGit;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;

import static android.content.Context.MODE_PRIVATE;

public class ResumeFragment extends Fragment {
    private static final String TAG = "ResumeFragment";
    private static UserGit userGit;
    private static List<ResumeVisitable> gitRepos= new ArrayList<>();
    private RepoAdapter repoAdapter;
    @BindView(R.id.ivBanner)
    ImageView ivBanner;
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvDes)
    TextView tvDes;
    @BindView(R.id.rvRepoList)
    RecyclerView rvRepoList;


    public ResumeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ResumeFragment newInstance(String param1, String param2) {
        ResumeFragment fragment = new ResumeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    Subscription subscriptionUser;
    Subscription subscriptionRepo ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_resume, container, false);
    }
    void setupRV() {
        final StaggeredGridLayoutManager staggeredGridLayoutManagerVertical =
                new StaggeredGridLayoutManager(
                        1, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        staggeredGridLayoutManagerVertical.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        staggeredGridLayoutManagerVertical.invalidateSpanAssignments();

        rvRepoList.setLayoutManager(staggeredGridLayoutManagerVertical);
        rvRepoList.setHasFixedSize(true);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(3000);
        itemAnimator.setRemoveDuration(3000);
        rvRepoList.setItemAnimator(itemAnimator);
        repoAdapter = new RepoAdapter(getContext(), gitRepos);
        rvRepoList.setAdapter(repoAdapter);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setupRV();
        SharedPreferences pre= getActivity().getSharedPreferences ("resume",MODE_PRIVATE);
        tvName.setText(pre.getString("resume_name", ""));
        tvEmail.setText(pre.getString("resume_name", ""));
        tvDes.setText(pre.getString("resume_name", ""));
        if (userGit != null) {
            Picasso.with(getContext()).load(userGit.getAvatarUrl()).into(ivAvatar);
            tvName.setText(userGit.getName());
            tvEmail.setText(userGit.getEmail());
            tvDes.setText(userGit.getHtmlUrl());
        }else {
            subscriptionUser =   MainApplication.getMainComponent().githubService().getUserGit("beyonderVN")
                    .subscribeOn(MainApplication.getMainComponent().schedulerProvider().computation())
                    .observeOn(MainApplication.getMainComponent().schedulerProvider().ui())
                    .subscribe(new Subscriber<UserGit>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "onCompleted: ");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "onError: ");
                        }

                        @Override
                        public void onNext(UserGit userGit1) {
                            Log.d(TAG, "onNext: "+userGit1.getName());
                            userGit = userGit1;
                            Picasso.with(getContext()).load(userGit.getAvatarUrl()).into(ivAvatar);
                            tvName.setText(userGit.getName());
                            tvEmail.setText(userGit.getEmail());
                            tvDes.setText(userGit.getHtmlUrl());
                            SharedPreferences pre= getActivity().getSharedPreferences ("resume",MODE_PRIVATE);
                            SharedPreferences.Editor edit=pre.edit();
                            edit.putString("resume_name", userGit.getName());
                            edit.putString("resume_email", userGit.getEmail());
                            edit.putString("resume_htmlurl", userGit.getHtmlUrl());
                            edit.commit();
                        }
                    });

        }

        if (gitRepos.size()>0) {
            Log.d(TAG, "gitRepos.size(): "+gitRepos.size());

        }else {
            subscriptionRepo =   MainApplication.getMainComponent().githubService().getRepos("beyonderVN")
                    .subscribeOn(MainApplication.getMainComponent().schedulerProvider().computation())
                    .observeOn(MainApplication.getMainComponent().schedulerProvider().ui())
                    .subscribe(new Subscriber<List<GitRepo>>(){
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(List<GitRepo> gitRepos1) {
                            Log.d(TAG, "gitRepos1.size(): "+gitRepos1.size());
                            for (GitRepo gitRepo:gitRepos1
                                 ) {
                                if (!gitRepo.getFork()){
                                    gitRepos.add(new RepoVM(gitRepo));
                                }
                            }
                            repoAdapter.notifyDataSetChanged();
                        }
                    });

        }

    }

    @Override
    public void onDetach() {
        if (subscriptionUser != null) {
            subscriptionUser.unsubscribe();
        }
        if (subscriptionRepo != null) {
            subscriptionRepo.unsubscribe();
        }

        super.onDetach();

    }




}

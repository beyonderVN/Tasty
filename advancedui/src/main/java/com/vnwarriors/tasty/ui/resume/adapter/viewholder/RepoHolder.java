package com.vnwarriors.tasty.ui.resume.adapter.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.vnwarriors.tasty.R;
import com.vnwarriors.tasty.ui.resume.adapter.viewmodel.RepoVM;
import com.vnwarriors.tasty.ui.resume.github.model.GitRepo;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Long on 10/28/2016.
 */
public class RepoHolder extends BaseViewHolder<RepoVM> {
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvHtmlUrl)
    TextView tvHtmlUrl;
    @BindView(R.id.tvDes)
    TextView tvDes;

    public RepoHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
    @Override
    public void bind(RepoVM item) {
        Log.d("RepoHolder: ",item.gitRepo.getName());
        final GitRepo gitRepo = item.gitRepo;
        tvName.setText(gitRepo.getName());
        tvHtmlUrl.setText(gitRepo.getHtmlUrl());
        tvDes.setText(gitRepo.getDescription());
    }

}

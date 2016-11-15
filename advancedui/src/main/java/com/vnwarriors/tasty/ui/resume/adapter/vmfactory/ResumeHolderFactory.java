package com.vnwarriors.tasty.ui.resume.adapter.vmfactory;

import android.view.View;

import com.vnwarriors.tasty.ui.resume.adapter.viewholder.BaseViewHolder;
import com.vnwarriors.tasty.ui.resume.adapter.viewmodel.RepoVM;


/**
 * Created by Long on 10/5/2016.
 */

public interface ResumeHolderFactory {
    BaseViewHolder createHolder(int type, View view);
    int getType(RepoVM repoVM);
}

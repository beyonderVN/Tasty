package com.vnwarriors.tastyclarify.ui.resume.adapter.viewmodel;

import com.vnwarriors.tastyclarify.ui.resume.github.model.GitRepo;
import com.vnwarriors.tastyclarify.ui.resume.adapter.vmfactory.ResumeHolderFactory;

import java.io.Serializable;

/**
 * Created by Long on 11/2/2016.
 */

public class RepoVM implements Serializable,ResumeVisitable {

    public final GitRepo gitRepo;

    public RepoVM(GitRepo gitRepo) {
        this.gitRepo = gitRepo;
    }

    @Override
    public int getVMType(ResumeHolderFactory resumeHolderFactory) {
        return resumeHolderFactory.getType(this);
    }
}

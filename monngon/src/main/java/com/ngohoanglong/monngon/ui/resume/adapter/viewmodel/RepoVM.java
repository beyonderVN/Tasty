package com.ngohoanglong.monngon.ui.resume.adapter.viewmodel;

import com.ngohoanglong.monngon.ui.resume.github.model.GitRepo;
import com.ngohoanglong.monngon.ui.resume.adapter.vmfactory.ResumeHolderFactory;

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

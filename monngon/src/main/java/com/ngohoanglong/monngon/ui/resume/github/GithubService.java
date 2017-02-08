package com.ngohoanglong.monngon.ui.resume.github;

import android.util.Log;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.ngohoanglong.monngon.ui.resume.github.model.GitRepo;
import com.ngohoanglong.monngon.ui.resume.github.model.UserGit;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by Long on 11/1/2016.
 */
@Singleton
public class GithubService {
    private static final String TAG = "GithubService";
    private final GithubApi githubApi;
    @Inject
    public GithubService(GithubApi githubApi) {
        this.githubApi = githubApi;
    }

    @RxLogObservable
    public Observable<UserGit> getUserGit(String user) {
        Log.d(TAG, "getUserGit: ");
        return githubApi.getUser(user);
    }
    @RxLogObservable
    public Observable<List<GitRepo>> getRepos(String user) {
        Log.d(TAG, "getUserGit: ");
        return githubApi.getRepos(user);
    }
}

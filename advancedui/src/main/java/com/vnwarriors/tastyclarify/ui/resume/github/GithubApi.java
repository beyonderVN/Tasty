package com.vnwarriors.tastyclarify.ui.resume.github;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.vnwarriors.tastyclarify.ui.resume.github.model.GitRepo;
import com.vnwarriors.tastyclarify.ui.resume.github.model.UserGit;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Long on 11/1/2016.
 */

public interface GithubApi {
    @RxLogObservable
    @GET("users/{userModel}")
    Observable<UserGit> getUser(@Path("userModel") String user);

    @RxLogObservable
    @GET("users/{userModel}/repos")
    Observable<List<GitRepo>> getRepos(@Path("userModel") String user);
}

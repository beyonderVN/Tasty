package com.ngohoanglong.monngon.ui.resume.github;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.ngohoanglong.monngon.ui.resume.github.model.GitRepo;
import com.ngohoanglong.monngon.ui.resume.github.model.UserGit;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Long on 11/1/2016.
 */

public interface GithubApi {
    @RxLogObservable
    @GET("users/{user}")
    Observable<UserGit> getUser(@Path("user") String user);

    @RxLogObservable
    @GET("users/{user}/repos")
    Observable<List<GitRepo>> getRepos(@Path("user") String user);
}

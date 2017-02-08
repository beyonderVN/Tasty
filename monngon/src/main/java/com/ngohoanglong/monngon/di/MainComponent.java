package com.ngohoanglong.monngon.di;

import android.content.Context;

import com.ngohoanglong.advancedui.appcore.common.schedulers.SchedulerProvider;
import com.ngohoanglong.monngon.ui.resume.github.GithubService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Long on 11/10/2016.
 */

@Singleton
@Component(modules = {MainModule.class})
public interface MainComponent {
    Context context();
    GithubService githubService();
    SchedulerProvider schedulerProvider();
}
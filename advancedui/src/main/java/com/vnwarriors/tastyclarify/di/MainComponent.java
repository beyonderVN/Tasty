package com.vnwarriors.tastyclarify.di;

import android.content.Context;

import com.vnwarriors.advancedui.appcore.common.schedulers.SchedulerProvider;
import com.vnwarriors.tastyclarify.ui.resume.github.GithubService;

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
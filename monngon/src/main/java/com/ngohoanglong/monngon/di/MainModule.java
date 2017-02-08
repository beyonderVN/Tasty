package com.ngohoanglong.monngon.di;

import android.content.Context;

import com.ngohoanglong.advancedui.appcore.common.schedulers.BaseSchedulerProvider;
import com.ngohoanglong.advancedui.appcore.common.schedulers.SchedulerProvider;
import com.ngohoanglong.monngon.MainApplication;
import com.ngohoanglong.monngon.ui.resume.github.GithubApi;
import com.ngohoanglong.monngon.ui.resume.github.GithubServiceFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Long on 11/10/2016.
 */

@Module
public class MainModule {
    private Context context;
    private final MainApplication application;

    public MainModule(MainApplication application) {
        this.application = application;
    }

    @Provides @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    BaseSchedulerProvider provideSchedulerProvider(SchedulerProvider schedulerProvider) {
        return schedulerProvider;
    }
    //  provide GithubApi
    @Provides
    @Singleton
    GithubApi provideGithubApi() {
        return GithubServiceFactory.makeService();
    }


}

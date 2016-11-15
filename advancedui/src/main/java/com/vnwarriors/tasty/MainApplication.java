package com.vnwarriors.tasty;

import android.app.Application;
import android.content.Context;

import com.vnwarriors.tasty.di.DaggerMainComponent;
import com.vnwarriors.tasty.di.MainComponent;

import io.realm.Realm;


/**
 * Created by Long on 10/5/2016.
 */

public class MainApplication extends Application {



    public static Context mContext;
    static MainComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        mContext = getApplicationContext();
        setupGraph();
    }

    void setupGraph(){
        applicationComponent = DaggerMainComponent.builder()
                .mainModule(new com.vnwarriors.tasty.di.MainModule(this))
                .build();


    }

    public static com.vnwarriors.tasty.di.MainComponent getMainComponent() {
        return applicationComponent;
    }

}

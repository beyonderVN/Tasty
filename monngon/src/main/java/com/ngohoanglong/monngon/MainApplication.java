package com.ngohoanglong.monngon;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.ngohoanglong.monngon.di.DaggerMainComponent;
import com.ngohoanglong.monngon.di.MainComponent;

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
        // Initialize the SDK before executing any other operations,
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

    }

    void setupGraph(){
        applicationComponent = DaggerMainComponent.builder()
                .mainModule(new com.ngohoanglong.monngon.di.MainModule(this))
                .build();


    }

    public static com.ngohoanglong.monngon.di.MainComponent getMainComponent() {
        return applicationComponent;
    }

}

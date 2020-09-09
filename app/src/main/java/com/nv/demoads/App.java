package com.nv.demoads;

import android.annotation.SuppressLint;
import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.Collections;
import java.util.List;

public class App extends Application {

    @Override
    public void onCreate() {

        super.onCreate();

        MobileAds.initialize(this);

        List<String> testDeviceIds = Collections.singletonList("C6F024B17D3F16A840DC572C157DF77C");

        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();

        MobileAds.setRequestConfiguration(configuration);

        InterstitialUtils.initialize(this);
        InterUtils.initialize(this);

    }
}

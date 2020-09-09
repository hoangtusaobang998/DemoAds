package com.nv.demoads;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;



public class InterstitialUtils implements UnitID {

    @SuppressLint("StaticFieldLeak")
    private static InterstitialUtils instance;

    private InterstitialAd mInterstitialAd;

    private AdReceiver adReceiver;

    private Context context;

    private boolean loadAds = true;

    public static void initialize(Context context) {
        if (instance == null) {
            instance = new InterstitialUtils(context);
        }
    }

    public static InterstitialUtils get() {
        return instance;
    }

    public InterstitialUtils(Context context) {
        this.context = context;
        this.mInterstitialAd = new InterstitialAd(context);
        this.mInterstitialAd.setAdUnitId(IT_AD_KEY);
        AdListener adListener = new AdListener() {

            @Override
            public void onAdLoaded() {
                loadAds = true;
            }

            @Override
            public void onAdOpened() {
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                loadAds = false;
            }

            @Override
            public void onAdClosed() {
                if (adReceiver != null) {
                    adReceiver.onAdReceiver();
                }
            }

            @Override
            public void onAdClicked() {
            }
        };
        this.mInterstitialAd.setAdListener(adListener);
    }

    public void loadAds() {
        if (this.mInterstitialAd == null) {
            return;
        }
        this.loadAds = true;
        this.mInterstitialAd.loadAd(AdUtil.getAdRequestBuilderWithTestDevice(this.context).build());
    }

    public void reload() {
        if (this.mInterstitialAd == null || this.mInterstitialAd.isLoaded()) {
            return;
        }
        this.loadAds();
    }

    private void show(AdReceiver adReceiver) {
        this.adReceiver = adReceiver;
        if (this.adReceiver == null) {
            return;
        }
        if (!this.loadAds || this.mInterstitialAd == null || !this.mInterstitialAd.isLoaded()) {
            this.adReceiver.onAdReceiver();
            return;
        }
        this.mInterstitialAd.show();
    }

    public interface AdReceiver {
        void onAdReceiver();
    }
}

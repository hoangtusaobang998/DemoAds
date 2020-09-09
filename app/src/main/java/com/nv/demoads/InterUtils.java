package com.nv.demoads;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;


public class InterUtils implements UnitID {

    @SuppressLint("StaticFieldLeak")
    private static InterUtils instance;

    private InterstitialAd mInterstitialAd;

    private AdReceiver adReceiver;

    private Context context;

    public static void initialize(Context context) {
        if (instance == null) {
            instance = new InterUtils(context);
        }
    }

    public static InterUtils get() {
        return instance;
    }

    public InterUtils(Context context) {
        this.context = context;
        this.mInterstitialAd = new InterstitialAd(context);
        this.mInterstitialAd.setAdUnitId(IT_RANK_AD_KEY);
        AdListener adListener = new AdListener() {

            @Override
            public void onAdLoaded() {
                show();
            }

            @Override
            public void onAdOpened() {
                if (adReceiver != null) {
                    adReceiver.onOpen();
                }
            }

            @Override
            public void onAdFailedToLoad(int i) {
                if (adReceiver != null) {
                    adReceiver.onError();
                }
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

    public void loadAds(AdReceiver adReceiver) {
        if (this.mInterstitialAd == null) {
            return;
        }
        this.adReceiver = adReceiver;
        this.mInterstitialAd.loadAd(AdUtil.getAdRequestBuilderWithTestDevice(this.context).build());
    }

    private void show() {
        if (this.mInterstitialAd == null || !this.mInterstitialAd.isLoaded()) {
            return;
        }
        this.mInterstitialAd.show();
    }

    public interface AdReceiver {

        void onAdReceiver();

        void onOpen();

        void onError();

    }
}

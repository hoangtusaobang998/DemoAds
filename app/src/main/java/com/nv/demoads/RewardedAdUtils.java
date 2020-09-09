package com.nv.demoads;

import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;


public class RewardedAdUtils implements UnitID {

    private RewardedAd rewardedAd;
    private boolean adsLoad = false;
    private Activity activity;
    private AdsRewarReceiver adsRewarReceiver;
    @SuppressLint("StaticFieldLeak")
    private static RewardedAdUtils instance;

    public static void initialize(Activity activity) {
        if (instance == null) {
            instance = new RewardedAdUtils(activity);
        }
    }

    public static RewardedAdUtils get() {
        return instance;
    }

    private RewardedAdLoadCallback loadCallback = new RewardedAdLoadCallback() {

        @Override
        public void onRewardedAdLoaded() {
            adsLoad = true;
            show();
        }

        @Override
        public void onRewardedAdFailedToLoad(LoadAdError i) {
            adsLoad = false;
            if (adsRewarReceiver != null) {
                adsRewarReceiver.onError();
            }
        }
    };

    private RewardedAdCallback rewardedAdCallback = new RewardedAdCallback() {
        @Override
        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
            //->Phần thưởng người dùng kiếm được
            adsLoad = false;
            if (adsRewarReceiver != null) {
                adsRewarReceiver.onUserEarnedReward(rewardItem);
            }
        }

        @Override
        public void onRewardedAdOpened() {
            adsLoad = false;
            if (adsRewarReceiver != null) {
                adsRewarReceiver.onOpen();
            }
        }

        @Override
        public void onRewardedAdClosed() {
            adsLoad = false;
            if (adsRewarReceiver != null) {
                adsRewarReceiver.onRewardedAdClosed();
            }
        }

        @Override
        public void onRewardedAdFailedToShow(int i) {
            adsLoad = false;
            if (adsRewarReceiver != null) {
                adsRewarReceiver.onError();
            }
        }
    };

    public RewardedAdUtils(Activity activity) {
        this.initRewardedAd(activity);
    }

    private void initRewardedAd(Activity activity) {
        this.activity = activity;
    }

    public void loadAds(AdsRewarReceiver adsRewarReceiver) {
        this.rewardedAd = new RewardedAd(activity, RE_AD_KEY);
        this.adsRewarReceiver = adsRewarReceiver;
        this.rewardedAd.loadAd(AdUtil.getAdRequestBuilderWithTestDevice(this.activity).build(), this.loadCallback);
    }

    private void show() {
        if (!this.adsLoad || !this.rewardedAd.isLoaded()) {
            return;
        }
        this.rewardedAd.show(activity, this.rewardedAdCallback);
    }

    public interface AdsRewarReceiver {

        void onUserEarnedReward(RewardItem rewardItem);

        void onRewardedAdClosed();

        void onOpen();

        void onError();

    }

}

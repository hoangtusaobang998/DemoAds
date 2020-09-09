package com.nv.demoads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        RewardedAdUtils.initialize(this);

    }

    public void onRewar(View view) {
        progressBar.setVisibility(View.VISIBLE);
        RewardedAdUtils.get().loadAds(new RewardedAdUtils.AdsRewarReceiver() {
            @Override
            public void onUserEarnedReward(RewardItem rewardItem) {

            }

            @Override
            public void onRewardedAdClosed() {

            }

            @Override
            public void onOpen() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });
    }

    public void onITRank(View view) {
        progressBar.setVisibility(View.VISIBLE);
        InterUtils.get().loadAds(new InterUtils.AdReceiver() {
            @Override
            public void onAdReceiver() {

            }

            @Override
            public void onOpen() {
              progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });
    }

    public void onIT(View view) {
        progressBar.setVisibility(View.VISIBLE);
        InterstitialUtils.get().reload();
        progressBar.setVisibility(View.GONE);
    }
}
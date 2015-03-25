package com.cyanococcus.budget.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class SplashScreenFragment extends Fragment {
    private static final String TAG = SplashScreenFragment.class.getSimpleName();
    private static final int SPLASH_SCREEN_DELAY = 2500;

    private ProgressBar mProgress;

    public SplashScreenFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_splash_screen, container, false);

        mProgress = (ProgressBar) rootView.findViewById(R.id.progress_bar);

        display().start();

        return rootView;
    }

    private CountDownTimer display() {
        return new CountDownTimer(SPLASH_SCREEN_DELAY, SPLASH_SCREEN_DELAY / mProgress.getMax()) {

            @Override
            public void onTick(long millisUntilFinished) {
                mProgress.setProgress(mProgress.getProgress() + 1);
            }

            @Override
            public void onFinish() {
                mProgress.setProgress(mProgress.getMax());

                Activity activity = getActivity();
                Intent intent = new Intent(activity, MainActivity.class);
                startActivity(intent);
                activity.finish();
            }
        };
    }
}
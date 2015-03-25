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

public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new SplashScreenFragment())
                    .commit();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class SplashScreenFragment extends Fragment {
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

            new CountDownTimer(SPLASH_SCREEN_DELAY, SPLASH_SCREEN_DELAY / mProgress.getMax()) {

                /**
                 * Callback fired on regular interval.
                 *
                 * @param millisUntilFinished The amount of time until finished.
                 */
                @Override
                public void onTick(long millisUntilFinished) {
                    mProgress.setProgress(mProgress.getProgress() + 1);
                }

                /**
                 * Callback fired when the time is up.
                 */
                @Override
                public void onFinish() {
                    mProgress.setProgress(mProgress.getMax());

                    Activity activity = getActivity();
                    Intent intent = new Intent(activity, MainActivity.class);
                    startActivity(intent);
                    activity.finish();
                }
            }.start();

            return rootView;
        }
    }
}

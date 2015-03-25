package com.cyanococcus.budget.app;

import android.app.Activity;
import android.os.Bundle;


public class SearchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (savedInstanceState == null) {
            HistoryFragment fragment = new HistoryFragment();

            Bundle bundle = new Bundle();
            bundle.putBoolean("SEARCH", false);
            bundle.putLong("INITIAL_DATE", getIntent().getLongExtra("INITIAL_DATE", 0));
            bundle.putLong("END_DATE", getIntent().getLongExtra("END_DATE", 0));

            fragment.setArguments(bundle);

            getFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }
}

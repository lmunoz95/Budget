package com.cyanococcus.budget.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cyanococcus.budget.app.adapter.NavigationDrawerAdapter;
import com.cyanococcus.budget.app.data.BudgetDbHelper;
import com.cyanococcus.budget.app.data.Expense;
import com.cyanococcus.budget.app.model.NavigationDrawerItem;

import java.util.ArrayList;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // navigation drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] menuTitles;
    private TypedArray menuIcons;

    private ArrayList<NavigationDrawerItem> mItems;
    private NavigationDrawerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        menuTitles = getResources().getStringArray(R.array.drawer_items);

        // navigation drawer icons from resources
        menuIcons = getResources().obtainTypedArray(R.array.drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mItems = new ArrayList<NavigationDrawerItem>();

        // adding navigation drawer items to array
        // Record
        mItems.add(new NavigationDrawerItem(menuTitles[0], menuIcons.getResourceId(0, -1)));
        // History
        mItems.add(new NavigationDrawerItem(menuTitles[1], menuIcons.getResourceId(1, -1)));
        // Search
        mItems.add(new NavigationDrawerItem(menuTitles[2], menuIcons.getResourceId(2, -1)));
        // Exit
        mItems.add(new NavigationDrawerItem(menuTitles[3], menuIcons.getResourceId(3, -1)));

        // Recycle the typed array
        menuIcons.recycle();

        // setting the navigation drawer list adapter
        mAdapter = new NavigationDrawerAdapter(getApplicationContext(), mItems);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(this);

        // enabling action bar app icon and behaving it as toggle bottom
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, // drawer layout
                R.drawable.ic_drawer, //navigation menu toggle icon
                R.string.drawer_open, // navigation drawer open - description for accessibility
                R.string.drawer_close // navigation drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle navigation drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle action bar actions click
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // display view for selected nav drawer item
        displayView(position);
    }

    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new RecordFragment();
                break;
            case 1:
                fragment = new HistoryFragment();
                break;
            case 2:
                fragment = new SearchFragment();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(menuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e(TAG, "Error in creating fragment");
        }
    }
}

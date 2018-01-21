/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package it.polimi.two.firstappever;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

<<<<<<< HEAD
/**
 * A simple launcher activity offering access to the individual samples in this project.
 */
public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
    private Sample[] mSamples;
    private GridView mGridView;

=======
public class MainActivity extends AppCompatActivity {
/*
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
*/

    @Override
>>>>>>> 6c1b84c9daee6b1babbbe732acf7aeaa65fa5677
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
        // Prepare list of samples in this dashboard.
        mSamples = new Sample[]{
                new Sample(R.string.navigationdraweractivity_title, R.string.navigationdraweractivity_description,
                        com.example.android.navigationdrawer.NavigationDrawerActivity.class),
        };

        // Prepare the GridView
        mGridView = (GridView) findViewById(android.R.id.list);
        mGridView.setAdapter(new SampleAdapter());
        mGridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> container, View view, int position, long id) {
        startActivity(mSamples[position].intent);
    }

    private class SampleAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mSamples.length;
=======
/*
        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
*/
/*

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  */
/* host Activity *//*

                mDrawerLayout,         */
/* DrawerLayout object *//*

                R.drawable.ic_drawer,  */
/* nav drawer image to replace 'Up' caret *//*

                R.string.drawer_open,  */
/* "open drawer" description for accessibility *//*

                R.string.drawer_close  */
/* "close drawer" description for accessibility *//*

        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
*/

/*
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
/*    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }*/

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
            case R.id.action_websearch:
                // create intent to perform web search for this planet
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
                // catch event that there's no activity to handle intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
>>>>>>> 6c1b84c9daee6b1babbbe732acf7aeaa65fa5677
        }

<<<<<<< HEAD
=======
    *//* The click listner for ListView in the navigation drawer *//*
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
>>>>>>> 6c1b84c9daee6b1babbbe732acf7aeaa65fa5677
        @Override
        public Object getItem(int position) {
            return mSamples[position];
        }

<<<<<<< HEAD
        @Override
        public long getItemId(int position) {
            return mSamples[position].hashCode();
        }
=======
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    *//**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     *//*
>>>>>>> 6c1b84c9daee6b1babbbe732acf7aeaa65fa5677

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.sample_dashboard_item,
                        container, false);
            }

            ((TextView) convertView.findViewById(android.R.id.text1)).setText(
                    mSamples[position].titleResId);
            ((TextView) convertView.findViewById(android.R.id.text2)).setText(
                    mSamples[position].descriptionResId);
            return convertView;
        }
    }

<<<<<<< HEAD
    private class Sample {
        int titleResId;
        int descriptionResId;
        Intent intent;
=======
    *//**
     * Fragment that appears in the "content_frame", shows a planet
     *//*
    public static class PlanetFragment extends Fragment {
        public static final String ARG_PLANET_NUMBER = "planet_number";
>>>>>>> 6c1b84c9daee6b1babbbe732acf7aeaa65fa5677

        private Sample(int titleResId, int descriptionResId, Intent intent) {
            this.intent = intent;
            this.titleResId = titleResId;
            this.descriptionResId = descriptionResId;
        }

        private Sample(int titleResId, int descriptionResId,
                       Class<? extends Activity> activityClass) {
            this(titleResId, descriptionResId,
                    new Intent(MainActivity.this, activityClass));
        }
<<<<<<< HEAD
    }
}
=======
    }*/
}
>>>>>>> 6c1b84c9daee6b1babbbe732acf7aeaa65fa5677

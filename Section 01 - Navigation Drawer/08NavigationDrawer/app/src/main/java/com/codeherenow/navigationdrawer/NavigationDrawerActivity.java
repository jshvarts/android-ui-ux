/*
 * Copyright (C) 2015 Code Here Now
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codeherenow.navigationdrawer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Ragunath Jawahar <www.codeherenow.com>
 */
public class NavigationDrawerActivity extends ActionBarActivity
        implements AdapterView.OnItemClickListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerListView;
    private TextView mShareDrawerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        // UI References
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerListView = (ListView) findViewById(R.id.drawerListView);
        mShareDrawerTextView = (TextView) findViewById(R.id.shareDrawerTextView);

        // Set the toolbar
        setSupportActionBar(toolbar);

        // Initialize the Drawer Toggle
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_closed);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // Initialize the ListView
        String[] websites = getResources().getStringArray(R.array.websites);
        ArrayAdapter<String> websitesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, websites);
        mDrawerListView.setAdapter(websitesAdapter);

        // Event listeners
        mDrawerListView.setOnItemClickListener(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        boolean actionHandled = false;

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            actionHandled = true;
        } else if (id == R.id.action_share) {
            mDrawerLayout.openDrawer(mShareDrawerTextView);
            actionHandled = true;
        }

        return actionHandled || super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Show a toast
        Adapter adapter = parent.getAdapter();
        String website = adapter.getItem(position).toString();
        Toast.makeText(this, website, Toast.LENGTH_SHORT).show();

        // Dismiss the drawer
        if (mDrawerLayout.isDrawerOpen(mDrawerListView)) {
            mDrawerLayout.closeDrawer(mDrawerListView);
        }
    }

    @Override
    public void onBackPressed() {
        boolean openDrawers = mDrawerLayout.isDrawerOpen(mDrawerListView) ||
                mDrawerLayout.isDrawerOpen(mShareDrawerTextView);

        if (openDrawers) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }
}

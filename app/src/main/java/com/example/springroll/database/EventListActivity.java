package com.example.springroll.database;

import android.app.Fragment;

/**
 * Created by SpringRoll on 4/15/2016.
 */
public class EventListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createdFragment() {
        return new EventListFragment();
    }
}

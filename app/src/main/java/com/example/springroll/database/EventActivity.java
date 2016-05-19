package com.example.springroll.database;

import android.app.Fragment;

/**
 * Created by SpringRoll on 3/30/2016.
 */
public class EventActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createdFragment() {
        long eventId = (long)getIntent().getSerializableExtra(EventFragment.EXTRA_EVENT_ID);
        return EventFragment.newInstance(eventId);
    }
}



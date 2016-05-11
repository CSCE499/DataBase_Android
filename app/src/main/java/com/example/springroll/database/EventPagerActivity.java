package com.example.springroll.database;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import library.calendarAPI.WeekViewEvent;

/**
 * Created by SpringRoll on 4/23/2016.
 */
public class EventPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<WeekViewEvent> mEvents;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);

        mEvents = CalEventManager.get(this).getEventList();
        FragmentManager fm = getFragmentManager();
        /**
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return mEvents.size();
            }

            @Override
            public Fragment getItem(int pos) {
                WeekViewEvent e = mEvents.get(pos);
                return EventFragment.newInstance(e.getId());
            }

        });
         */
    }
}

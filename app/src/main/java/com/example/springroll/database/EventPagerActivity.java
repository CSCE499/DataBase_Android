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
<<<<<<< HEAD

=======
>>>>>>> 5e5def1a0c9e4c35098b30734bb6d0bf1599d52d
        /**
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return mEvents.size();
            }

<<<<<<< HEAD
            /**
            @Override
            public Fragment getItem(int pos) {
                WeekViewEvent e = mEvents.get(pos);
                //return EventFragment.newInstance(e.getId());
                return null;
            }

        });*/

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int pos, float posOffset, int posOffsetPixels) {
            }

            public void onPageSelected(int pos) {
                WeekViewEvent e = mEvents.get(pos);
                if (e.getName() != null) {
                    setTitle(e.getName());
                }
            }
        });

        long crimeId = (long)getIntent()
                .getSerializableExtra(EventFragment.EXTRA_EVENT_ID);
        for (int i = 0; i < mEvents.size(); i++) {
            if (mEvents.get(i).getId() == (crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

=======
            @Override
            public Fragment getItem(int pos) {
                WeekViewEvent e = mEvents.get(pos);
                return EventFragment.newInstance(e.getId());
            }

        });
         */
>>>>>>> 5e5def1a0c9e4c35098b30734bb6d0bf1599d52d
    }
}

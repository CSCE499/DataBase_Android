package com.example.springroll.database;

import android.app.ListFragment;
import android.os.Bundle;

import java.util.List;

import library.CalendarAPI.WeekViewEvent;

/**
 * Created by SpringRoll on 3/31/2016.
 */
public class EventListFragment extends ListFragment {

    private List<WeekViewEvent> mEvent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.title);
        //mEvent = BasicActivity.get(getActivity()).getmEvents();
    }




}

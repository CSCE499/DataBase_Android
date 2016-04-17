package com.example.springroll.database;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import library.CalendarAPI.WeekViewEvent;
import library.UserFunctions;

/**
 * Created by SpringRoll on 4/16/2016.
 */
public class CalEventManager {
    private UserFunctions mFunction;

    private static CalEventManager sEventActivity;
    private Context mAppContext;
    private List<WeekViewEvent> mEvents;


    private CalEventManager(Context context){
        mAppContext = context;
        mEvents = new ArrayList<WeekViewEvent>();
    }

    public static CalEventManager get(Context context){
        if(sEventActivity == null){
            sEventActivity = new CalEventManager(context.getApplicationContext());
        }
        return sEventActivity;
    }

    //////////////////////////////
    //
    //      Getter & Setter
    //
    //////////////////////////////

    public List<WeekViewEvent> getmEvents() {
        return mEvents;
    }

    public void setmEvents(List<WeekViewEvent> mEvents) {
        this.mEvents = mEvents;
    }

    public void addEvent(WeekViewEvent e) {
        mEvents.add(e);
    }

    public WeekViewEvent getEvent(long id){
        for(WeekViewEvent e : mEvents){
            if(e.getId() == id){
                return e;
            }
        } return null;
    }
}

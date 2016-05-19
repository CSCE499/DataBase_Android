package com.example.springroll.database;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import library.UserFunctions;
import library.calendarAPI.WeekViewEvent;

/**
 * Created by SpringRoll on 4/16/2016.
 */
public class CalEventManager {
    /** Class name for log messages. */
    private final static String LOG_TAG = CalEventManager.class.getSimpleName();

    private UserFunctions mFunction;

    private static CalEventManager sEventActivity;
    private Context mAppContext;
    private List<WeekViewEvent> mEvents;

    private CalEventManager(Context context){
        Log.d(LOG_TAG,"CalEventManager...");
        mAppContext = context;
        mEvents = new ArrayList<WeekViewEvent>();

    }

    public static CalEventManager get(Context context){
        //Log.d(LOG_TAG,"GET_CalEventManager...");
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

    public List<WeekViewEvent> getEventList(){
        return mEvents;
    }

    public void setNewListEvent(List<WeekViewEvent> mEvents) {
        this.mEvents = mEvents;
    }

    public void addEvent(WeekViewEvent e) {
        mEvents.add(e);
    }

    public void deleteEvent(WeekViewEvent e){
        mEvents.remove(e);
    }

    public WeekViewEvent getSingleEvent(long id){
        for(WeekViewEvent e : mEvents){
            if(e.getId() == id){
                return e;
            }
        } return null;
    }

}

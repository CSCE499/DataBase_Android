package com.example.springroll.database;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import library.UserFunctions;
import library.calendarAPI.WeekViewEvent;

/**
 * Created by SpringRoll on 4/16/2016.
 */
public class CalEventManager {
    private UserFunctions mFunction;

    private static CalEventManager sEventActivity;
    private Context mAppContext;
    private List<WeekViewEvent> mEvents;
    private int year;
    private int month;

    public void setYearMonth(int year,int month) {
        this.year = year;
        this.month = month;
    }

    protected String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
    }

    private CalEventManager(Context context){
        mAppContext = context;
        mEvents = new ArrayList<WeekViewEvent>();

        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, month - 1);
        startTime.set(Calendar.YEAR, year);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 1);
        endTime.set(Calendar.MONTH, 7);

        WeekViewEvent event = new WeekViewEvent(1, getEventTitle(startTime), startTime, endTime);
        event.setLocation("Same house");

        event.setColor(R.color.event_color_01);
        mEvents.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 30);
        startTime.set(Calendar.MONTH, month-1);
        startTime.set(Calendar.YEAR, year);
        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, 4);
        endTime.set(Calendar.MINUTE, 30);
        endTime.set(Calendar.MONTH, 7);

        event = new WeekViewEvent(10, getEventTitle(startTime), startTime, endTime);
        event.setLocation("Kevin house");

        event.setColor(R.color.event_color_02);
        mEvents.add(event);
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

    public List<WeekViewEvent> getEventList(){
        return mEvents;
    }

    public void setNewListEvent(ArrayList<WeekViewEvent> mEvents) {
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

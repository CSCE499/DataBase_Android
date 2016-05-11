package com.example.springroll.database;

import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.springroll.database.signInUtil.SignInActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import library.UserFunctions;
import library.calendarAPI.DateTimeInterpreter;
import library.calendarAPI.MonthLoader;
import library.calendarAPI.WeekView;
import library.calendarAPI.WeekViewEvent;

/**
 * This is a base activity which contains week view and all the codes necessary to initialize the
 * week view.
 * Created by Raquib-ul-Alam Kanak on 1/3/2014.
 * Website: http://alamkanak.github.io
 */
public class MainActivity extends AppCompatActivity implements WeekView.EventClickListener, WeekView.EmptyViewClickListener, MonthLoader.MonthChangeListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener {
    /** Class name for log messages. */
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    /** Key for diplay the canlendar view option. */
    private static final int REQUEST_EVENT = 1;
    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;

    /** The custom UI Calendar display for setting up the column and date. */
    private WeekView mWeekView;

    /** The UserFunction class to store and get calendar information from WAMP SERVER. */
    private UserFunctions mFunction;

    /** Bundle key for saving/restoring the toolbar title. */
    private final static String BUNDLE_KEY_TOOLBAR_TITLE = "title";

    private ArrayList<WeekViewEvent> mNewEvent; //Test Debug arrayList

    private List<WeekViewEvent> EventFromCAL;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG,"onCreate...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calander_base);

        mFunction = new UserFunctions(getApplicationContext());

        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);

        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);

        // Get the event
        mWeekView.getEventClickListener();

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

        // Set long press listener for empty view
        mWeekView.setEmptyViewLongPressListener(this);

        // Initially, there will be no events on the week view because the user has not tapped on
        // it yet.

        EventFromCAL = CalEventManager.get(getApplicationContext()).getEventList();

        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        setupDateTimeInterpreter(false);

    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        Log.d(LOG_TAG,"onMonthChange...");
        Log.i(LOG_TAG,""+EventFromCAL.size());

        List<WeekViewEvent> matchedEvents = new ArrayList<WeekViewEvent>();
        for (WeekViewEvent event : EventFromCAL) {
            if (eventMatches(event, newYear, newMonth)) {
                matchedEvents.add(event);
            }
        }
        return matchedEvents;

    }

    /**
     * Checks if an event falls into a specific year and month.
     * @param event The event to check for.
     * @param year The year.
     * @param month The month.
     * @return True if the event matches the year and month.
     */
    private boolean eventMatches(WeekViewEvent event, int year, int month) {
        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month - 1) || (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month - 1);
    }

    /**
     * Get events that were added by tapping on empty view.
     * @param year The year currently visible on the week view.
     * @param month The month currently visible on the week view.
     * @return The events of the given year and month.
     */
    private ArrayList<WeekViewEvent> getNewEvents(int year, int month) {
        Log.d(LOG_TAG," getNewEvents...");
        // Get the starting point and ending point of the given month. We need this to find the
        // events of the given month.
        Calendar startOfMonth = Calendar.getInstance();
        startOfMonth.set(Calendar.YEAR, year);
        startOfMonth.set(Calendar.MONTH, month - 1);
        startOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        startOfMonth.set(Calendar.HOUR_OF_DAY, 0);
        startOfMonth.set(Calendar.MINUTE, 0);
        startOfMonth.set(Calendar.SECOND, 0);
        startOfMonth.set(Calendar.MILLISECOND, 0);
        Calendar endOfMonth = (Calendar) startOfMonth.clone();
        endOfMonth.set(Calendar.DAY_OF_MONTH, endOfMonth.getMaximum(Calendar.DAY_OF_MONTH));
        endOfMonth.set(Calendar.HOUR_OF_DAY, 23);
        endOfMonth.set(Calendar.MINUTE, 59);
        endOfMonth.set(Calendar.SECOND, 59);

        // Find the events that were added by tapping on empty view and that occurs in the given
        // time frame.
        ArrayList<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        for (WeekViewEvent event : mNewEvent) {
            if (event.getEndTime().getTimeInMillis() > startOfMonth.getTimeInMillis() &&
                    event.getStartTime().getTimeInMillis() < endOfMonth.getTimeInMillis()) {
                events.add(event);
            }
        }
        return events;
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_EVENT){
            //
        }
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        // Save the title so it will be restored properly to match the view loaded when rotation
        // was changed or in case the activity was destroyed.
    }

    @Override
    public void onResume(){
        Log.d(LOG_TAG,"onResume...");
        super.onResume();
        mWeekView.notifyDatasetChanged();
    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected");
        int id = item.getItemId();
        setupDateTimeInterpreter(id == R.id.action_week_view);
        switch (id){
            //Meneu option Today
            case R.id.action_today:
                mWeekView.goToToday();
                return true;

            case R.id.action_day_view:
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(1);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;

            //Menu option 3 days view
            case R.id.action_three_day_view:
                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_THREE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(3);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;

            //Menu option week view
            case R.id.action_week_view:
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(7);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                }
                return true;

            //Menu option logout
            case R.id.action_logout:
                item.setChecked(!item.isChecked());
                mFunction.logoutUser();
                Intent upanel = new Intent(getApplicationContext(), SignInActivity.class);
                upanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(upanel);
                /**
                 * Close Main Screen
                 **/
                finish();
                return true;

            //Menu option setting
            case R.id.action_settings:
                return true;

            //Menu option add event
            case R.id.action_add_event:
                WeekViewEvent e = new WeekViewEvent();
                CalEventManager.get(this).addEvent(e);
                Intent i = new Intent(this,EventActivity.class);
                i.putExtra(EventFragment.EXTRA_EVENT_ID,e.getId());
                startActivityForResult(i,0);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     * @param shortDate True if the date values should be short.
     */
    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }

    /**
     * Getting the Calendar Information
     * @param time
     * @return string details of calendar
     */
    protected String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d/%d", time.get(Calendar.HOUR_OF_DAY),
                time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1,
                time.get(Calendar.DAY_OF_MONTH),time.get(Calendar.YEAR));
    }

    /**
     * Display the event detail on event click
     * @param event: event clicked.
     * @param eventRect: view containing the clicked event.
     */
    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(this, "Clicked " + event.getName(), Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this,EventActivity.class);
        i.putExtra(EventFragment.EXTRA_EVENT_ID, event.getId());
        Log.i("onEventClick", "" + EventFragment.EXTRA_EVENT_ID + ", " + event.getId());

        startActivityForResult(i,0);
    }

    /**
     * Display the long press on even click
     * @param event: event clicked.
     * @param eventRect: view containing the clicked event.
     */
    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(this, "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    /**
     *
     * @param time: {@link Calendar} object set with the date and time of the long pressed position on the view.
     */
    @Override
    public void onEmptyViewLongPress(Calendar time) {
        Toast.makeText(this, "Empty view long pressed: " + getEventTitle(time), Toast.LENGTH_SHORT).show();
    }

    public WeekView getWeekView() {
        return mWeekView;
    }

    /**
     * This is currently not working
     * @param time: {@link Calendar} object set with the date and time of the clicked position on the view.
     */
    @Override
    public void onEmptyViewClicked(Calendar time) {
        Toast.makeText(this, "Empty view pressed: " + getEventTitle(time), Toast.LENGTH_SHORT).show();

        /**
        // Set the new event with duration one hour.
        Calendar endTime = (Calendar) time.clone();
        endTime.add(Calendar.HOUR, 1);

        // Create a new event.
        WeekViewEvent event = new WeekViewEvent(20, "New event", time, endTime);
        mNewEvent.add(event);

        // Refresh the week view. onMonthChange will be called again.
        mWeekView.notifyDatasetChanged();
         */
    }

}

package com.example.springroll.database;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.springroll.database.signInUtil.SignInActivity;

import library.UserFunctions;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private UserFunctions mFunction;
    /**
     * Class name for log messages.
     */
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    /**
     * Bundle key for saving/restoring the toolbar title.
     */
    private final static String BUNDLE_KEY_TOOLBAR_TITLE = "title";

    /**
     * The toolbar view control.
     */
    private Toolbar toolbar;

    /** Our navigation drawer class for handling navigation drawer logic. */
    //private NavigationDrawer navigationDrawer;

    /**
     * Initializes the Toolbar for use with the activity.
     */
    private void setupToolbar(final Bundle savedInstanceState) {
       // toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Set up the activity to use this toolbar. As a side effect this sets the Toolbar's title
        // to the activity's title.
//        setSupportActionBar(toolbar);

        if (savedInstanceState != null) {
            // Some IDEs such as Android Studio complain about possible NPE without this check.
            assert getSupportActionBar() != null;

            // Restore the Toolbar's title.
            getSupportActionBar().setTitle(
                    savedInstanceState.getCharSequence(BUNDLE_KEY_TOOLBAR_TITLE));
        }
    }


    private Cursor mCursor = null;
    private static final String[] COLS = new String[]{
            CalendarContract.Events.TITLE, CalendarContract.Events.DTSTART
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar(savedInstanceState);
        mFunction = new UserFunctions(getApplicationContext());
        //setupToolbar(savedInstanceState);
        //setupNavigationMenu(savedInstanceState);

        /** Calendar set up demo
         Intent intent = new Intent(Intent.ACTION_INSERT);
         intent.setData(CalendarContract.Events.CONTENT_URI);

         intent.setType("vnd.android.cursor.item/event");
         intent.putExtra(CalendarContract.Events.TITLE, "Learn Android");
         intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Home suit home");
         intent.putExtra(CalendarContract.Events.DESCRIPTION, "Download Examples");

         // Setting dates
         GregorianCalendar calDate = new GregorianCalendar(2012, 10, 02);
         intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
         calDate.getTimeInMillis());
         intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
         calDate.getTimeInMillis());

         // make it a full day event
         intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

         // make it a recurring Event
         intent.putExtra(CalendarContract.Events.RRULE, "FREQ=WEEKLY;COUNT=11;WKST=SU;BYDAY=TU,TH");

         // Making it private and shown as busy
         intent.putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
         intent.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

         startActivity(intent);

         *
         if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
         // TODO: Consider calling
         //    ActivityCompat#requestPermissions
         // here to request the missing permissions, and then overriding
         //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
         //                                          int[] grantResults)
         // to handle the case where the user grants the permission. See the documentation
         // for ActivityCompat#requestPermissions for more details.
         return;
         }
         mCursor = getContentResolver().query(
         CalendarContract.Events.CONTENT_URI, COLS, null, null, null);
         mCursor.moveToFirst();

         Button b = (Button)findViewById(R.id.next);
         b.setOnClickListener(this);
         b = (Button)findViewById(R.id.previous);
         b.setOnClickListener(this);
         onClick(findViewById(R.id.previous));
         */
        Intent intent = new Intent(this,BasicActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onSaveInstanceState(final Bundle bundle) {
        super.onSaveInstanceState(bundle);
        // Save the title so it will be restored properly to match the view loaded when rotation
        // was changed or in case the activity was destroyed.
        if (toolbar != null) {
            bundle.putCharSequence(BUNDLE_KEY_TOOLBAR_TITLE, toolbar.getTitle());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
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
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        /**
         TextView tv = (TextView)findViewById(R.id.data);

         String title = "N/A";

         Long start = 0L;

         switch(v.getId()) {
         case R.id.next:
         if(!mCursor.isLast()) mCursor.moveToNext();
         break;
         case R.id.previous:
         if(!mCursor.isFirst()) mCursor.moveToPrevious();
         break;

         }

         Format df = DateFormat.getDateFormat(this);
         Format tf = DateFormat.getTimeFormat(this);
         try {

         title = mCursor.getString(0);

         start = mCursor.getLong(1);

         } catch (Exception e) {

         //ignore

         }

         tv.setText(title+" on "+df.format(start)+" at "+tf.format(start));
         */
    }

    @Override
    public void onBackPressed() {
        final FragmentManager fragmentManager = this.getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag(MainActivity.class.getSimpleName()) == null) {
            final Class fragmentClass = MainActivity.class;
            // if we aren't on the home fragment, navigate home.
            //final Fragment fragment = Fragment.instantiate(this, fragmentClass.getName());

            fragmentManager
                    .beginTransaction()
                    //.replace(R.id.main_fragment_container, fragment, fragmentClass.getSimpleName())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();

            // Set the title for the fragment.

            final ActionBar actionBar = this.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(getString(R.string.app_name));
            }
            return;
        }
        super.onBackPressed();
    }

    /**
    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 1);
        endTime.set(Calendar.MONTH, newMonth - 1);
        WeekViewEvent event = new WeekViewEvent(1, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 30);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, 4);
        endTime.set(Calendar.MINUTE, 30);
        endTime.set(Calendar.MONTH, newMonth-1);
        event = new WeekViewEvent(10, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_02));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 4);
        startTime.set(Calendar.MINUTE, 20);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, 5);
        endTime.set(Calendar.MINUTE, 0);
        event = new WeekViewEvent(10, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_03));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 5);
        startTime.set(Calendar.MINUTE, 30);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 2);
        endTime.set(Calendar.MONTH, newMonth-1);
        event = new WeekViewEvent(2, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_02));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 5);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);
        startTime.add(Calendar.DATE, 1);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 3);
        endTime.set(Calendar.MONTH, newMonth - 1);
        event = new WeekViewEvent(3, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_03));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, 15);
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 3);
        event = new WeekViewEvent(4, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_04));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, 1);
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 3);
        event = new WeekViewEvent(5, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, startTime.getActualMaximum(Calendar.DAY_OF_MONTH));
        startTime.set(Calendar.HOUR_OF_DAY, 15);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 3);
        event = new WeekViewEvent(5, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_02));
        events.add(event);

        //AllDay event
        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 0);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 23);
        event = new WeekViewEvent(7, getEventTitle(startTime),null, startTime, endTime, true);
        event.setColor(getResources().getColor(R.color.event_color_04));
        events.add(event);
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, 8);
        startTime.set(Calendar.HOUR_OF_DAY, 2);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.DAY_OF_MONTH, 10);
        endTime.set(Calendar.HOUR_OF_DAY, 23);
        event = new WeekViewEvent(8, getEventTitle(startTime),null, startTime, endTime, true);
        event.setColor(getResources().getColor(R.color.event_color_03));
        events.add(event);

        // All day event until 00:00 next day
        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, 10);
        startTime.set(Calendar.HOUR_OF_DAY, 0);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.SECOND, 0);
        startTime.set(Calendar.MILLISECOND, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.DAY_OF_MONTH, 11);
        event = new WeekViewEvent(8, getEventTitle(startTime), null, startTime, endTime, true);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);
        return events;
    }*/
}

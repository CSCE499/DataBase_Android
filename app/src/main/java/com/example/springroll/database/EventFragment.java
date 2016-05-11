package com.example.springroll.database;

/**
 * Created by SpringRoll on 4/23/2016.
 */

import android.app.Activity;

import android.app.Dialog;
import android.app.DialogFragment;
>>>>>>> 5e5def1a0c9e4c35098b30734bb6d0bf1599d52d
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Switch;

import java.util.Calendar;

import library.calendarAPI.DateTimePickerDialog;
import library.calendarAPI.WeekViewEvent;

/**
 * Created by SpringRoll on 3/30/2016.
 */
public class EventFragment extends Fragment implements DateTimePickerDialog.DateTimeListener{

    /** Class name for log messages. */
    private final static String LOG_TAG = EventFragment.class.getSimpleName();

    /** Extra Argument for event id*/
    public static final String EXTRA_EVENT_ID = "com.example.springroll.database.event_id";

    private WeekViewEvent mEvent;

    private boolean mSave = false, mEdit = false, mDelete = false, mCancel = false,
            mAllDay = mEvent.isAllDay();


    public static final String EXTRA_EVENT_ID = "com.example.springroll.database.event_id";
    public WeekViewEvent getmEvent() {
        return mEvent;
    }

    public void setmEvent(WeekViewEvent mEvent) {
        this.mEvent = mEvent;
    }

    private boolean mSave = false, mEdit = false, mDelete = false, mCancel = false, mAllDay = false;
    private WeekViewEvent mEvent;
    private Button mStartDateButton, mEndDateButton;
    private EditText mTitle, mLocation, mNotes;
    private Switch mAllDaySwitch;
    private Calendar calendar, mFromDate, mToDate;
    private int year, month, day, hour,minute,am_pm = -2;
    private RatingBar mPriority;

    private boolean edit = false;
    private boolean viewing = true;

    /**
     *
     * @param eventId
     * @return
     */
    public static EventFragment newInstance(long eventId) {
        Log.d(LOG_TAG,"newInstance...");
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_EVENT_ID, eventId);

        //Create a new Fragment
    public static EventFragment newInstance(long eventId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_EVENT_ID, eventId);

        EventFragment fragment = new EventFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG,"onCreate...");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        long eventId = (long)getArguments().getSerializable(EXTRA_EVENT_ID);
        mEvent = CalEventManager.get(getActivity()).getSingleEvent(eventId);

        calendar = Calendar.getInstance();
        mFromDate = Calendar.getInstance();
        mToDate = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mEvent = new WeekViewEvent();
        long eventId = (long)getArguments().getSerializable(EXTRA_EVENT_ID);
        mEvent = CalEventManager.get(getActivity()).getSingleEvent(eventId); //Need to fix this one

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        Log.d(LOG_TAG,"onCreateView...");

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_event,container,false);


            if (NavUtils.getParentActivityName(getActivity()) != null) {
               //this.getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            }

        // Declare the component Title text filed
        mTitle = (EditText)v.findViewById(R.id.event_title);

        if(mEvent.getName() != null) {
           mTitle.setText(mEvent.getName());
        }


               // getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            }

        mTitle = (EditText)v.findViewById(R.id.event_title);
        mTitle.setText(mEvent.getName());
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEvent.setName(s.toString());
                Log.d(LOG_TAG, "onText_title_Changed...");
                Log.i("onTextChanged",s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //This space intentionally left blank
            }
        });//End Title object

        // Declare the component Location text filed
        mLocation = (EditText)v.findViewById(R.id.event_location);

        if(mEvent.getLocation() != null){
            mLocation.setText(mEvent.getLocation());
        }

        });

        mLocation = (EditText)v.findViewById(R.id.event_location);
        mLocation.setText(mEvent.getLocation());
        mLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEvent.setLocation(s.toString());
                Log.d(LOG_TAG, "onText_location_Changed...");
                Log.i("onTextChanged", s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //This space intentionally left blank
            }
        });// End Location object


        // Declare the component Start date button
        mStartDateButton = (Button)v.findViewById(R.id.start_date_button);
        if(mEvent.getStartTime() != null){
            mStartDateButton.setText("");
            Log.i(LOG_TAG,mEvent.getStartTime().toString());
        }else {
            mStartDateButton.setText(setString(am_pm, mStartDateButton, mAllDay));
        }
        mStartDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "onStartButtonChanged...");

        });

        mStartDateButton = (Button)v.findViewById(R.id.start_date_button);

        mFromDate = Calendar.getInstance();
        //this is the test
        mFromDate.set(Calendar.HOUR_OF_DAY, hour);
        mFromDate.set(Calendar.MINUTE, minute);
        mFromDate.set(Calendar.MONTH, month);
        mFromDate.set(Calendar.YEAR, year);
        mFromDate.set(Calendar.DATE, day);
        mEvent.setStartTime(mFromDate);
        //end debug

        mStartDateButton.setText(setString(am_pm, mStartDateButton, mAllDay));
        mStartDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(mStartDateButton, am_pm);
                //mFromDate = Calendar.getInstance();
                //mFromDate.set(year, month, day, hour, minute);
                mFromDate.set(Calendar.HOUR_OF_DAY, hour);
                mFromDate.set(Calendar.MINUTE, minute);
                mFromDate.set(Calendar.MONTH, month);
                mFromDate.set(Calendar.YEAR, year);
                mFromDate.set(Calendar.DATE, day);
                //mEvent.setStartTime(mFromDate);
                Log.i("onButtonChanged", mFromDate.toString());

                mEvent.setStartTime(mFromDate);
                if (mFromDate.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) {
                    mSave = false;
                    mStartDateButton.setPaintFlags(mStartDateButton.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    mSave = true;
                    mStartDateButton.setPaintFlags(0);
                }
            }
        });// End StartDate object


        // Declare the component End date object
        mEndDateButton = (Button)v.findViewById(R.id.end_date_button);
        mEndDateButton.setText(setString(am_pm, mEndDateButton, mAllDay));

        mEndDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "onEndButtonChanged...");

        });

        mEndDateButton = (Button)v.findViewById(R.id.end_date_button);
        mEndDateButton.setText(setString(am_pm, mEndDateButton, mAllDay));

        //This is the test
        mToDate = Calendar.getInstance();
        mToDate.set(Calendar.HOUR_OF_DAY, hour+1);
        mToDate.set(Calendar.MINUTE,minute);
        mToDate.set(Calendar.MONTH, month);
        mToDate.set(Calendar.YEAR, year);
        mToDate.set(Calendar.DATE, day);
        mEvent.setStartTime(mToDate);
        //end debug

        mEndDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(mEndDateButton, am_pm);
                //mToDate = Calendar.getInstance();
                //mToDate.set(year, month, day, hour, minute);
                mToDate.set(Calendar.HOUR_OF_DAY,hour);
                mToDate.set(Calendar.MINUTE,minute);
                mToDate.set(Calendar.MONTH,month);
                mToDate.set(Calendar.YEAR, year);
                mToDate.set(Calendar.DATE,day);
                //mEvent.setStartTime(mToDate);
                Log.i("onButtonChanged", mToDate.toString());
                mEvent.setStartTime(mToDate);
                if(mToDate.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()){
                    mSave = false;
                    mEndDateButton.setPaintFlags(mEndDateButton.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else{
                    mSave = true;
                    mEndDateButton.setPaintFlags(0);
                }
            }
        });// End EndDate object


        //
        });

        mAllDaySwitch = (Switch)v.findViewById(R.id.switch1);
        mAllDaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mAllDay = true;
                    mEvent.setAllDay(true);
                    mStartDateButton.setText(setString(am_pm, mStartDateButton, mAllDay));
                    mEndDateButton.setText(setString(am_pm, mEndDateButton, mAllDay));
                } else {
                    mAllDay = false;
                    mEvent.setAllDay(false);
                    mStartDateButton.setText(setString(am_pm, mStartDateButton, mAllDay));
                    mEndDateButton.setText(setString(am_pm, mEndDateButton, mAllDay));
                }
            }
        });

        mNotes = (EditText)v.findViewById(R.id.notescroll);
        mNotes.setText(mEvent.getmNote());
        //mNotes.setEnabled(false);
        mNotes.setEnabled(true);

        mNotes.setEnabled(false);
        mNotes.setTextColor(Color.BLACK);
        mNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEvent.setmNote(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPriority = (RatingBar)v.findViewById(R.id.ratingBar);
        mPriority.setRating(mEvent.getmPriority());
        //mPriority.setEnabled(false);
        mPriority.setEnabled(true);
        mPriority.setEnabled(false);
        mPriority.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mEvent.setmPriority((int) rating);
            }
        });
        //end Declare

        return v;
    }

    /**
     *
     * @param am_pm
     * @param b
     * @param mAllDay
     * @return
     */

        return v;
    }

    public StringBuilder setString(int am_pm,Button b,Boolean mAllDay){
        StringBuilder text;
        if(mAllDay){
            text = new StringBuilder().append(month).append("/").append(day).append("/").append(year);
        }
        else if(am_pm != -1 && b.getId() == R.id.start_date_button){
            text = new StringBuilder().append(month).append("/").append(day).append("/").append(year).append("  -  ").append(hour).append(":").append(minute).append(" ").append(am_pm == Calendar.AM ? "AM" : "PM");
        }else{
            text = new StringBuilder().append(month).append("/").append(day).append("/").append(year).append("  -  ").append(hour+1).append(":").append(minute).append(" ").append(am_pm == Calendar.AM ? "AM" : "PM");
        }
        return text;
    }

    private void showDateTimeDialog(Button b, int am_pm) {
        DateTimePickerDialog pickerDialog = new DateTimePickerDialog(getActivity(), false, this);
        pickerDialog.show();
        b.setText(setString(am_pm, b, mAllDay));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_frag, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if(mSave) {
           // menu.findItem(R.id.delete_event).setVisible(true);

        if(mSave) {
            menu.findItem(R.id.delete_event).setVisible(true);
            menu.findItem(R.id.edit_event).setVisible(true);
            menu.findItem(R.id.cancel_event).setVisible(false);
        }
        else {
            //menu.findItem(android.R.id.home).setVisible(false);
            menu.findItem(R.id.delete_event).setVisible(false);
            menu.findItem(R.id.edit_event).setVisible(false);
            menu.findItem(R.id.cancel_event).setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if(NavUtils.getParentActivityIntent(getActivity()) != null){
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;

            case R.id.save_event:
                mSave = true;
                if(NavUtils.getParentActivityIntent(getActivity()) != null){
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;

            case R.id.cancel_event:
                mSave = false;
                if(NavUtils.getParentActivityIntent(getActivity()) != null){
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;

            case R.id.delete_event:
                mSave = false;
                return true;
            case R.id.edit_event:

                //This is the test need to have method for this!
                mStartDateButton.setEnabled(true);
                mEndDateButton.setEnabled(true);
                mTitle.setEnabled(true);
                mLocation.setEnabled(true);
                mAllDaySwitch.setEnabled(true);
                mNotes.setEnabled(true);
                mPriority.setEnabled(true);
                //end test
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        //Save the instance
        //CalEventManager.get(getActivity()).saveEvents();
    }

    public void returnResult(){
        getActivity().setResult(Activity.RESULT_OK, null);
    }

    /**
     *
     * @param b
     * @param am_pm
     */
    private void showDateTimeDialog(Button b, int am_pm) {
        Log.d(LOG_TAG,"showDateTimePicker...");
        DateTimePickerDialog pickerDialog = new DateTimePickerDialog(getActivity(), false, this);
        //pickerDialog.show();
        b.setText(setString(am_pm, b, mAllDay));
        Log.i("showDateSetText",setString(am_pm, b, mAllDay).toString());
        pickerDialog.show();
        getActivity().setResult(Activity.RESULT_OK,null);
    }

    @Override
    public void onDateTimeSelected(int year, int month, int day, int hour, int min, int am_pm) {
        Log.d(LOG_TAG,"onDataTimeSelected...");
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = min;
        this.am_pm = am_pm;
        Calendar mCal = Calendar.getInstance();
        mCal.set(Calendar.HOUR_OF_DAY,hour);
        mCal.set(Calendar.MINUTE,minute);
        mCal.set(Calendar.MONTH,month);
        mCal.set(Calendar.YEAR, year);
        mCal.set(Calendar.DATE, day);
        Log.i("onDataTimeSelected: ", mCal.toString());

        //String text = day + "/" + month + "/" + year + " - " + hour + ":" + min;
        // if (am_pm != -1)
        //text = text + (am_pm == Calendar.AM ? "AM" : "PM");
    }

    public static class DateTimePickerFragment extends DialogFragment implements DateTimePickerDialog.DateTimeListener {
        private int year,month,day,am_pm;
        private int hour,minute;
        DateTimePickerDialog.DateTimeListener onDateSet;

        public DateTimePickerFragment(){}

        public void setCallBack(DateTimePickerDialog.DateTimeListener ondate){
            onDateSet = ondate;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new DateTimePickerDialog(getActivity(),false,this);
        }

        @Override
        public void setArguments(Bundle args) {
            super.setArguments(args);
            year = args.getInt("year");
            month = args.getInt("month");
            day = args.getInt("day");
            hour = args.getInt("hour");
            minute = args.getInt("minute");
        }

        @Override
        public void onDateTimeSelected(int year, int month, int day, int hour, int min, int am_pm) {
            this.year = year;
            this.month = month;
            this.day = day;
            this.hour = hour;
            this.minute = min;
            this.am_pm = am_pm;
        }
    }

}




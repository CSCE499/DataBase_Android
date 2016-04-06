package com.example.springroll.database;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import java.util.Calendar;

import library.CalendarAPI.DateTimePickerDialog;
import library.CalendarAPI.WeekViewEvent;

/**
 * Created by SpringRoll on 3/30/2016.
 */
public class EventActivity extends Fragment implements DateTimePickerDialog.DateTimeListener{
    public WeekViewEvent getmEvent() {
        return mEvent;
    }

    public void setmEvent(WeekViewEvent mEvent) {
        this.mEvent = mEvent;
    }

    private boolean mSave = false;
    private WeekViewEvent mEvent;
    private Button mStartDate, mEndDate, mStartTime,mEndTime;
    private EditText mTitle, mLocation;
    private Switch mAllDaySwitch;
    private Calendar calendar, mFromDate, mToDate;
    private int year, month, day, hour,minute,am_pm = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEvent = new WeekViewEvent();

        mFromDate = mToDate = calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        if(hour >= 12){
            am_pm = 1;
        }
        //showDate(year, month+1, day);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_event,container,false);

        mTitle = (EditText)v.findViewById(R.id.event_title);
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEvent.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //This space intentionally left blank
            }
        });

        mLocation = (EditText)v.findViewById(R.id.event_location);
        mLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEvent.setLocation(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //This space intentionally left blank
            }
        });

        mStartDate = (Button)v.findViewById(R.id.start_date_button);
        mStartDate.setText(new StringBuilder().append(month).append("/").append(day).append("/").append(year));
        mStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDatePicker(mStartDate,mFromDate);
                //DateTimePickerDialog pickerDialog = new DateTimePickerDialog(this, false, this);
                //pickerDialog.show();
                showDateTimeDialog();
            }
        });
        /*fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mStartDate.setText(new StringBuilder().append(monthOfYear).append("/").append(dayOfMonth).append("/").append(year));
                mFromDate.set(year, monthOfYear, dayOfMonth);

            }
        });
        */





        mEndDate = (Button)v.findViewById(R.id.end_date_button);
        mEndDate.setText(new StringBuilder().append(month).append("/").append(day).append("/").append(year));
        mEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDatePicker(mEndDate,mToDate);
            }
        });

        //mStartTime = (Button)v.findViewById(R.id.start_time_button);

        //mEndTime = (Button)v.findViewById(R.id.end_time_button);


        mAllDaySwitch = (Switch)v.findViewById(R.id.switch1);
        mAllDaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mEvent.setAllDay(true);
                    //RelativeLayout.LayoutParams param1 = (RelativeLayout.LayoutParams) mStartDate.getLayoutParams();
                    //param1.width = ViewGroup.LayoutParams.MATCH_PARENT;
                   // mStartDate.setLayoutParams(param1);
                    //mStartTime.setVisibility(View.GONE);
                   // RelativeLayout.LayoutParams param2 = (RelativeLayout.LayoutParams) mEndDate.getLayoutParams();
                   // param2.width = ViewGroup.LayoutParams.MATCH_PARENT;
                   // mEndDate.setLayoutParams(param2);
                    //mEndTime.setVisibility(View.GONE);

                }else{
                    //mEvent.setAllDay(false);
                    //RelativeLayout.LayoutParams param1 = (RelativeLayout.LayoutParams) mStartDate.getLayoutParams();
                    //param1.width = 350;
                    //mStartDate.setLayoutParams(param1);
                    //mStartTime.setVisibility(View.VISIBLE);
                    //RelativeLayout.LayoutParams param2 = (RelativeLayout.LayoutParams) mEndDate.getLayoutParams();
                    //param2.width = 350;
                    //mEndDate.setLayoutParams(param2);
                    //mEndTime.setVisibility(View.VISIBLE);
                }
            }
        });
        return v;
    }

    private void showDateTimeDialog() {
        DateTimePickerDialog pickerDialog = new DateTimePickerDialog(getActivity(), false, this);
        pickerDialog.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_frag, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if(mSave)
            menu.findItem(R.id.delete_event).setVisible(true);
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
                return true;

            case R.id.cancel_event:
                return true;

            case R.id.delete_event:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateTimeSelected(int year, int month, int day, int hour, int min, int am_pm) {

    }



    //////////////////////////////////////
    //
    //      DatePickerDialog
    //      & TimePickerDialog
    //
    ////////////////////////////////////////

    /*
    private void showDatePicker(final Button b, final Calendar mCal){
        DateTimePickerFragment date = new DateTimePickerFragment();
        DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                b.setText(new StringBuilder().append(monthOfYear+1).append("/").append(dayOfMonth).append("/").append(year));
                mCal.set(year,monthOfYear+1,dayOfMonth);
            }
        };
        Bundle args = new Bundle();
        args.putInt("year", calendar.get(Calendar.YEAR));
        args.putInt("month", calendar.get(Calendar.MONTH));
        args.putInt("day", calendar.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        date.setCallBack(ondate);
        date.show(getFragmentManager(),"Date Picker");

    }

    private void showTimePicker(final Bundle b, final Calendar mcal){
        DateTimePickerFragment date = new DateTimePickerFragment();
    }

    public static class DateTimePickerFragment extends DialogFragment{
        DatePickerDialog.OnDateSetListener onDateSet;
        TimePickerDialog.OnTimeSetListener onTimeSet;
        private int year,month,day;
        private int hour,minute;

        public DateTimePickerFragment(){}

        public void setCallBack(DatePickerDialog.OnDateSetListener ondate){
            onDateSet = ondate;
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
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new DatePickerDialog(getActivity(),onDateSet,year,month,day);
        }
    }
    */



}



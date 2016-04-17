package com.example.springroll.database;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import library.CalendarAPI.WeekViewEvent;

/**
 * Created by SpringRoll on 3/31/2016.
 */
public class EventListFragment extends ListFragment{
    private static final String TAG = "EventListFragment";
    private List<WeekViewEvent> mEvent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.title);
        mEvent = CalEventManager.get(getActivity()).getmEvents();

        EventAdapter adapter = new EventAdapter(mEvent);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //WeekViewEvent e = (WeekViewEvent)(getListAdapter()).getItem(position);
        WeekViewEvent e = ((EventAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, e.getName() + " was clicked");

        Intent i = new Intent(getActivity(),BasicActivity.class);
        i.putExtra(EventActivity.EXTRA_EVENT_ID, e.getId());
        startActivity(i);
    }

    private class EventAdapter extends ArrayAdapter<WeekViewEvent>{

        public EventAdapter(List<WeekViewEvent> e){
            super(getActivity(),0,e);
        }

        @Override
        public View getView(int posistion, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_event,null);
            }

            //Configure the view for this event
            WeekViewEvent e = getItem(posistion);

            TextView title = (TextView)convertView.findViewById(R.id.event_list_titleTextView);
            return convertView;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.action_add_event:
                WeekViewEvent e = new WeekViewEvent();
                CalEventManager.get(getActivity()).addEvent(e);
                Intent i = new Intent(getActivity(),BaseActivity.class);
                i.putExtra(EventActivity.EXTRA_EVENT_ID,e.getId());
                startActivityForResult(i,0);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public void onResume(){
         super.onResume();
         ((EventAdapter)getListAdapter()).notifyDataSetChanged();
     }

    public void returnResult(){
        getActivity().setResult(Activity.RESULT_OK,null);
    }
}

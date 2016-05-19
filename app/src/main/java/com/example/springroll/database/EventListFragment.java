package com.example.springroll.database;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import library.calendarAPI.WeekViewEvent;

/**
 * Created by SpringRoll on 3/31/2016.
 */
public class EventListFragment extends ListFragment{
    private static final String TAG = "EventListFragment";
    private static final int REQUEST_EVENT = 1;
    private boolean mSubtitleVisible;
    private List<WeekViewEvent> mEvent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        getActivity().setTitle(R.string.title);
        mEvent = CalEventManager.get(getActivity()).getEventList();

        EventAdapter adapter = new EventAdapter(mEvent);
        setListAdapter(adapter);

        //setRetainInstance(true);
        mSubtitleVisible = false;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ListView listView = (ListView)v.findViewById(android.R.id.list);
        registerForContextMenu(listView);
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //Get event from the adapter
        WeekViewEvent e = ((EventAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, e.getName() + " was clicked");
        //Start EventActivity
        Intent i = new Intent(getActivity(),EventActivity.class);
        i.putExtra(EventFragment.EXTRA_EVENT_ID, e.getId());
        startActivity(i);
        //startActivityForResult(i,REQUEST_EVENT);
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
            title.setText(e.getName());
            return convertView;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.action_add_event:
                WeekViewEvent e = new WeekViewEvent();
                CalEventManager.get(getActivity()).addEvent(e);
                Intent i = new Intent(getActivity(),EventActivity.class);
                i.putExtra(EventFragment.EXTRA_EVENT_ID,e.getId());
                startActivityForResult(i,0);
                return true;
                /**
                 case R.id.menu_item_show_subtitle:
                 if (getActivity().getActionBar().getSubtitle() == null) {
                 getActivity().getActionBar().setSubtitle(R.string.show_subtitle);
                 mSubtitleVisible = true;
                 item.setTitle(R.string.hide_subtitle);
                 }else{
                 getActivity().getActionBar().setSubtitle(null);
                 mSubtitleVisible = false;
                 item.setTitle(R.string.show_subtitle);
                 }
                 return true;
                 */
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.menu_event_item_context,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;
        EventAdapter adapter = (EventAdapter)getListAdapter();
        WeekViewEvent e = adapter.getItem(position);

        switch (item.getItemId()){
            case R.id.context_delete_event:
                CalEventManager.get(getActivity()).deleteEvent(e);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_EVENT){
            //
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override public void onResume(){
         super.onResume();
         ((EventAdapter)getListAdapter()).notifyDataSetChanged();
     }

}

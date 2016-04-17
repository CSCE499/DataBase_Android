package com.example.springroll.database;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by SpringRoll on 3/31/2016.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {
    protected abstract Fragment createdFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_frag);
        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.frag_container);
        if(fragment == null){
            //fragment = new EventActivity();
            fragment = createdFragment();
            fm.beginTransaction().add(R.id.frag_container,fragment).commit();
        }
    }
}

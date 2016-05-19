<<<<<<< HEAD
package com.example.springroll.database;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by SpringRoll on 5/5/2016.
 */
public class ComputeActivity extends Activity{
    private Button compute;
    private TimePicker pick;

    private int hr;
    private int min;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);


        pick = (TimePicker)findViewById(R.id.picker);
        //pick.setIs24HourView(true);
        pick.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hr = hourOfDay;
                min = minute;
            }
        });

        compute = (Button)findViewById(R.id.computeButton);

        compute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "hr = " + hr + ", min = " + min, Toast.LENGTH_SHORT).show();
                //Intent i = new Intent(this,MainActivity.class);
                //(i);
                finish();
            }
        });

    }

}
=======
package com.example.springroll.database;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by SpringRoll on 5/5/2016.
 */
public class ComputeActivity extends Activity{
    private Button compute;
    private TimePicker pick;

    private int hr;
    private int min;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);


        pick = (TimePicker)findViewById(R.id.picker);
        //pick.setIs24HourView(true);
        pick.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hr = hourOfDay;
                min = minute;
            }
        });

        compute = (Button)findViewById(R.id.computeButton);

        compute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "hr = " + hr + ", min = " + min, Toast.LENGTH_SHORT).show();
                //Intent i = new Intent(this,MainActivity.class);
                //(i);
                finish();
            }
        });

    }

}
>>>>>>> b642cffb34a4db2a8810d702f8bbb77eda4c8f6f

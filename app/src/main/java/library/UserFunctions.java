package library;

import android.content.Context;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import library.calendarAPI.WeekViewEvent;

/**
 * Created by SpringRoll on 1/5/2016.
 */
public class UserFunctions {
    private final static String LOG_TAG = UserFunctions.class.getSimpleName();
    private JSONParser jsonParser;
    private static UserFunctions singleton = null;
    private Context context;
    private DatabaseHandler db;
    //URL of the PHP API
    private static String loginURL = "http://152.117.180.231/webapp/";
    private static String registerURL = "http://152.117.180.231/webapp/";
    private static String scheduleURL = "http://152.117.180.231/webapp/";

    private static String login_tag = "login";
    private static String register_tag = "register";
    private static String updateEvents_tag = "update_event";
    private static String addEvents_tag = "add_event";
    private static String getEvents_tag = "get_event";
    private static String deleteEvents_tag = "delete_event";

    /**
     * Constructor.
     * @param context context.
     */
    public UserFunctions(final Context context){
        assert (singleton ==null);
        singleton = this;
        this.context = context;

        db = new DatabaseHandler(context);
        jsonParser = new JSONParser();
    }

    /**
     * Gets the singleton instance of this class.
     * @return instance
     */
    public synchronized static UserFunctions getUserFunctionManager() {
        return singleton;
    }


    /**
     * Function to Login
     * @param username username
     * @param password password
     * @return return the json object with username and password
     */
    public JSONObject loginUser(String username, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        return json;
    }

    /**
     * Function to  Register
     * @param username username
     * @param password password
     * @return return the json object with username and password
     */
    public JSONObject registerUser(String username, String password){
        // Building Parameters
        List <NameValuePair> params= new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        return json;
    }

    /**
    private String mFilename;
    public void saveEvents(ArrayList<WeekViewEvent> mEvents)throws JSONException, IOException {
        // Build an array in JSON
        JSONArray array = new JSONArray();
        for (WeekViewEvent c : mEvents)
            array.put(c.toJSON());
        // Write the file to disk
        Writer writer = null;
        try {
            OutputStream out = context
                    .openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }*/

    public JSONObject addUserSchedule(JSONObject event){
        //Get Username
        String username = db.getUserDetails().get("username");

        // Building Parameters
        List <NameValuePair> params= new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", addEvents_tag));
        params.add(new BasicNameValuePair("username", "admin"));
        params.add(new BasicNameValuePair("event", event.toString()));
        Log.i("addUserSchedule", event.toString());
        JSONObject json = jsonParser.getJSONFromUrl(scheduleURL, params);
        return json;
    }

    public JSONObject updateUserSchedule(JSONObject event){
        //Get Username
        String username = db.getUserDetails().get("username");

        // Building Parameters
        List <NameValuePair> params= new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", updateEvents_tag));
        params.add(new BasicNameValuePair("username", "admin"));
        params.add(new BasicNameValuePair("event", event.toString()));
        Log.i("addUserSchedule", event.toString());
        JSONObject json = jsonParser.getJSONFromUrl(scheduleURL, params);
        return json;
    }

    public JSONObject getUserShcedule(){
        //Get Username
        String username = db.getUserDetails().get("username");
        // Building Parameters
        List <NameValuePair> params= new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", getEvents_tag));
        params.add(new BasicNameValuePair("username", username));
        //params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(scheduleURL, params);
        return json;
    }

    public JSONObject deleteSchedule(String eventID){
        //Get Username
        String username = db.getUserDetails().get("username");
        // Building Parameters
        List <NameValuePair> params= new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", deleteEvents_tag));
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("event", ""+eventID));
        JSONObject json = jsonParser.getJSONFromUrl(scheduleURL, params);
        return json;
    }

    public List<WeekViewEvent> repeatEvent(WeekViewEvent e){
        Log.d(LOG_TAG,"repeatEvent..");
        List<WeekViewEvent> repeat = new ArrayList<WeekViewEvent>();
        SimpleDateFormat dayFormat = new SimpleDateFormat("E", Locale.US);


        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm",Locale.US);
        //String currentday = dayFormat.format(e.getStartTime().getTime());
        int diff = 8;
        //WeekViewEvent event = new WeekViewEvent();
        if(e.getmDoneDate() == null){
            //diff = 30;
            //Log.i(LOG_TAG,"inside if..."+diff);

        }else {
            //diff = e.getmDoneDate().get(Calendar.DAY_OF_MONTH) - e.getStartTime().get(Calendar.DAY_OF_MONTH);
            //Log.i(LOG_TAG, "inside else..." + diff + " " + e.getmDoneDate().getTime().toString());
        }
        //repeat.add(e);
       WeekViewEvent[] event = new WeekViewEvent[diff-1];
        for(int i = 0; i < diff-1; i++) {
           //event[i] = new WeekViewEvent(e);

            event[i].setmPrepeatID(e.getId());
        }
        //WeekViewEvent event[i] = new WeekViewEvent(e);
        /*7
        WeekViewEvent event = new WeekViewEvent();
        event.setColor(e.getColor());
        event.setEndTime(e.getEndTime());
        event.setStartTime(e.getStartTime());
        event.setLocation(e.getLocation());
        event.setAllDay(e.isAllDay());
        event.setmCourse(e.getmCourse());
        event.setmDoneDate(e.getmDoneDate());
        event.setmRepeatDays(e.getmRepeatDays());
        event.setmNote(e.getmNote());
        event.setmPriority(e.getmPriority());
        event.setmPrepeatID(e.getId());
        event.setName(e.getName());
        */

        Date start = e.getStartTime().getTime();

        Calendar startTime = Calendar.getInstance();
        //Calendar startTime =  e.getStartTime();
        startTime.set(Calendar.DAY_OF_MONTH,start.getDate());
        startTime.set(Calendar.HOUR_OF_DAY, start.getHours());
        startTime.set(Calendar.MINUTE, start.getMinutes());
        startTime.set(Calendar.MONTH, start.getMonth());
        startTime.set(Calendar.YEAR, start.getYear() + 1900);


        int m = e.getStartTime().get(Calendar.DAY_OF_MONTH);
        Log.i(LOG_TAG, "begin of loop...S--" + diff + " " + start.toString());


        Date end = e.getEndTime().getTime();

        Calendar endTime = Calendar.getInstance();
        //Calendar endTime = e.getEndTime();
        endTime.set(Calendar.DAY_OF_MONTH,end.getDate());
        endTime.set(Calendar.HOUR_OF_DAY, end.getHours());
        endTime.set(Calendar.MINUTE, end.getMinutes());
        endTime.set(Calendar.MONTH, end.getMonth());
        endTime.set(Calendar.YEAR, end.getYear() + 1900);

        int n = e.getEndTime().get(Calendar.DAY_OF_MONTH);


        Log.i(LOG_TAG, "begin of loop...E--" + diff + " " + end.toString());

        for (int i = 0; i < diff; i++) {
            startTime.set(Calendar.DAY_OF_MONTH, m + i);
            endTime.set(Calendar.DAY_OF_MONTH, n + i);



            String letter = dayFormat.format(startTime.getTime());
            Log.i(LOG_TAG, "inside loop..." + i +"--" + letter);

            String matchedLetter  = "G";
            switch (letter){
                case "Mon":{
                    matchedLetter = "M";
                    break;
                }
                case "Tue":{
                    matchedLetter = "T";
                    break;
                }
                case "Wed":{
                    matchedLetter = "W";
                    break;
                }
                case "Thu":{
                    matchedLetter = "R";
                    break;
                }
                case "Fri":{
                    matchedLetter = "F";
                    break;
                }
                case "Sat":{
                    matchedLetter = "A";
                    break;
                }
                case "Sun":{
                    matchedLetter = "S";
                    break;
                }
            }

            if (e.getmRepeatDays().indexOf(matchedLetter) >= 0 && i < diff-1){
                start = e.getStartTime().getTime();

                startTime = Calendar.getInstance();
                startTime.set(Calendar.DAY_OF_MONTH,start.getDate());
                startTime.set(Calendar.HOUR_OF_DAY, start.getHours());
                startTime.set(Calendar.MINUTE, start.getMinutes());
                startTime.set(Calendar.MONTH, start.getMonth());
                startTime.set(Calendar.YEAR, start.getYear()+1900);


                m = e.getStartTime().get(Calendar.DAY_OF_MONTH);
                Log.i(LOG_TAG, "begin of loop...S--" + diff + " " + start.toString());


                end = e.getEndTime().getTime();

                endTime = Calendar.getInstance();
                endTime.set(Calendar.DAY_OF_MONTH,end.getDate());
                endTime.set(Calendar.HOUR_OF_DAY, end.getHours());
                endTime.set(Calendar.MINUTE, end.getMinutes());
                endTime.set(Calendar.MONTH, end.getMonth());
                endTime.set(Calendar.YEAR, end.getYear() + 1900);

                n = e.getEndTime().get(Calendar.DAY_OF_MONTH);

                //event.setId(event.generateUniqueId());

                Log.i(LOG_TAG, "inside loop...S--" + diff + " " + startTime.getTime().toString());
                event[i].setStartTime(startTime);

                event[i].setEndTime(endTime);
                Log.i(LOG_TAG, "inside loop...E--" + diff + " " + endTime.getTime().toString());
                event[i].setmPrepeatID(e.getId());



                //if()
                    repeat.add(event[i]);
                Log.i(LOG_TAG, "inside loop...Event_Id--" + event[i].getId());
                Log.i(LOG_TAG, "inside loop...Event_Mother--"+e.getStartTime().getTime() + " +++ "+ e.getEndTime().getTime());
            }


        }
        //event = new WeekViewEvent(event);

        return repeat;
    }

    private int getDay(Calendar object){
        return -1;
    }

    /**
     * Function to logout user
     * Resets the temporary data stored in SQLite Database
     * @param
     * @return
     */
    public boolean logoutUser(){
        Log.d(LOG_TAG, "logoutUser");
        db.resetTables();
        return true;
    }
}

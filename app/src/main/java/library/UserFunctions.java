package library;

import android.content.Context;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SpringRoll on 1/5/2016.
 */
public class UserFunctions {
    private final static String LOG_TAG = UserFunctions.class.getSimpleName();
    private JSONParser jsonParser;
    private static UserFunctions singleton = null;
    private Context context = null;
    private DatabaseHandler db;
    //URL of the PHP API
    private static String loginURL = "http://152.117.180.231/webapp/";
    private static String registerURL = "http://152.117.180.231/webapp/";

    private static String login_tag = "login";
    private static String register_tag = "register";
    private static String saveEvents_tag = "save_event";

    /**
     * Constructor.
     * @param context context.
     */
    public UserFunctions(final Context context){
        assert (singleton ==null);
        singleton = this;
        this.context = context.getApplicationContext();

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
        JSONObject json = jsonParser.getJSONFromUrl(registerURL,params);
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

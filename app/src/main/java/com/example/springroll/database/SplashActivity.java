package com.example.springroll.database;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.example.springroll.database.SignIn.SignInActivity;

import java.util.concurrent.CountDownLatch;

import library.DatabaseHandler;
import library.UserFunctions;

/**
 * Created by SpringRoll on 1/28/2016.
 */
public class SplashActivity extends Activity{
    private final static String LOG_TAG = SplashActivity.class.getSimpleName();
    private final CountDownLatch timeoutLatch = new CountDownLatch(1);
    UserFunctions functionsManager;
    DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        functionsManager = new UserFunctions(this);
        db = new DatabaseHandler(this);

        final Thread thread = new Thread(new Runnable() {
            public void run() {
                String provider = db.getUserDetails().get("username");
                if(provider != null){
                    Log.i("SplashActivity", provider);
                    goMain();
                }else
                    goSignIn();
                // Wait for the splash timeout.
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) { System.exit(1); }

                // Expire the splash page delay.
                timeoutLatch.countDown();
            }
        });
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Touch event bypasses waiting for the splash timeout to expire.
        timeoutLatch.countDown();
        return true;
    }

    /**
     * Starts an activity after the splash timeout.
     * @param intent the intent to start the activity.
     */
    private void goAfterSplashTimeout(final Intent intent) {
        final Thread thread = new Thread(new Runnable() {
            public void run() {
                // wait for the splash timeout expiry or for the user to tap.
                try {
                    timeoutLatch.await();
                } catch (InterruptedException e) {
                    System.exit(1);
                }


                SplashActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        startActivity(intent);
                        // finish should always be called on the main thread.
                        finish();
                    }
                });
            }
        });
        thread.start();
    }

    /**
     * Go to the main activity after the splash timeout has expired.
     */
    protected void goMain() {
        Log.d(LOG_TAG, "Launching Main Activity...");
        goAfterSplashTimeout(new Intent(this, BasicActivity.class));
    }

    /**
     * Go to the sign in activity after the splash timeout has expired.
     */
    protected void goSignIn() {
        Log.d(LOG_TAG, "Launching Sign-in Activity...");
        goAfterSplashTimeout(new Intent(this, SignInActivity.class));
    }
}

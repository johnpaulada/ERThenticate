package com.erthenticate.app.erthenticate;

import android.app.IntentService;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

class ETService extends IntentService {

    private final IBinder mBinder = new LocalBinder();
    // Random number generator
    private final Random mGenerator = new Random();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ETService(String name) {
        super(name);
    }

    public ETService() {
        super("ETService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
//        Logger.getLogger("app").log(Level.INFO, intent.getDataString());
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        ETService getService() {
            // Return this instance of LocalService so clients can call public methods
            return ETService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /** method for clients */
    public int getRandomNumber() {
        return mGenerator.nextInt(100);
    }

    public void showWindow(WindowManager wm, LinearLayout ll, WindowManager.LayoutParams ll_lp) {
        //And finally we add what we created to the screen.
        wm.addView(ll, ll_lp);
    }
}

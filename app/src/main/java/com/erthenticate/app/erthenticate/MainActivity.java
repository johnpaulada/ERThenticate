package com.erthenticate.app.erthenticate;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public final static int REQUEST_CODE = 5463;
    ETService mService;
    boolean mBound = false;
    GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mServiceIntent = new Intent(MainActivity.this, ETService.class);
                MainActivity.this.startService(mServiceIntent);
                bindService(mServiceIntent, mConnection, Context.BIND_AUTO_CREATE);
            }
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkDrawOverlayPermission() {
        /** check if we already  have permission to draw over other apps */
        if (!Settings.canDrawOverlays(this)) {
            /** if not construct intent to request permission */
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            /** request permission via start activity for result */
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Log.d("SERVICE", "WOAH");
            mDetector = new GestureDetectorCompat(this, new MyGestureListener());
            createWindow();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        /** check if received result code
         is equal our requested code for draw permission  */
        if (requestCode == REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
                Log.d("SERVICE", "WOAH");
                mDetector = new GestureDetectorCompat(this, new MyGestureListener());
                createWindow();
            }
        }
    }

    private void createWindow() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        LinearLayout ll;
        WindowManager.LayoutParams ll_lp;

        //Just a sample layout parameters.
        ll_lp = new WindowManager.LayoutParams();
        ll_lp.format = PixelFormat.TRANSPARENT;
        ll_lp.height = WindowManager.LayoutParams.FILL_PARENT;
        ll_lp.width = WindowManager.LayoutParams.FILL_PARENT;
        ll_lp.gravity = Gravity.CLIP_HORIZONTAL | Gravity.TOP;

        //This one is necessary.
        ll_lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

        //Play around with these two.
//        ll_lp.flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;

        ll = new PassLayout(this);
        ll.setBackgroundColor(android.graphics.Color.argb(0, 0, 0, 0));
        ll.setHapticFeedbackEnabled(true);
        ll.setOnGenericMotionListener(new View.OnGenericMotionListener() {
            @Override
            public boolean onGenericMotion(View v, MotionEvent event) {
                Log.d("SERVICE", event.toString());
                return false;
            }
        });
        ll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("SERVICE", "Damn son");
//
//                int action = MotionEventCompat.getActionMasked(event);
                String DEBUG_TAG = "SERVICE";
//
                Log.d(DEBUG_TAG, event.toString());
                getWindow().superDispatchTouchEvent(event);
//
//                switch(action) {
//                    case (MotionEvent.ACTION_DOWN) :
//                        Log.d(DEBUG_TAG,"Action was DOWN");
//                        return false;
//                    case (MotionEvent.ACTION_MOVE) :
//                        Log.d(DEBUG_TAG,"Action was MOVE");
//                        return false;
//                    case (MotionEvent.ACTION_UP) :
//                        Log.d(DEBUG_TAG,"Action was UP");
//                        return false;
//                    case (MotionEvent.ACTION_CANCEL) :
//                        Log.d(DEBUG_TAG,"Action was CANCEL");
//                        return false;
//                    case (MotionEvent.ACTION_OUTSIDE) :
//                        Log.d(DEBUG_TAG,"Movement occurred outside bounds " +
//                                "of current screen element");
//                        return false;
//                    default :
//                        return false;
//                }

                return false;
            }
        });

        mService.showWindow(wm, ll, ll_lp);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG,"onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
            return true;
        }
    }

    public void onButtonClick(View v) {
        if (mBound) {
            int num = mService.getRandomNumber();
            checkDrawOverlayPermission();
            Toast.makeText(this, "number: " + num, Toast.LENGTH_SHORT).show();
        }
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            ETService.LocalBinder binder = (ETService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            Snackbar.make((View)findViewById(R.id.fab), "Service started", Snackbar.LENGTH_SHORT)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.e("SERVICE", "onServiceDisconnected");
            mBound = false;
        }
    };
}

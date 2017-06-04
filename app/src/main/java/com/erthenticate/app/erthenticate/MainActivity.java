package com.erthenticate.app.erthenticate;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private App app;
    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        app = new App();
        initListeners();
    }

    private void recordEvent(MotionEvent event) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (app.getState().getState() == State.STATE_RECORDING) {
            this.mDetector.onTouchEvent(event);
            recordEvent(event);
        }

        return super.onTouchEvent(event);
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

    private void initListeners() {
        // Init fab
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // Init submit
        Button submitBtn = (Button) findViewById(R.id.submitButton);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // If ready to record
                if (app.getState().getState() == State.STATE_READY) {

                    // Set name
                    EditText nameInput = (EditText) findViewById(R.id.nameInput);
                    app.setName(nameInput.getText().toString());

                    // Set recording state
                    app.getState().setState(State.STATE_RECORDING);

                    // Create blank record list
                    app.setRecordList(new ArrayList<Record>());
                    showMessage("Started Recording.");
                }
            }
        });

        // Init gesture listeners
        mDetector = new GestureDetectorCompat(this, new CustomSimpleGestureListener());
    }

    class CustomSimpleGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            app.setCounter(app.getCounter()+1);

            showMessage("Fling Detected");
            Log.d("MOTION", "FLING");

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    private void showMessage(String msg) {
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT)
                .setAction("Okay", null)
                .show();
    }
}

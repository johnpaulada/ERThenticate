package com.erthenticate.app.erthenticate;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private App app;

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
        Log.d("MOTION", event.toString());

        Record record = new Record(
                0, // Phone ID
                1, // User ID
                1, // Document ID
                event.getEventTime(), // Timestamp
                event.getAction(), // Touch action
                getResources().getConfiguration().orientation, // Phone orientation
                event.getX(), // X
                event.getY(), // Y
                event.getPressure(), // Pressure
                event.getSize(), // Touch area
                0.0 // Finger orientation
        );

        app.getRecordList().add(record);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (app.getState().getState() == State.STATE_RECORDING) {
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
                if (app.getState().getState() == State.STATE_RECORDING) {
                    String csvFilename = app.getName() + ".csv";
                    writeFile(csvFilename);
                    readFile(csvFilename);
                    app.getState().setState(State.STATE_READY);

                    Snackbar.make(findViewById(android.R.id.content), "Stopped recording.", Snackbar.LENGTH_SHORT)
                            .setAction("Verify", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO: Add verification here
                                }
                            })
                            .show();
                }
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
    }

    private void writeFile(String filename) {
        try {
            File file = getInternalFile(filename);

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            for (Record record : app.getRecordList()) {
                bw.write(record + "");
                bw.newLine();
            }

            bw.close();
        } catch (IOException ex) {
            Log.e("IO", ex.getMessage());
        }
    }

    private String readFile(String filename) {
        try {
            File file = getInternalFile(filename);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder builder = new StringBuilder();
            String line = reader.readLine();

            while (line != null) {
                Log.d("CSV", line);
                builder.append(line);
                line = reader.readLine();
            }

            reader.close();

            return builder.toString();
        } catch (IOException ex) {
            Log.e("IO", ex.getMessage());
        }

        return "";
    }

    private File getInternalFile(String filename) {
        return new File(MainActivity.this.getFilesDir(), filename);
    }

    private void showMessage(String msg) {
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT)
                .setAction("Okay", null)
                .show();
    }
}

package com.erthenticate.app.erthenticate;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class PassLayout extends LinearLayout {

    public PassLayout(Context context) {
        super(context);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        Log.d("SERVICE", "Generic");
        return false;
    }
}

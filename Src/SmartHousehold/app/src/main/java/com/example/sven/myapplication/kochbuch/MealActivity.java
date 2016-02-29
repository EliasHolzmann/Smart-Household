package com.example.sven.myapplication.kochbuch;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import com.example.sven.myapplication.R;

public class MealActivity extends AppCompatActivity {
    private String ARG_CHRONO_BASE_OR_CURRENT_VALUE = "ChronometerBase";
    private String ARG_CHRONO_RUNNING = "ChronometerRunning";
    private boolean isChronometerRunning = false;
    private long chronometerCountMillis = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        final Chronometer timer = ((Chronometer) findViewById(R.id.MealActivityTimer));

        if (savedInstanceState != null) {
            long chronoBase = savedInstanceState.getLong(ARG_CHRONO_BASE_OR_CURRENT_VALUE);
            boolean chronoRunning = savedInstanceState.getBoolean(ARG_CHRONO_RUNNING);
            if (chronoRunning) {
                timer.setBase(chronoBase);
                timer.start();
                ((Button) findViewById(R.id.MealActivityStartStopTimer)).setText("Stop");
                isChronometerRunning = true;
            } else {
                chronometerCountMillis = chronoBase;
                timer.setBase(SystemClock.elapsedRealtime() - chronometerCountMillis);
            }
        }

        ((Button) findViewById(R.id.MealActivityStartStopTimer)).setOnClickListener(new View.OnClickListener() {
            @Override
            synchronized public void onClick(View view) {
                if (isChronometerRunning) {
                    chronometerCountMillis = SystemClock.elapsedRealtime() - timer.getBase();
                    timer.stop();
                    ((Button) view).setText("Start");
                    isChronometerRunning = false;
                } else {
                    timer.setBase(SystemClock.elapsedRealtime() - chronometerCountMillis);
                    timer.start();
                    ((Button) view).setText("Stop");
                    isChronometerRunning = true;
                }
            }
        });

        ((Button) findViewById(R.id.MealActivityResetTimer)).setOnClickListener(new View.OnClickListener() {
            @Override
            synchronized public void onClick(View view) {
                chronometerCountMillis = 0;
                timer.setBase(SystemClock.elapsedRealtime());
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.v("SvensApplication", "onSaveInstanceState");
        super.onSaveInstanceState(outState);
        outState.putLong(ARG_CHRONO_BASE_OR_CURRENT_VALUE, ((Chronometer) findViewById(R.id.MealActivityTimer)).getBase());

        outState.putBoolean(ARG_CHRONO_RUNNING, isChronometerRunning);
    }
}

package com.example.sven.myapplication.kochbuch;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sven.myapplication.R;
import com.example.sven.myapplication.kochbuch.model.Database;
import com.example.sven.myapplication.kochbuch.model.Meal;
import com.example.sven.myapplication.kochbuch.model.Step;

public class CookingActivity extends AppCompatActivity {
    public static final String EXTRA_MEAL_ID = "EXTRA_MEAL_ID";
    private String ARG_CHRONO_BASE_OR_CURRENT_VALUE = "ChronometerBase";
    private String ARG_CHRONO_RUNNING = "ChronometerRunning";
    private boolean isChronometerRunning = false;
    private long chronometerCountMillis = 0;

    private int stepId;
    private Step steps[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking);

        int mealId = getIntent().getIntExtra(EXTRA_MEAL_ID, -1);
        if (mealId == -1) {
            throw new RuntimeException("CookingActivity must be started with extra EXTRA_MEAL_ID");
        }

        Meal meal = Database.getInstance().getMeal(mealId);
        setTitle(meal.getName());
        steps = meal.getSteps();

        if (steps.length == 0) {
            Toast.makeText(getApplicationContext(), "Keine Schritte konfiguriert", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        stepId = 0;
        loadStep();

        ((Button) findViewById(R.id.MealActivityNext)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stepId >= steps.length - 1) {
                    Toast.makeText(getApplicationContext(), "Dies ist der letzte Schritt", Toast.LENGTH_SHORT).show();
                    return;
                }
                stepId++;
                loadStep();
            }
        });

        ((Button) findViewById(R.id.MealActivityBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stepId <= 0) {
                    Toast.makeText(getApplicationContext(), "Dies ist der erste Schritt", Toast.LENGTH_SHORT).show();
                    return;
                }
                stepId--;
                loadStep();
            }
        });

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

    private void loadStep() {
        Step step = steps[stepId];

        ((TextView) findViewById(R.id.MealActivityHeading)).setText(step.title);
        ((TextView) findViewById(R.id.MealActivityDescription)).setText(step.description);
        ((TextView) findViewById(R.id.MealActivityTimeSeconds)).setText(step.getTimeSecondsString());
        findViewById(R.id.MealActivityTimeLinearLayout).setVisibility(step.lengthSeconds == -1 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.v("SvensApplication", "onSaveInstanceState");
        super.onSaveInstanceState(outState);
        outState.putLong(ARG_CHRONO_BASE_OR_CURRENT_VALUE, ((Chronometer) findViewById(R.id.MealActivityTimer)).getBase());

        outState.putBoolean(ARG_CHRONO_RUNNING, isChronometerRunning);
    }
}
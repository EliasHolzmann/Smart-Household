package com.example.sven.myapplication.kochbuch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.sven.myapplication.R;
import com.example.sven.myapplication.kochbuch.model.Step;

public class NewStep extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_step);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        findViewById(R.id.newStepSaveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean timerEnabled = ((CheckBox) findViewById(R.id.newStepTimerCheckbox)).isChecked();
                String secondsStr = ((EditText) findViewById(R.id.newStepSeconds)).getText().toString();
                String minutesStr = ((EditText) findViewById(R.id.newStepMinutes)).getText().toString();
                String title = ((EditText) findViewById(R.id.newStepTitle)).getText().toString();
                String description = ((EditText) findViewById(R.id.newStepDescription)).getText().toString();

                if (secondsStr.length() == 0) {
                    secondsStr = "0";
                }
                if (minutesStr.length() == 0) {
                    minutesStr = "0";
                }

                int seconds = Integer.parseInt(secondsStr);
                int minutes = Integer.parseInt(minutesStr);


                Step step;

                if (timerEnabled) {
                    step = new Step(title, description, 60 * minutes + seconds);
                } else {
                    step = new Step(title, description);
                }

                Intent intent = new Intent();

                intent.putExtra(NewReceipt.EXTRA_STEP, step);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

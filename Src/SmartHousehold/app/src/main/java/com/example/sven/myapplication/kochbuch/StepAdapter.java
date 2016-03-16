package com.example.sven.myapplication.kochbuch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sven.myapplication.R;
import com.example.sven.myapplication.kochbuch.model.Step;

import java.util.List;

/**
 * This ArrayAdapter offers an Adapter for usage in ListViews with a item view developed specifically for steps.
 */
public class StepAdapter extends ArrayAdapter<Step> {
    public StepAdapter(Context context, Step[] meal) {
        super(context, 0, meal);
    }

    public StepAdapter(Context context, List<Step> meal) {
        super(context, 0, meal);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Step step = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.step_list_item, parent, false);
        }
        // Lookup view for data population
        TextView stepName = (TextView) convertView.findViewById(R.id.stepListName);
        TextView stepLength = (TextView) convertView.findViewById(R.id.stepListLength);
        // Populate the data into the template view using the data object
        stepName.setText(step.title);
        String minutes = "" + step.lengthSeconds / 60;
        String seconds = "" + step.lengthSeconds % 60;
        if (seconds.length() == 1) {
            seconds = "0" + seconds;
        }
        stepLength.setText(step.lengthSeconds == -1 ? "" : (minutes + ":" + seconds));
        // Return the completed view to render on screen
        return convertView;
    }

}

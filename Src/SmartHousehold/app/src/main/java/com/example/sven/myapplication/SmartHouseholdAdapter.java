package com.example.sven.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by User on 28.02.2016.
 */
public class SmartHouseholdAdapter extends CursorAdapter {

    private SmartHouseholdOpenHandler openHandler;
    private LayoutInflater inflator;

    public SmartHouseholdAdapter(Context context){
        super(context, null, 0);
        inflator = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflator.inflate(R.layout.listen, null);
    }

    @Override
    public void bindView(final View view, final Context context, final Cursor cursor) {

        int ciBezeichnung = cursor.getColumnIndex(SmartHouseholdOpenHandler.BEZEICHNUNG);
        String Bez = cursor.getString(ciBezeichnung);

        TextView textViewBezeichnung = (TextView) view.findViewById(R.id.textView10);
        textViewBezeichnung.setText(Bez);

        int ciID = cursor.getColumnIndex(SmartHouseholdOpenHandler._ID);
        final String ID = cursor.getString(ciID);
        textViewBezeichnung.setText(ID);

        Button loeschen = (Button) view.findViewById(R.id.button7);

        loeschen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openHandler = new SmartHouseholdOpenHandler(context);
                openHandler.onDeleteTableRow(ID);

            }
        });

    }
}

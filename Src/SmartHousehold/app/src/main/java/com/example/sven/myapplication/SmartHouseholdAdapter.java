package com.example.sven.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by User on 28.02.2016.
 */
public class SmartHouseholdAdapter extends CursorAdapter {

    /*
    Exakt die selbe Funktionalität wie in der EinkaufslisteAdapter.class
     */

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
        final String Bez = cursor.getString(ciBezeichnung);

        TextView textViewBezeichnung = (TextView) view.findViewById(R.id.textView10);
        textViewBezeichnung.setText(Bez);

        int ciID = cursor.getColumnIndex(SmartHouseholdOpenHandler._ID);
        final String ID = cursor.getString(ciID);

        Button loeschen = (Button) view.findViewById(R.id.button7);

        loeschen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openHandler = new SmartHouseholdOpenHandler(context);
                openHandler.onDeleteTableRow(ID);

                view.setVisibility(View.INVISIBLE);

                Toast.makeText(context, "Einkaufsliste " + Bez + " wurde erfolgreich gelöscht.", Toast.LENGTH_LONG).show();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openHandler = new SmartHouseholdOpenHandler(context);

                Intent myIntent = new Intent(context, Wocheneinkauf.class);
                myIntent.putExtra("LISTENID", Long.parseLong(ID));
                myIntent.putExtra("BEZEICHNUNG", Bez);
                context.startActivity(myIntent);

            }
        });

    }
}

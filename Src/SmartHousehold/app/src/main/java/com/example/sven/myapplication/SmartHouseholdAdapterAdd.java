package com.example.sven.myapplication;

import android.app.Activity;
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

import com.example.sven.myapplication.kochbuch.model.Ingredient;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 28.02.2016.
 */
public class SmartHouseholdAdapterAdd extends CursorAdapter {

    /*
    Exakt die selbe Funktionalität wie in der EinkaufslisteAdapter.class
     */

    private EinkaufslisteOpenHandler openHandlerEL;
    private SmartHouseholdOpenHandler openHandler;
    private LayoutInflater inflator;
    public List<Ingredient> list;
    public int count = 0;

    public SmartHouseholdAdapterAdd(Context context, List<Ingredient> Zutaten){
        super(context, null, 0);
        inflator = LayoutInflater.from(context);
        list = Zutaten;
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

        final int ciID = cursor.getColumnIndex(SmartHouseholdOpenHandler._ID);
        final String ID = cursor.getString(ciID);

        Button loeschen = (Button) view.findViewById(R.id.button7);
        loeschen.setVisibility(View.INVISIBLE);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openHandlerEL = new EinkaufslisteOpenHandler(context);


                for (Object s : list) {
                    openHandlerEL.insert(Long.parseLong(ID), list.get(count).name.toString(), String.valueOf(list.get(count).amount), list.get(count).amountType, String.valueOf(((float) list.get(count).price)/100), "", list.get(count).priceType);
                    count++;
                }

                Toast.makeText(context, "Die Zutaten wurden der Einkaufsliste hinzugefügt.", Toast.LENGTH_LONG).show();

                ((Activity) context).finish();
            }
        });

    }
}

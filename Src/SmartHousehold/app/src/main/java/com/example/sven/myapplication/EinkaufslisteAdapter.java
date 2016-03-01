package com.example.sven.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Sven on 29.02.2016.
 */
public class EinkaufslisteAdapter extends CursorAdapter {

    private EinkaufslisteOpenHandler openHandler;
    private LayoutInflater inflator;

    /*
    Ein Inflator wird erzeugt, welcher das layout "zutaten" enthält und je nach Anzahl der Datensätze
    dementsprechend oft das Layout dupliziert
     */

    public EinkaufslisteAdapter(Context context){
        super(context, null, 0);
        inflator = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
         return inflator.inflate(R.layout.zutaten, null);
    }

    @Override
    public void bindView(final View view, final Context context, Cursor cursor) {

        /*
        Die View in der Einkaufsliste für die einzelnen Zutaten wird mit Daten befüllt
         */

        int ciID = cursor.getColumnIndex(EinkaufslisteOpenHandler._ID);
        final String ID = cursor.getString(ciID);

        int ciListenID = cursor.getColumnIndex(EinkaufslisteOpenHandler.LISTENID);
        final String ListenID = cursor.getString(ciListenID);

        int ciBezeichnung = cursor.getColumnIndex(EinkaufslisteOpenHandler.BEZEICHNUNG);
        final String Bezeichnung = cursor.getString(ciBezeichnung);

        int ciMenge = cursor.getColumnIndex(EinkaufslisteOpenHandler.MENGE);
        final String Menge = cursor.getString(ciMenge);

        int ciEinheit = cursor.getColumnIndex(EinkaufslisteOpenHandler.EINHEIT);
        final String Einheit = cursor.getString(ciEinheit);

        int ciPreis = cursor.getColumnIndex(EinkaufslisteOpenHandler.PREIS);
        final String Preis = cursor.getString(ciPreis);

        int ciPreiseinheit = cursor.getColumnIndex(EinkaufslisteOpenHandler.PREISEINHEIT);
        final String Preiseinheit = cursor.getString(ciPreiseinheit);

        int ciImWagen = cursor.getColumnIndex(EinkaufslisteOpenHandler.IMWAGEN);
        final String ImWagen = cursor.getString(ciImWagen);

        TextView tvBezeichnung = (TextView) view.findViewById(R.id.textView11);
        TextView tvMengeEinheit = (TextView) view.findViewById(R.id.textView2);
        TextView tvPreis = (TextView) view.findViewById(R.id.textView3);

        tvBezeichnung.setText(Bezeichnung);
        tvMengeEinheit.setText(Menge + " " + Einheit);
        tvPreis.setText(Preis + " €/" + Preiseinheit);

        Button loeschen = (Button) view.findViewById(R.id.button8);

        /*
        Mit dem Löschen-Button können Zutaten aus der Einkaufsliste entfernt werden. Außerdem wird eine Erfolgsmeldung ausgegeben.
         */

        loeschen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openHandler = new EinkaufslisteOpenHandler(context);
                openHandler.onDeleteTableRow(ID);

                view.setVisibility(View.INVISIBLE);

                Toast.makeText(context, "Zutat wurde erfolgreich entfernt.", Toast.LENGTH_LONG).show();

            }
        });
    }
}

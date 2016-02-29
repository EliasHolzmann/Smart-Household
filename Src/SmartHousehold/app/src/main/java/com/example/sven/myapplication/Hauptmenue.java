package com.example.sven.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sven.myapplication.kochbuch.Kochbuch;
import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;

import java.net.MalformedURLException;
import java.util.List;

public class Hauptmenue extends AppCompatActivity {

    private MobileServiceClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hauptmenue);

        try {
            mClient = new MobileServiceClient(
                    "https://smarthousehold.azurewebsites.net",
                    this
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        TodoItem item = new TodoItem();
        item.Text = "Awesome item";

        final TextView tv = (TextView) findViewById(R.id.textView7);

        mClient.getTable(TodoItem.class).insert(item, new TableOperationCallback<TodoItem>() {
            public void onCompleted(TodoItem entity, Exception exception, ServiceFilterResponse response) {
                if (exception == null) {
                    tv.setText("Erfolgreich");
                } else {
                    tv.setText("Fehlgeschlagen");
                }
            }
        });

        final TextView tv2 = (TextView) findViewById(R.id.textView9);

        try {
            mClient.getTable(TodoItem.class).execute(new TableQueryCallback<TodoItem>() {
                @Override
                public void onCompleted(List<TodoItem> result, int count, Exception exception, ServiceFilterResponse response) {
                    tv2.setText(result.get(1).Text);
                }
            });
        } catch (MobileServiceException e) {
            tv2.setText("Fehlgeschlagen");
        }

        Button einkaufsliste_button = (Button) findViewById(R.id.button5);

        einkaufsliste_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(Hauptmenue.this, Einkaufsliste_Hauptscreen.class);
                Hauptmenue.this.startActivity(myIntent);

            }
        });

        Button kochbuch_button = (Button) findViewById(R.id.button6);

        kochbuch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(Hauptmenue.this, Kochbuch.class);
                Hauptmenue.this.startActivity(myIntent);

            }
        });

    }

}

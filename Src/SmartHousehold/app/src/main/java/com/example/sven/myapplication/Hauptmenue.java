package com.example.sven.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import org.w3c.dom.Text;

import java.net.MalformedURLException;

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

    }
}

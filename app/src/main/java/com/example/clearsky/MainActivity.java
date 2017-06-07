package com.example.clearsky;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Firebase.set
        // Write a message to the database
        //DB

        Person person = new Person();
        //dding values
        person.setName("tzion");
        person.setAddress("hadad");

        ArrayList childs = new ArrayList<String>();
        childs.add("users");
        childs.add("15");
        //Storing values to firebase
        DbProvider.getInstance().write(childs, person);
        DbProvider.getInstance().read();

        childs = new ArrayList<String>();
        childs.add("users");
        childs.add("16");
        //Storing values to firebase
        DbProvider.getInstance().write(childs, person);

    }
}

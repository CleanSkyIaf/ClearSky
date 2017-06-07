package com.example.clearsky;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by ofek13 on 07/06/2017.
 */

public class DbProvider {
    protected static DbProvider dbProvider = null;
    protected static FirebaseDatabase database = FirebaseDatabase.getInstance();
    protected static DatabaseReference myRef = database.getReference();

    public static DbProvider getInstance() {

        if (dbProvider == null) {
            dbProvider = new DbProvider();
        }

        return dbProvider;
    }

    public void write(ArrayList<String> childs, Object obj){
        DatabaseReference currRef = database.getReference();

        for (String child : childs) {
            currRef = currRef.child(child);
        }

        currRef.setValue(obj);
    }

    public void read() {
        // Read from the database
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Person personsObject = dataSnapshot.child("users").child("12").getValue(Person.class);

//                GenericTypeIndicator<List<Person>> t = new GenericTypeIndicator<List<Person>>() {};
//                Object personsObj = dataSnapshot.child("users").getValue(t);
//                List<Person> persons = dataSnapshot.child("users").getValue(t);
             //   Person [] persons = dataSnapshot.child("users").getValue(Person.class);
//                for(Person person : persons ) {
//                    Log.d(TAG, "Value is: " + person.getName());
//                }

            }


            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}

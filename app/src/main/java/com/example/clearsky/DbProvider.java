package com.example.clearsky;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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

    public static void write(ArrayList<String> childs, Object obj){
        DatabaseReference currRef = database.getReference();
        //currRef.setValue("reports");
        for (String child : childs) {
            currRef = currRef.child(child).push();
        }
        currRef.setValue(obj);
    }

    public void readAllReports(final ArrayList<String> reports, final ArrayAdapter<String> arrayAdapterReports) {
        // Read from the database

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                ArrayList<Report> tempReports = new ArrayList<>();

                for (DataSnapshot postSnapshot: dataSnapshot.child("reports").getChildren()) {
                    tempReports.add(postSnapshot.getValue(Report.class));
                }

                reports.add("סוג");
                reports.add("מיקום");
                reports.add("כיוון");
                reports.add("תאריך");
                reports.add("כמות");

                for (Report report : tempReports) {
                   report.addToList(reports);
                }

                arrayAdapterReports.notifyDataSetChanged();
            }


            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
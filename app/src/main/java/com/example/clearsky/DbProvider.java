package com.example.clearsky;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by ofek13 on 07/06/2017.
 */

public class DbProvider {
    protected static DbProvider dbProvider = null;
    protected static FirebaseDatabase _database = FirebaseDatabase.getInstance();
    protected static DatabaseReference _myRef = _database.getReference();

    public static DbProvider getInstance() {

        if (dbProvider == null) {
            dbProvider = new DbProvider();
        }

        return dbProvider;
    }

    public static DatabaseReference getRef() {
        return _database.getReference();
    }

    public static void writeValue(ArrayList<String> childs, Object value){
        DatabaseReference currRef = _database.getReference();

        for (String child : childs) {
            currRef = currRef.child(child).push();
        }

        currRef.setValue(value);
    }

    public void readAllReports(final ArrayList<String> reports, final ArrayAdapter<String> arrayAdapterReports) {
        // Read from the _database

        _myRef.addListenerForSingleValueEvent(new ValueEventListener() {

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

    public void readAllUsers(final ArrayList<String> usersList, final ArrayAdapter<String> arrayAdapterUsers) {
        _myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                ArrayList<String> tempUsers = new ArrayList<>();

                for (DataSnapshot postSnapshot: dataSnapshot.child("users").getChildren()) {

                    String currUserName = postSnapshot.child("userName").getValue().toString();

                    if (!currUserName.equals(User.getUserName())) {
                        tempUsers.add(currUserName);
                    }
                }

                for (String user : tempUsers) {
                    usersList.add(user);
                }

                arrayAdapterUsers.notifyDataSetChanged();
            }


            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
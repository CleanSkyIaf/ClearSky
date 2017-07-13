package com.example.clearsky;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class UsersListActivity extends AppCompatActivity {

    private ArrayList<String> usersList = new ArrayList<>();
    private ListView usersListView;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        usersListView = (ListView) findViewById(R.id.usersListListView);

        final ArrayAdapter<String> arrayAdapterUsers =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usersList);

        usersListView.setAdapter(arrayAdapterUsers);

        DbProvider.getInstance().readAllUsers(usersList, arrayAdapterUsers);

        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Dialog signInDialog = new Dialog(context);
                signInDialog.setContentView(R.layout.dialog_user_details);
                signInDialog.setTitle("פרטי משתמש");

                final String userNameChose = (String) parent.getItemAtPosition(position);

                final TextView userFirstName = (TextView)signInDialog.findViewById(R.id.firstNameUserDetailsText);
                final TextView userLastName = (TextView)signInDialog.findViewById(R.id.lastNameUserDetailsText);
                final TextView userUserName = (TextView)signInDialog.findViewById(R.id.userNameUserDetailsText);
                Button deleteUserButton = (Button)signInDialog.findViewById(R.id.deleteUserButton);
                final Button acceptUserButton = (Button)signInDialog.findViewById(R.id.acceptUserButton);

                DatabaseReference myFirebaseRef = DbProvider.getRef();
                final DatabaseReference ref;
                ref = myFirebaseRef.child("users");

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        DataSnapshot userNameDetails = snapshot.child(userNameChose);
                        if (!(Boolean)userNameDetails.child("isAcceptedUser").getValue()) {
                            acceptUserButton.setVisibility(View.VISIBLE);
                        }

                        userFirstName.setText(userNameDetails.child("firstName").getValue().toString());
                        userLastName.setText(userNameDetails.child("lastName").getValue().toString());
                        userUserName.setText(userNameChose);


                    }
                    public void onCancelled(DatabaseError arg0) {
                        // TODO Auto-generated method stub

                    }
                });

                deleteUserButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        DatabaseReference myFirebaseRef = DbProvider.getRef();
                        final DatabaseReference ref;
                        ref = myFirebaseRef.child("users");

                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {

                                ref.child(userUserName.getText().toString()).removeValue();


                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                        context);
                                alertDialogBuilder.setTitle("מחיקת משתמש");
                                alertDialogBuilder.setMessage("המשתמש נמחק בהצלחה").setCancelable(false)
                                        .setPositiveButton("אישור",new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }
                            public void onCancelled(DatabaseError arg0) {
                                // TODO Auto-generated method stub

                            }
                        });
                    }
                });

                acceptUserButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        DatabaseReference myFirebaseRef = DbProvider.getRef();
                        final DatabaseReference ref;
                        ref = myFirebaseRef.child("users");

                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {

                                ref.child(userNameChose).child("isAcceptedUser").setValue(true);


                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                        context);
                                alertDialogBuilder.setTitle("אישור משתמש");
                                alertDialogBuilder.setMessage("המשתמש אושר בהצלחה").setCancelable(false)
                                        .setPositiveButton("אישור",new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }
                            public void onCancelled(DatabaseError arg0) {
                                // TODO Auto-generated method stub

                            }
                        });
                    }
                });

                signInDialog.show();
            }
        });
    }
}

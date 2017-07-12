package com.example.clearsky;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends Activity {

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        final Context context = this;

        checkInternetCon(context);

        Button signIn = (Button) findViewById(R.id.btconnect3);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatabaseReference myFirebaseRef = DbProvider.getRef();
                DatabaseReference ref;
                final TextView userName = (TextView) findViewById(R.id.tfuserName);
                final TextView password = (TextView) findViewById(R.id.tfpassword);

                ref = myFirebaseRef.child("users");

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        final String userNameString = userName.getText().toString().trim();
                        final String passwordString = password.getText().toString();

                        if (isCorrectUser(snapshot, userNameString, passwordString)) {

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    context);
                            alertDialogBuilder.setTitle("התחברות");
                            alertDialogBuilder.setMessage("התחברת בהצלחה").setCancelable(false)
                                    .setPositiveButton("אישור", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //Set static user details
                                            User.setUserName(userNameString);

                                            LoginPage.this.finish();
                                            Intent myIntent = new Intent(LoginPage.this, MainActivity.class);
                                            LoginPage.this.startActivity(myIntent);
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                        else {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    context);
                            alertDialogBuilder.setTitle("הפרטים שהוזנו שגויים");
                            alertDialogBuilder.setMessage("שם משתמש או סיסמה שגויים, אנא נסה שוב").setCancelable(false)
                                    .setPositiveButton("אישור",new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError arg0) {
                        // TODO Auto-generated method stub

                    }
                });
            }
        });
    }

    private boolean isCorrectUser(DataSnapshot snapshot, String userNameString, String passwordString) {
        return snapshot.child(userNameString).exists() &&
                snapshot.child(userNameString).getValue().toString().equals(passwordString);
    }

    private void checkInternetCon(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if( activeNetworkInfo == null) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);
            alertDialogBuilder.setTitle("אין חיבור");
            alertDialogBuilder.setMessage("האפליקציה דורשת חיבור אינטרנט, ולפלאפון אין חיבור זמין כרגע").setCancelable(false)
                    .setPositiveButton("יציאה",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                            finish();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
}
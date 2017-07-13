package com.example.clearsky;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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

        Button newUser = (Button) findViewById(R.id.newUserBUtton);
        Button signIn = (Button) findViewById(R.id.btconnect);

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
                    public void onDataChange(final DataSnapshot snapshot) {
                        final String userNameString = userName.getText().toString().trim();
                        final String passwordString = password.getText().toString();

                        if (isCorrectUser(snapshot, userNameString, passwordString)) {
                            if ((Boolean)snapshot.child(userNameString).child("isAcceptedUser").getValue()) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                        context);
                                alertDialogBuilder.setTitle("התחברות");
                                alertDialogBuilder.setMessage("התחברת בהצלחה").setCancelable(false)
                                        .setPositiveButton("אישור", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //Set static user details
                                                User.setUserName(userNameString);
                                                User.set_isAdmin((Boolean)snapshot.child(userNameString).child("isAdmin").getValue());

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
                                alertDialogBuilder.setTitle("המשתמש לא אושר");
                                alertDialogBuilder.setMessage("המשתמש לא אושר עדיין אנא המתן לאישור").setCancelable(false)
                                        .setPositiveButton("אישור", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }
                        } else {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    context);
                            alertDialogBuilder.setTitle("הפרטים שהוזנו שגויים");
                            alertDialogBuilder.setMessage("שם משתמש או סיסמה שגויים, אנא נסה שוב").setCancelable(false)
                                    .setPositiveButton("אישור", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
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

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog signInDialog = new Dialog(context);
                signInDialog.setContentView(R.layout.dialog_sign_up);
                signInDialog.setTitle("משתמש חדש");

                final TextView newUserFirstName = (TextView)signInDialog.findViewById(R.id.newUserFirstNameText);
                final TextView newUserLastName = (TextView)signInDialog.findViewById(R.id.newUserLastNameText);
                final TextView newUserUserName = (TextView)signInDialog.findViewById(R.id.newUserUserNameText);
                final TextView newUserPassword = (TextView)signInDialog.findViewById(R.id.newUserPasswordText);
                Button signUpButton = (Button)signInDialog.findViewById(R.id.newUserSignUpButton);

                signUpButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        DatabaseReference myFirebaseRef = DbProvider.getRef();
                        final DatabaseReference ref;
                        ref = myFirebaseRef.child("users");

                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {

                                final String userNameString = newUserUserName.getText().toString().trim();
                                final String passwordString = newUserPassword.getText().toString();

                                if(snapshot.child(userNameString).exists()){

                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                            context);
                                    alertDialogBuilder.setTitle("שם משתמש קיים");
                                    alertDialogBuilder.setMessage("שם המשתמש כבר קיים, אנא נסה שם אחר").setCancelable(false)
                                            .setPositiveButton("אישור", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });

                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();
                                }
                                else {

                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                            context);
                                    alertDialogBuilder.setTitle("הרשמה");
                                    alertDialogBuilder.setMessage("נרשמת בהצלחה, אנא המתן לאישור המשתמש").setCancelable(false)
                                            .setPositiveButton("אישור", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                    String firstNameString = newUserFirstName.getText().toString();
                                                    String lastNameString = newUserLastName.getText().toString();

                                                    ref.child(userNameString).setValue( new newUser(
                                                            userNameString,
                                                            passwordString,
                                                            firstNameString,
                                                            lastNameString));

                                                    LoginPage.this.finish();
                                                }
                                            });
                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();

                                }
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

    private boolean isCorrectUser(DataSnapshot snapshot, String userNameString, String passwordString) {
        return snapshot.child(userNameString).exists() &&
                snapshot.child(userNameString).child("password").getValue().toString().equals(passwordString);
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
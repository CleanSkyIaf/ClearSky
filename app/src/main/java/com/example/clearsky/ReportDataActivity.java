package com.example.clearsky;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by tsion on 07/06/2017.
 */

public class ReportDataActivity extends AppCompatActivity {
    public static long uniqeId;
    private Report report;
    private Button sendButton;
    private ImageButton locationButton;
    private LocationManager mLocationManager;
    Context context = this;
    private double longitude = 0;
    private double latitude = 0;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private Long timeInMills=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_page);

        final EditText datePicker = (EditText) findViewById(R.id.dateText);
        final Spinner birdType = (Spinner) findViewById(R.id.spinner_bird_type);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);

                dialog.setContentView(R.layout.dialog_date);
                dialog.setTitle("שעון");

                final TimePicker tp = (TimePicker) dialog.findViewById(R.id.timePicker1);
                Button sendButtond = (Button) dialog.findViewById(R.id.buttonOK);
                sendButtond.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Date date = Calendar.getInstance().getTime();
                        String dateString = (new SimpleDateFormat("dd/MM/YY")).format(date) + " "
                                + String.format("%02d", tp.getCurrentHour()) + ":" + String.format("%02d", tp.getCurrentMinute());
                        datePicker.setText(dateString);
                        date.setHours(tp.getCurrentHour());
                        date.setMinutes(tp.getCurrentMinute());
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        timeInMills = cal.getTimeInMillis();
                        if (timeInMills > Calendar.getInstance().getTimeInMillis()) {

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    context);
                            TextView title = new TextView(context);
                            title.setText("שעה עתידית");
                            title.setPadding(10, 10, 10, 10);
                            title.setGravity(Gravity.CENTER);
                            title.setTextSize(20);

                            alertDialogBuilder.setCustomTitle(title);
                            alertDialogBuilder.setMessage("השעה שהוכנסה לא חוקית. אנא תקן השעה").setCancelable(false)
                                    .setPositiveButton("אישור", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();


                        } else
                            dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });


        sendButton = (Button) findViewById(R.id.sendReport);
        locationButton = (ImageButton) findViewById(R.id.locationButton);

        //////location

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
                List<String> providers = mLocationManager.getProviders(true);
                Location bestLocation = null;
                for (String provider : providers) {
                    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions,

//
//                        ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION   },
//                                MY_PERMISSIONS_REQUEST_LOCATION  );

                        // and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        //return TODO;
                    }
                    Location l = mLocationManager.getLastKnownLocation(provider);
                    if (l == null) {
                        continue;
                    }
                    if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                        // Found best last known location: %s", l);
                        bestLocation = l;
                    }
                }

                if (bestLocation != null) {
                    longitude = bestLocation.getLongitude();
                    latitude = bestLocation.getLatitude();
                }

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                alertDialogBuilder.setTitle("coordinates");
                alertDialogBuilder.setMessage("the lat is  " + latitude + "long:  " + longitude).setCancelable(true);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                EditText textout = (EditText) findViewById(R.id.locationText); // need to be check
                textout.setText(latitude + ", " + longitude);

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmpType;
                String tmpLocation;
                String tmpDirection;
                long tmpDate;
                String tmpNum;
                String tmpHeight;


                Spinner editText1 = (Spinner) findViewById(R.id.spinner_bird_type);
//                TextView textView = (TextView)mySpinner.getSelectedView();
//                String result = textView.getText().toString();
                EditText editText2 = (EditText) findViewById(R.id.locationText);
                Spinner editText3 = (Spinner) findViewById(R.id.spinner_direction);
                EditText editText4 = (EditText) findViewById(R.id.height_text);
                Spinner editText5 = (Spinner) findViewById(R.id.spinner_birds_amount);

                tmpType = editText1.getSelectedItem().toString();
                tmpLocation = editText2.getText().toString();
                tmpDirection = editText3.getSelectedItem().toString();
                tmpHeight = editText4.getText().toString();
                tmpNum = editText5.getSelectedItem().toString();

                if(tmpType.isEmpty() || tmpLocation.isEmpty() ||tmpDirection.isEmpty() || tmpHeight.isEmpty() || tmpNum.isEmpty() || timeInMills==null ){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);
                    alertDialogBuilder.setMessage("ישנם שדות שאינם מלאים").setCancelable(false)
                            .setPositiveButton("אישור",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                else {
                    ArrayList<String> array = new ArrayList<String>();
                    array.add("reports");
                    report = new Report(tmpType, tmpLocation, tmpDirection, timeInMills, tmpNum, User.getUserName(), tmpHeight);
                    DbProvider.writeValue(array, report);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);
                    TextView title = new TextView(context);
                    title.setText("שליחת דיווח");
                    title.setPadding(10, 10, 10, 10);
                    title.setGravity(Gravity.CENTER);
                    title.setTextSize(20);

                    alertDialogBuilder.setCustomTitle(title);
                    alertDialogBuilder.setMessage("הדיווח נשלח בהצלחה").setCancelable(false)
                            .setPositiveButton("אישור", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    finish();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

//                AlertDialog.Builder hiB = new AlertDialog.Builder(context);
//                hiB.setTitle("sent successfully");
//                //hiB.setMessage("go to sleep");
//                hiB.create().show();
                }
            }
        });

    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission. ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission. ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("we need permissions")
                        .setMessage("we realy need")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(ReportDataActivity.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                           android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:

                        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, (android.location.LocationListener) this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

}

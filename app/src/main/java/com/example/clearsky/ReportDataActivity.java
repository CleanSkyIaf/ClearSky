package com.example.clearsky;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tsion on 07/06/2017.
 */

public class ReportDataActivity extends AppCompatActivity {
    public static long uniqeId;
    private Report report;
    private Button sendButton;
    private ImageButton locationButton;
    //private FusedLocationProviderClient mFusedLocationClient;
    Context context = this;
    private double longitude=0;
    private double latitude=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_page);

        sendButton = (Button) findViewById(R.id.sendReport);
        locationButton = (ImageButton) findViewById(R.id.locationButton);

        //////location

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



           // LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);


            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                alertDialogBuilder.setTitle("permission problem-turn on location services on your device");
                //alertDialogBuilder.setMessage("nothing :)").setCancelable(true);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                return;
            }

            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                }
            };

           // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, (android.location.LocationListener) locationListener);


            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }
            else{

                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                }
            }


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);
            alertDialogBuilder.setTitle("coordinates");
            alertDialogBuilder.setMessage("the lat is  " +latitude+ "long:  "+ longitude).setCancelable(true);

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }});

        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String tmpType;
                String tmpLocation;
                String tmpDirection;
                String tmpDate;
                String tmpNum;

                EditText editText1 = (EditText) findViewById(R.id.birdTypeText);
                EditText editText2 = (EditText) findViewById(R.id.locationText);
                EditText editText3 = (EditText) findViewById(R.id.directionText);
                EditText editText4 = (EditText) findViewById(R.id.dateText);
                EditText editText5 = (EditText) findViewById(R.id.amountText);

                tmpType = editText1.getText().toString();
                tmpLocation = editText2.getText().toString();
                tmpDirection = editText3.getText().toString();
                tmpDate = editText4.getText().toString();
                tmpNum = editText5.getText().toString();
                ArrayList<String> array = new ArrayList<String>();

                array.add("reports");
                report = new Report(tmpType,tmpLocation,tmpDirection,tmpDate,Integer.parseInt(tmpNum));
                DbProvider.write(array,report);
                AlertDialog.Builder hiB = new AlertDialog.Builder(context);
                hiB.setTitle("sent successfully");
                //hiB.setMessage("go to sleep");
                hiB.create().show();
            }
        });

    }
}

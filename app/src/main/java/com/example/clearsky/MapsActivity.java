package com.example.clearsky;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ArrayList<Report> allReports = new ArrayList<>();

    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        readReportData();

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                Report currReport = (Report)marker.getTag();

                LinearLayout info = new LinearLayout(context);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView desc = new TextView(context);
                desc.setTextColor(Color.BLACK);
                desc.setGravity(Gravity.CENTER);
                desc.setTypeface(null, Typeface.BOLD);
                desc.setText("פרטים:");
                desc.setBackgroundColor(Color.WHITE);

                TextView birdAzimuth = new TextView(context);
                birdAzimuth.setTextColor(Color.GRAY);
                birdAzimuth.setGravity(Gravity.CENTER);
                birdAzimuth.setText("כיוון: " + currReport.getBirdAzimuth());
                birdAzimuth.setBackgroundColor(Color.WHITE);

                TextView birdCount = new TextView(context);
                birdCount.setTextColor(Color.GRAY);
                birdCount.setGravity(Gravity.CENTER);
                birdCount.setText("כמות: " + currReport.getBirdCount());
                birdCount.setBackgroundColor(Color.WHITE);

                TextView birdKind = new TextView(context);
                birdKind.setTextColor(Color.GRAY);
                birdKind.setGravity(Gravity.CENTER);
                birdKind.setText("סוג ציפור: " + currReport.getBirdKind());
                birdKind.setBackgroundColor(Color.WHITE);

                TextView birdPlace = new TextView(context);
                birdPlace.setTextColor(Color.GRAY);
                birdPlace.setGravity(Gravity.CENTER);
                birdPlace.setText("מיקום: " + currReport.getBirdPlace());
                birdPlace.setBackgroundColor(Color.WHITE);

                TextView height = new TextView(context);
                height.setTextColor(Color.GRAY);
                height.setGravity(Gravity.CENTER);
                height.setText("גובה: " + currReport.getHeight());
                height.setBackgroundColor(Color.WHITE);

                TextView reporter = new TextView(context);
                reporter.setTextColor(Color.GRAY);
                reporter.setGravity(Gravity.CENTER);
                reporter.setText("מדווח: " + currReport.getReporter());
                reporter.setBackgroundColor(Color.WHITE);

                TextView date = new TextView(context);
                date.setTextColor(Color.GRAY);
                date.setGravity(Gravity.CENTER);
                date.setBackgroundColor(Color.WHITE);
                Calendar.getInstance().setTimeInMillis(currReport.getDate());
                date.setText("שעה: " +  (new SimpleDateFormat("HH:mm")).format(Calendar.getInstance().getTime()));

                info.addView(desc);
                info.addView(birdAzimuth);
                info.addView(birdCount);
                info.addView(birdKind);
                info.addView(birdPlace);
                info.addView(height);
                info.addView(reporter);
                info.addView(date);

                return info;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }});

            // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);

    }

    private void readReportData() {
        DbProvider.getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.child("reports").getChildren()) {
                    Report tempReport = postSnapshot.getValue(Report.class);
                    allReports.add(tempReport);

                    String[] birdPlace = tempReport.getBirdPlace().split(",");
                    if (birdPlace.length == 2) {
                        double v1 = Double.valueOf(birdPlace[0]);
                        double v2 = Double.valueOf(birdPlace[1]);

                        long halfHour = 60*30*1000;
                        int circleColor =R.mipmap.green_circle ;
                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(tempReport.getDate());
                        Date date = cal.getTime();

                     if (cal.getTimeInMillis()+halfHour>Calendar.getInstance().getTimeInMillis()) {
                         circleColor = R.mipmap.green_circle;
                         addMarker(tempReport, v1, v2, circleColor);
                     }
                     else if(cal.getTimeInMillis()+(2*halfHour)>Calendar.getInstance().getTimeInMillis()) {
                         circleColor = R.mipmap.yellow_circle;
                         addMarker(tempReport, v1, v2, circleColor);
                     }
                     else if(cal.getTimeInMillis()+(3*halfHour)>Calendar.getInstance().getTimeInMillis()) {
                         circleColor = R.mipmap.red_circle;
                         addMarker(tempReport, v1, v2, circleColor);
                     }
                    }
                }

            }

            private void addMarker(Report tempReport, double v1, double v2, int circleColor) {
                mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(circleColor))
                        .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                        .position(new LatLng(v1, v2)))
                        .setTag(tempReport);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

//        // Retrieve the data from the marker.
//        Integer clickCount = (Integer) marker.getTag();
//
//        // Check if a click count was set, then display the click count.
//        if (clickCount != null) {
//            clickCount = clickCount + 1;
//            marker.setTag(clickCount);
//            Toast.makeText(this,
//                    marker.getTitle() +
//                            " has been clicked " + clickCount + " times.",
//                    Toast.LENGTH_SHORT).show();
//        }
//
//        // Return false to indicate that we have not consumed the event and that we wish
//        // for the default behavior to occur (which is for the camera to move such that the
//        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }
}

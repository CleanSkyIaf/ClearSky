package com.example.clearsky;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private Button insertButton;
    private Button infoButton;
    private Button chatButton;

    Context context=this;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        insertButton=(Button)findViewById(R.id.newreport) ;
        infoButton=(Button)findViewById(R.id.watchreport) ;
        chatButton=(Button)findViewById(R.id.chat) ;

        insertButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
            //    ReportDataActivity reportDataActivity = new ReportDataActivity();
                Intent intent = new Intent(MainActivity.this, ReportDataActivity.class);
                startActivity(intent);

            }
        });

        infoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent myIntent = new Intent(MainActivity.this,ReportsView.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        chatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, ChatRooms.class);
                startActivity(intent);
            }
        });
    }
}

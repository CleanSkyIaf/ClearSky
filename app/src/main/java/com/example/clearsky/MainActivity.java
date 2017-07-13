package com.example.clearsky;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private Button insertButton;
    private Button infoButton;
    private Button chatButton;
    private Button mapButton;
    private Button manageUsersButton;

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
        mapButton=(Button)findViewById(R.id.map);

        insertButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
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

        mapButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        if (User.getIsAdmin()) {
            manageUsersButton=(Button)findViewById(R.id.manageUsersbutton);
            manageUsersButton.setVisibility(View.VISIBLE);

            manageUsersButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(MainActivity.this, UsersListActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}

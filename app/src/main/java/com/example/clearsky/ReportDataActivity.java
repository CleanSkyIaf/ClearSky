package com.example.clearsky;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by tsion on 07/06/2017.
 */

public class ReportDataActivity extends AppCompatActivity {

    private Button sendButton;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_page);

        sendButton=(Button)findViewById(R.id.sendReport) ;

        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder hiB = new AlertDialog.Builder(context);
                hiB.setTitle("data was sent successfully!");
           //     hiB.setMessage("go to sleep");
                hiB.create().show();
            }
        });
    }

    public void addReport(){


    }
}

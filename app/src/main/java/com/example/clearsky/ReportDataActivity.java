package com.example.clearsky;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by tsion on 07/06/2017.
 */

public class ReportDataActivity extends AppCompatActivity {
    public static long uniqeId;
    private Report report;
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


    public void addReport(){


    }
}

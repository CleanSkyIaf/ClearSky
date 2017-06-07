package com.example.clearsky;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ReportsView extends AppCompatActivity {

    private ArrayList<Report> reportsList = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_view);

        Report r = new Report("yona", "TA", "180", "07/06/17", 300);
        ArrayList<String> childs = new ArrayList<>();
        childs.add("users");
        childs.add("16");
        //Storing values to firebase
        DbProvider.getInstance().write(childs, r);

        listView = (ListView)findViewById(R.id.reportsListView);

        final ArrayAdapter<Report> arrayAdapterReports =
                new ArrayAdapter<Report>(this, android.R.layout.simple_list_item_1, reportsList);

        listView.setAdapter(arrayAdapterReports);

        DbProvider.getInstance().readAllReports(reportsList);
    }
}

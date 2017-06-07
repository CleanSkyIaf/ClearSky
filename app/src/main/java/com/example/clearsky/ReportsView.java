package com.example.clearsky;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

public class ReportsView extends AppCompatActivity {

    private ArrayList<String> reportsList = new ArrayList<>();
    private GridView reportsTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_view);

        reportsTable = (GridView)findViewById(R.id.reportsTable);

        final ArrayAdapter<String> arrayAdapterReports =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, reportsList);

        reportsTable.setAdapter(arrayAdapterReports);

        DbProvider.getInstance().readAllReports(reportsList, arrayAdapterReports);
    }
}

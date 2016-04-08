package com.example.abigailwatson.project3;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class PurchaseActivity extends AppCompatActivity {

    private ToyList purchases;

    ArrayList<String> toyNames = new ArrayList<String>();

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        purchases = getIntent().getExtras().getParcelable("toyList");

        for (int i = 0; i < purchases.getToyList().size(); i++) {
            toyNames.add(purchases.getToyList().get(i).getToyName());
        }

        setContentView(R.layout.activity_purchase);

        lv = (ListView) findViewById(R.id.toys_to_purchase);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                toyNames);

        lv.setAdapter(arrayAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}

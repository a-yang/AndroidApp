package com.example.abigailwatson.project3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
            String name = purchases.getToy(i).getToyName();
            name += ", ";
            name += Integer.toString(purchases.getToy(i).getPrice());
            toyNames.add(name);
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

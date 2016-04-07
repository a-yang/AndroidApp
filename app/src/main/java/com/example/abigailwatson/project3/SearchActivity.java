package com.example.abigailwatson.project3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.net.URL;
import java.util.ArrayList;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.view.DragEvent;
import android.widget.FrameLayout;
import android.content.ClipData;
import android.content.ClipDescription;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;
import android.view.View.DragShadowBuilder;



public class SearchActivity extends AppCompatActivity {

    public ToyList buttons;
    final String textSource = "http://people.cs.georgetown.edu/~wzhou/toy.data";

    private ListView lv;
    private android.widget.FrameLayout.LayoutParams layoutParams;
    ArrayList<String> names;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        names = new ArrayList<String>();
        names.add("Angela");
        names.add("Abby");
        names.add("Izzy");
        names.add("Adam");
        names.add("Mannhi");
        names.add("Kevin");
        names.add("Jenn");
        names.add("Joseph");
        names.add("Annette");
        names.add("Tony");
        names.add("Agnes");

        super.onCreate(savedInstanceState);

        new InternetClass().execute(textSource);

        setContentView(R.layout.activity_search);

        lv = (ListView) findViewById(R.id.list_of_toys);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                names);

        lv.setAdapter(arrayAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        lv.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> l, View v,
                                           int position, long id) {

                //Selected item is passed as item in dragData
                ClipData.Item item = new ClipData.Item(names.get(position));

                String[] clipDescription = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData dragData = new ClipData((CharSequence) v.getTag(),
                        clipDescription,
                        item);
                DragShadowBuilder myShadow = new DragShadowBuilder(v);

                v.startDrag(dragData, //ClipData
                        myShadow,  //View.DragShadowBuilder
                        names.get(position),  //Object myLocalState
                        0);    //flags

                return true;
            }});

        lv.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        layoutParams = (FrameLayout.LayoutParams) v.getLayoutParams();

                        // Do nothing
                        break;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        int x_cord = (int) event.getX();
                        int y_cord = (int) event.getY();
                        break;

                    case DragEvent.ACTION_DRAG_EXITED:
                        x_cord = (int) event.getX();
                        y_cord = (int) event.getY();
                        layoutParams.leftMargin = x_cord;
                        layoutParams.topMargin = y_cord;
                        v.setLayoutParams(layoutParams);
                        break;

                    case DragEvent.ACTION_DRAG_LOCATION:
                        x_cord = (int) event.getX();
                        y_cord = (int) event.getY();
                        break;

                    case DragEvent.ACTION_DRAG_ENDED:

                        // Do nothing
                        break;

                    case DragEvent.ACTION_DROP:
                        // Do nothing
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }

    public void goToScreenOne(View view) {
        finish();
    }




    private class InternetClass extends AsyncTask<String, Void, ToyList> {


        @Override
        protected ToyList doInBackground(String ... url) {
            return openTextSource(url);
        }

        protected ToyList openTextSource(String ... url) {
            URL textUrl;
            ToyList toyList = new ToyList();
            /*try {
                textUrl = new URL(textSource);
                BufferedReader bufferReader = new BufferedReader(new InputStreamReader(textUrl.openStream()));
                String StringBuffer;
                String stringText = "";
                while ((StringBuffer = bufferReader.readLine()) != null) {
                    stringText += StringBuffer;
                }
                bufferReader.close();
            } catch (MalformedURLException e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
            }*/
            return toyList;
        }

        protected void onPostExecute(ToyList toyList) {
            buttons = toyList;
        }

    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textPrompt = (TextView)findViewById(R.id.textprompt);
        textMsg = (TextView)findViewById(R.id.textmsg);

        textPrompt.setText("Wait...");

        URL textUrl;
        try {
            textUrl = new URL(textSource);
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(textUrl.openStream()));
            String StringBuffer;
            String stringText = "";
            ToyList finalToyList = new ToyList();
            while ((StringBuffer = bufferReader.readLine())!=null) {
                stringText = StringBuffer;
                byte[] toydata = stringText.getBytes();
                Toy toAdd = new Toy(toydata);
                finalToyList.addToy(toAdd);
            }

            bufferReader.close();
            textMsg.setText(stringText);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            textMsg.setText(e.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            textMsg.setText(e.toString());
        }

        textPrompt.setText("Finished!");

    }*/
    }

}

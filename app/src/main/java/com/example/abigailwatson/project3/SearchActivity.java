package com.example.abigailwatson.project3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import java.net.URL;

public class SearchActivity extends AppCompatActivity {

    public ToyList buttons;
    final String textSource = "http://people.cs.georgetown.edu/~wzhou/toy.data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new InternetClass().execute(textSource);
    }

    public void goToScreenOne(View view) {
        startActivity(new Intent(this, MainActivity.class));

    }


    private class InternetClass extends AsyncTask<String, Void, ToyList> {


        @Override
        protected ToyList doInBackground(String ... url) {
            return openTextSource(url);
        }

        protected ToyList openTextSource(String ... url) {
            URL textUrl;
            ToyList toyList = new ToyList();
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
            } catch (MalformedURLException e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
            }
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

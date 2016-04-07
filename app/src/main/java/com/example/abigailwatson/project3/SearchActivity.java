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
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;



public class SearchActivity extends AppCompatActivity {

    public ToyList buttons;
    final String textSource = "http://people.cs.georgetown.edu/~wzhou/toy.data";

    private  FrameLayout targetLayout;
    private ListView lv;
    private android.widget.FrameLayout.LayoutParams layoutParams;
    ArrayList<String> names;

    TextView comments;

    String commentMsg;


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

        comments = (TextView)findViewById(R.id.comments);
        lv = (ListView) findViewById(R.id.list_of_toys);

        targetLayout = (FrameLayout)findViewById(R.id.targetlayout);
        comments = (TextView)findViewById(R.id.comments);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                names);

        lv.setAdapter(arrayAdapter);
        lv.setOnItemLongClickListener(listSourceItemLongClickListener);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    OnItemLongClickListener listSourceItemLongClickListener
            = new OnItemLongClickListener(){

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
        }};

    private static class MyDragShadowBuilder extends View.DragShadowBuilder {
        private static Drawable shadow;

        public MyDragShadowBuilder(View v) {
            super(v);
            shadow = new ColorDrawable(Color.LTGRAY);
        }

        @Override
        public void onProvideShadowMetrics (Point size, Point touch){
            int width = getView().getWidth();
            int height = getView().getHeight();

            shadow.setBounds(0, 0, width, height);
            size.set(width, height);
            touch.set(width / 2, height / 2);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            shadow.draw(canvas);
        }

    }

    public void goToScreenOne(View view) {
        finish();
    }


    protected class MyDragEventListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            final int action = event.getAction();

            switch(action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //All involved view accept ACTION_DRAG_STARTED for MIMETYPE_TEXT_PLAIN
                    if (event.getClipDescription()
                            .hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))
                    {
                        commentMsg += v.getTag()
                                + " : ACTION_DRAG_STARTED accepted.\n";
                        comments.setText(commentMsg);
                        return true; //Accept
                    }else{
                        commentMsg += v.getTag()
                                + " : ACTION_DRAG_STARTED rejected.\n";
                        comments.setText(commentMsg);
                        return false; //reject
                    }
                case DragEvent.ACTION_DRAG_ENTERED:
                    commentMsg += v.getTag() + " : ACTION_DRAG_ENTERED.\n";
                    comments.setText(commentMsg);
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    commentMsg += v.getTag() + " : ACTION_DRAG_LOCATION - "
                            + event.getX() + " : " + event.getY() + "\n";
                    comments.setText(commentMsg);
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    commentMsg += v.getTag() + " : ACTION_DRAG_EXITED.\n";
                    comments.setText(commentMsg);
                    return true;
                case DragEvent.ACTION_DROP:
                    // Gets the item containing the dragged data
                    ClipData.Item item = event.getClipData().getItemAt(0);

                    commentMsg += v.getTag() + " : ACTION_DROP" + "\n";
                    comments.setText(commentMsg);

                    //If apply only if drop on buttonTarget
                    if(v == targetLayout){
                        String droppedItem = item.getText().toString();

                        commentMsg += "Dropped item - "
                                + droppedItem + "\n";
                        comments.setText(commentMsg);


                        return true;
                    }else{
                        return false;
                    }


                case DragEvent.ACTION_DRAG_ENDED:
                    if (event.getResult()){
                        commentMsg += v.getTag() + " : ACTION_DRAG_ENDED - success.\n";
                        comments.setText(commentMsg);
                    } else {
                        commentMsg += v.getTag() + " : ACTION_DRAG_ENDED - fail.\n";
                        comments.setText(commentMsg);
                    };
                    return true;
                default: //unknown case
                    commentMsg += v.getTag() + " : UNKNOWN !!!\n";
                    comments.setText(commentMsg);
                    return false;

            }
        }
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

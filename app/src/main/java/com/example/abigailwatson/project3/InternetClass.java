package com.example.abigailwatson.project3;

/**
 * Created by abigailwatson on 4/5/16.
 */

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.net.MalformedURLException;
        import java.net.URL;

        import android.app.Activity;
        import android.os.Bundle;
        import android.widget.TextView;

public class InternetClass extends Activity {

    TextView textMsg, textPrompt;
    final String textSource = "http://people.cs.georgetown.edu/~wzhou/toy.data";

    /** Called when the activity is first created. */
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
            while ((StringBuffer = bufferReader.readLine()) != null) {
                stringText += StringBuffer;
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

    }
}
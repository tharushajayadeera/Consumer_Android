package com.example.consumer;

import android.content.Intent;
import android.os.TransactionTooLargeException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.ProgressDialog.show;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Consumer";

    String ACTION = "com.example.producer.MAIN";
    String CATEGORY = "android.intent.category.DEFAULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recieve prime factors from consumer
        try
        {
            Intent intent = getIntent();
            String recievedNumberList = intent.getStringExtra("NUMBER");
            Log.d(TAG,"Recieved_Number_From_Producer : " + recievedNumberList);

            //Show recieved prime factors
            TextView textView = findViewById(R.id.editResult);
            textView.setText(recievedNumberList);
        }
        catch (Exception e)
        {
            Log.d(TAG,"Recieved_Number_From_Producer : No intent recieved from the producer");
        }
    }

    //On button click
    public void handleButtonClick(View view) {
        Toast.makeText(this,"btnClick",Toast.LENGTH_SHORT).show();

        Intent serviceStarter = new Intent();
        serviceStarter.setAction(ACTION);
        serviceStarter.addCategory(CATEGORY);

        //Get input number list from text area
        EditText editBox = findViewById(R.id.editNumber);
        String inputNumList = editBox.getText().toString();
        Log.d(TAG,"Input_Number : " + inputNumList);

        try
        {
            //Send input number list to the producer
            serviceStarter.putExtra("NUMBER",inputNumList);
            this.startActivity(serviceStarter);
            finish();
        }
        catch (Exception e)
        {
            //Handle the exception if the input is too large to send through intent
            if (e.getCause() instanceof TransactionTooLargeException)
            {
                String InputStringTooLargeException = "Your input is too large";
                TextView textView = findViewById(R.id.editResult);
                textView.setText(InputStringTooLargeException);
            }
        }
    }
}

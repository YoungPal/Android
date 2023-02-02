package com.kcci.createlogin01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_MENU = 101;
    Button button;
    EditText editText;
    TextView bringID;
    TextView bringPW;
    String strIP=null;
    String bStrID=null;
    String bStrPW=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        bringID = findViewById(R.id.bringIP);
        bringPW = findViewById(R.id.bringPW);

        Intent gIntent = getIntent();
        String bstrID=gIntent.getStringExtra("arg");
        String bstrPW=gIntent.getStringExtra("arg1");

        bringID.setText(bstrID);
        bringPW.setText(bstrPW);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                strIP = editText.getText().toString();
                Intent pintent = new Intent(getApplicationContext(), MenuActivity.class);
                pintent.putExtra("argIP",strIP);
                startActivityForResult(pintent, REQUEST_CODE_MENU);
            }
        });

    }
}
package com.kcci.socketclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.ScrollView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    static Handler mainHandler;
    ClientThread clientThread;
    TextView textView;
    ScrollView scrollView;
    final SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editTextIp = findViewById(R.id.editTextIp);
        EditText editTextId = findViewById(R.id.editTextId);
        EditText editTextSend = findViewById(R.id.editTextSend);
        ToggleButton toggleButton = findViewById(R.id.toggleButton);
        Button buttonSend = findViewById(R.id.buttonSend);
        textView = findViewById(R.id.textViewRec);
        scrollView = findViewById(R.id.ScrollViewRecv);

        toggleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(toggleButton.isChecked()){
                    String strIp=editTextIp.getText().toString();
                    String strId=editTextId.getText().toString();
                    clientThread = new ClientThread(strIp, strId);
                    clientThread.start();
                    buttonSend.setEnabled(true);
                } else{
                    clientThread.stopClient();
                    buttonSend.setEnabled(false);
                }

            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strSend = editTextSend.getText().toString();
                clientThread.sendData(strSend);
                editTextSend.setText("");
            }
        });
        mainHandler = new MainHandler();
    }
    class MainHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Date date = new Date();
            String strDate = dataFormat.format(date);
            Bundle bundle = msg.getData();
            String data = bundle.getString("msg");
            strDate = strDate + " " + data+'\n';
            textView.append(strDate);
            scrollView.fullScroll(View.FOCUS_DOWN);

        }
    }
}
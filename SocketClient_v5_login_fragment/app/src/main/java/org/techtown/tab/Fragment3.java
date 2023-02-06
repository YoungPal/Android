package org.techtown.tab;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Fragment3 extends Fragment {
    ClientThread clientThread;
    TextView textView;
    ScrollView scrollView;
    final SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);
        EditText editTextIp = view.findViewById(R.id.editTextIp);
        EditText editTextPort = view.findViewById(R.id.editTextPort);
        EditText editTextSend = view.findViewById(R.id.editTextSend);
        ToggleButton toggleButton = view.findViewById(R.id.toggleButton);
        Button buttonSend = view.findViewById(R.id.buttonSend);
        textView = view.findViewById(R.id.textViewRec);
        scrollView = view.findViewById(R.id.ScrollViewRecv);

        toggleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(toggleButton.isChecked()){
                    String strIp=editTextIp.getText().toString();
                    int intPort=Integer.parseInt(editTextPort.getText().toString());
                    clientThread = new ClientThread(strIp, intPort);
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
        return view;
    }
    void recvDataProcess(String data){
        Date date = new Date();
        String strDate = dataFormat.format(date);
        strDate = strDate + " " + data+'\n';
        textView.append(strDate);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

}
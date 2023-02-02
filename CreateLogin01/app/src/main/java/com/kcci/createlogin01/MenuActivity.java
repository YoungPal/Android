package com.kcci.createlogin01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
    public static final int REQuEST_CODE_MENU=101;
    public String strID = null;
    public String strPW = null;
    public String bStrIP = null;
    Button login;
    EditText editID;
    EditText editPW;
    TextView bringIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        login = findViewById(R.id.login);
        editID = findViewById(R.id.editID);
        editPW = findViewById(R.id.editPW);
        bringIP = findViewById(R.id.bringIP);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                strID = editID.getText().toString();
                strPW = editPW.getText().toString();
                if (strID.length() != 0 && strPW.length() != 0) {
                    Log.d("onClick()", "id : " + strID + " # pw : " + strPW);
                    Toast.makeText(view.getContext(), "id : " + strID + " # pw : " + strPW, Toast.LENGTH_LONG).show();
                    editID.setText("");
                    editPW.setText("");
                } else {
                    Toast.makeText(view.getContext(), "id와 pw 입력되지 않았습니다.", Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("arg",strID);
                intent.putExtra("arg1",strPW);
                startActivityForResult(intent, REQuEST_CODE_MENU);
            }
        });
        Intent gIntent2 = getIntent();
        String bstrIP=gIntent2.getStringExtra("argIP");
        bringIP.setText("IP:"+bstrIP);
    }
}
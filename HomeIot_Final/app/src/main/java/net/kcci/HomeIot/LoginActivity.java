package net.kcci.HomeIot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {
    EditText editTextIp;
    EditText editTextPort;
    EditText editTextId;
    EditText editTextPw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextIp = findViewById(R.id.editTextIp);
        editTextPort = findViewById(R.id.editTextPort);
        editTextId = findViewById(R.id.editTextId);
        editTextPw = findViewById(R.id.editTextPw);

        Intent intent = getIntent();
        final String serverIp = intent.getStringExtra("serverIp");
        final int serverPort = intent.getIntExtra("serverPort",ClientThread.serverPort);
        final String clientId = intent.getStringExtra("clientId");
        final String clientPw = intent.getStringExtra("clientPw");

        editTextIp.setHint(serverIp);
        editTextPort.setHint(Integer.toString(serverPort));
        editTextId.setHint(clientId);
        editTextPw.setHint(clientPw);

        Button buttonOk = findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int port;
                String ip = editTextIp.getText().toString();
                if(editTextPort.getText().toString().isEmpty())
                    port = 0;
                else
                    port = Integer.valueOf(editTextPort.getText().toString());
                String id = editTextId.getText().toString();
                String pw = editTextPw.getText().toString();

                if(ip.isEmpty())
                    ip =  serverIp;
                if(port == 0)
                    port =  serverPort;
                if(id.isEmpty())
                    id =  clientId;
                if(pw.isEmpty())
                    pw =  clientPw;
                Intent resultIntent = new Intent();
                resultIntent.putExtra("serverIp",ip);
                resultIntent.putExtra("serverPort",port);
                resultIntent.putExtra("clientId",id);
                resultIntent.putExtra("clientPw",pw);
                setResult(RESULT_OK,resultIntent);
                finish();
            }
        } );
        Button buttonCancel = findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}

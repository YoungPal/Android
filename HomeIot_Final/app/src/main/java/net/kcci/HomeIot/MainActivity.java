package net.kcci.HomeIot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_CODE_LOGIN_ACTIVITY = 100;
    static Handler mainHandler;
    ClientThread clientThread;
    Fragment1Home fragment1Home;
    Fragment2Dashboard fragment2Dashboard;
    Fragment3Telnet fragment3Telnet;
    int bottomNavigationIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment1Home = new Fragment1Home();
        fragment2Dashboard = new Fragment2Dashboard();
        fragment3Telnet = new Fragment3Telnet();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1Home).commit();

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab1:
                        Toast.makeText(getApplicationContext(), "첫 번째 탭 선택됨", Toast.LENGTH_LONG).show();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment1Home).commit();
                        bottomNavigationIndex = 0;
                        return true;
                    case R.id.tab2:
                        Toast.makeText(getApplicationContext(), "두 번째 탭 선택됨", Toast.LENGTH_LONG).show();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment2Dashboard).commit();
                        bottomNavigationIndex = 1;
                        return true;
                    case R.id.tab3:
                        Toast.makeText(getApplicationContext(), "세 번째 탭 선택됨", Toast.LENGTH_LONG).show();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment3Telnet).commit();
                        bottomNavigationIndex = 2;
                        return true;
                }

                return false;
            }
        });
        mainHandler = new MainHandler();
    }
    class MainHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String data = bundle.getString("msg");
            if(bottomNavigationIndex == 0)
                fragment1Home.recvDataProcess(data);
            else if(bottomNavigationIndex == 1)
                fragment2Dashboard.recvDataProcess(data);
            else if(bottomNavigationIndex == 2)
                fragment3Telnet.recvDataProcess(data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.login:
                Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                intent.putExtra("serverIp",ClientThread.serverIp);
                intent.putExtra("serverPort",ClientThread.serverPort);
                intent.putExtra("clientId",ClientThread.clientId);
                intent.putExtra("clientPw",ClientThread.clientPw);
                startActivityForResult(intent,REQUEST_CODE_LOGIN_ACTIVITY);
                break;
            case R.id.setting:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_LOGIN_ACTIVITY) {
            if(resultCode == RESULT_OK) {
                ClientThread.serverIp = data.getStringExtra("serverIp");
                ClientThread.serverPort = data.getIntExtra("serverPort",ClientThread.serverPort);
                ClientThread.clientId = data.getStringExtra("clientId");
                ClientThread.clientPw = data.getStringExtra("clientPw");
                clientThread = new ClientThread();
                clientThread.start();
                if(bottomNavigationIndex == 2)
                    fragment3Telnet.toggleButtonStart.setEnabled(false);
            } else if(resultCode == RESULT_CANCELED) {
                if(clientThread != null) {
                    clientThread.stopClient();
                    ClientThread.socket = null;
                    if(bottomNavigationIndex == 2)
                        fragment3Telnet.toggleButtonStart.setEnabled(true);
                }
            }
        }
    }
}

package net.kcci.HomeIot;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class Fragment2 extends Fragment {
    MainActivity mainActivity;
    ImageView imageViewLed;
    ImageView imageViewBlind;
    ImageView imageViewFan;
    TextView textIllu;
    TextView textTemp;
    TextView textHumi;

    boolean flagLed;
    boolean flagBlind;
    boolean flagFan;
    Switch switchLED;
    Switch switchBlind;
    Switch switchFan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view3 = inflater.inflate(R.layout.fragment2, container, false);
        Button buttonCondition = view3.findViewById(R.id.buttonCondition);
        Button buttonControl = view3.findViewById(R.id.buttonControl);

        ImageView imageViewIllu = view3.findViewById(R.id.imageViewIllu);
        ImageView imageViewTemp = view3.findViewById(R.id.imageViewTemp);
        ImageView imageViewHumi = view3.findViewById(R.id.imageViewHumi);
        textIllu = view3.findViewById(R.id.textIllu);
        textTemp = view3.findViewById(R.id.textTemp);
        textHumi = view3.findViewById(R.id.textHumi);

        imageViewLed = view3.findViewById(R.id.imageViewLed);
        imageViewBlind = view3.findViewById(R.id.imageViewBlind);
        imageViewFan = view3.findViewById(R.id.imageViewFan);
        switchLED = view3.findViewById(R.id.switchLED);
        switchBlind = view3.findViewById(R.id.switchBlind);
        switchFan = view3.findViewById(R.id.switchFan);

        mainActivity = (MainActivity)getActivity();

        buttonCondition.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mainActivity.clientThread.sendData("[KSH_ARD]LAMPON\n");
            }
        });

        switchLED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ClientThread.socket != null&&flagLed){
                    if(switchLED.isChecked()){
                        switchLED.setChecked(true);
                    }
                    else{
                        mainActivity.clientThread.sendData("[KSH_ARD]LEDON\n");
                        switchLED.setChecked(!switchLED.isChecked());
                    }
                }
                else{
                    if(switchLED.isChecked()==false){
                        switchLED.setChecked(false);
                    }
                    else{
                        mainActivity.clientThread.sendData("[KSH_ARD]LEDOFF\n");
                        switchLED.setChecked(!switchLED.isChecked());
                    }
                }
            }
        });


        switchBlind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ClientThread.socket != null&&flagBlind){
                    if(switchBlind.isChecked()){
                        switchBlind.setChecked(true);
                    }
                    else{
                        mainActivity.clientThread.sendData("[KSH_ARD]BLINSDON\n");
                        switchBlind.setChecked(!switchBlind.isChecked());
                    }
                }
                else{
                    if(switchBlind.isChecked()==false){
                        switchBlind.setChecked(false);
                    }
                    else{
                        mainActivity.clientThread.sendData("[KSH_ARD]BLINDSOFF\n");
                        switchBlind.setChecked(!switchBlind.isChecked());
                    }
                }
            }
        });
        switchFan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ClientThread.socket != null&&flagFan){
                    if(switchFan.isChecked()){
                        switchFan.setChecked(true);
                    }
                    else{
                        mainActivity.clientThread.sendData("[KSH_ARD]FANON\n");
                        switchFan.setChecked(!switchFan.isChecked());
                    }
                }
                else{
                    if(switchFan.isChecked()==false){
                        switchFan.setChecked(false);
                    }
                    else{
                        mainActivity.clientThread.sendData("[KSH_ARD]FANOFF\n");
                        switchFan.setChecked(!switchFan.isChecked());
                    }
                }
            }
        });

        buttonCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.clientThread.sendData("[KSH_ARD]GETSENSOR\n");
            }
        });
        return view3;
    }
    void recvDataProcess(String strRecvData) {
        String[] splitList = strRecvData.toString().split("\\[|]|@|\\r");
        for(int i=0; i<splitList.length; i++){
            Log.d("recvDataProcess", "i: "+ i + ", value: "+ splitList[i]);
        }
        if(splitList[2].equals("SENSOR")){
            textIllu.setText(splitList[3]+"%");
            textTemp.setText(splitList[4]+"℃");
            textHumi.setText(splitList[5]+"%");
        }
        else if(splitList[2].equals("LEDON")){
            imageViewLed.setImageResource(R.drawable.led_on);
            switchLED.setChecked(true);
            flagLed=true;
        }
        else if(splitList[2].equals("LEDOFF")){
            imageViewLed.setImageResource(R.drawable.led_off);
            flagLed=false;
        }
        else if(splitList[2].equals("BLINDSON")){
            imageViewBlind.setImageResource(R.drawable.blinds_on);
            switchBlind.setChecked(true);
            flagBlind=true;
        }
        else if(splitList[2].equals("BLINDSOFF")){
            imageViewBlind.setImageResource(R.drawable.blinds_off);
            flagBlind=false;
        }
        else if(splitList[2].equals("FANON")){
            imageViewFan.setImageResource(R.drawable.air_on);
            switchFan.setChecked(true);
            flagFan=true;
        }
        else if(splitList[2].equals("FANOFF")){
            imageViewFan.setImageResource(R.drawable.air_off);
            flagFan=false;
        }
    }
}

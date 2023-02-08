package net.kcci.HomeIot;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Fragment1Home extends Fragment {
    MainActivity mainActivity;
    ImageButton imageButtonLamp;
    ImageButton imageButtonPlug;
    boolean imageButtonLapmpCheck;
    boolean imageButtonPlugCheck;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view2 = inflater.inflate(R.layout.fragment1home, container, false);
        imageButtonLamp = view2.findViewById(R.id.imageButtonLamp);
        imageButtonPlug = view2.findViewById(R.id.imageButtonPlug);
        mainActivity = (MainActivity)getActivity();

        imageButtonLamp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(ClientThread.socket != null){
                    imageButtonLapmpCheck= !imageButtonLapmpCheck;
                    if(imageButtonLapmpCheck){
                        mainActivity.clientThread.sendData("[KSH_ARD]LAMPON\n");
                        imageButtonLamp.setImageResource(R.drawable.lamp_on);
                    }
                    else{
                        mainActivity.clientThread.sendData("[KSH_ARD]LAMPOFF\n");
                        imageButtonLamp.setImageResource(R.drawable.lamp_off);
                    }

                }
            }
        });
        imageButtonPlug.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(ClientThread.socket != null){
                    imageButtonPlugCheck= !imageButtonPlugCheck;
                    if(imageButtonPlugCheck){
                        mainActivity.clientThread.sendData("[KSH_ARD]PLUGON\n");
                        imageButtonPlug.setImageResource(R.drawable.plug_on);
                    }


                    else{
                        mainActivity.clientThread.sendData("[KSH_ARD]PLUGOFF\n");
                        imageButtonPlug.setImageResource(R.drawable.plug_off);
                    }


                }
            }
        });

        return view2;
    }
    void recvDataProcess(String strRecvData) {
        String[] splitList = strRecvData.toString().split("\\[|]|@|\\r");
        for(int i=0; i<splitList.length; i++){
            Log.d("recvDataProcess", "i: "+ i + ", value: "+ splitList[i]);
        }
        if(splitList[2].equals("LAMPON")){
            imageButtonLamp.setImageResource(R.drawable.lamp_on); //image change
            imageButtonLapmpCheck=true;
        }else if(splitList[2].equals("LAMPOFF")){
            imageButtonLamp.setImageResource(R.drawable.lamp_off);
            imageButtonLapmpCheck=false;
        }
        else if(splitList[2].equals("PLUGON")){
            imageButtonPlug.setImageResource(R.drawable.plug_on);
            imageButtonPlugCheck=true;
        }else if(splitList[2].equals("PLUGOFF")){
            imageButtonPlug.setImageResource(R.drawable.plug_off);
            imageButtonPlugCheck=false;
        }
    }
}

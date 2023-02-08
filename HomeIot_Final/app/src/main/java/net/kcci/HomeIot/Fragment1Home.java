package net.kcci.HomeIot;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;


public class Fragment1Home extends Fragment {
    MainActivity mainActivity;
    ImageButton imageButtonLamp;
    ImageButton imageButtonPlug;
    boolean imageButtonLampCheck;
    boolean imageButtonPlugCheck;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1home, container, false);
        imageButtonLamp = view.findViewById(R.id.imageButtonLamp);
        imageButtonPlug = view.findViewById(R.id.imageButtonPlug);
        mainActivity = (MainActivity)getActivity();
        imageButtonLamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ClientThread.socket != null) {
                    if (imageButtonLampCheck)
                        mainActivity.clientThread.sendData(ClientThread.arduinoId + "LAMPOFF\n");
                    else
                        mainActivity.clientThread.sendData(ClientThread.arduinoId + "LAMPON\n");
                }
            }
        });
        imageButtonPlug = view.findViewById(R.id.imageButtonPlug);
        imageButtonPlug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ClientThread.socket != null) {
                    if (imageButtonPlugCheck)
                        mainActivity.clientThread.sendData(ClientThread.arduinoId + "PLUGOFF\n");
                    else
                        mainActivity.clientThread.sendData(ClientThread.arduinoId + "PLUGON\n");
                }
            }
        });
        return view;
    }
    void recvDataProcess(String strRecvData)
    {
        String[] splitLists = strRecvData.toString().split("\\[|]|@|\\n") ;
        for(int i=0; i< splitLists.length; i++)
            Log.d("recvDataProcess"," i: " + i + ", value: " +splitLists[i]);
        if(splitLists[2].equals("LAMPON")) {
            imageButtonLamp.setImageResource(R.drawable.lamp_on);
            imageButtonLampCheck = true;
        } else if(splitLists[2].equals("LAMPOFF")) {
            imageButtonLamp.setImageResource(R.drawable.lamp_off);
            imageButtonLampCheck = false;
        } else if(splitLists[2].equals("PLUGON")) {
            imageButtonPlug.setImageResource(R.drawable.plug_on);
            imageButtonPlugCheck = true;
        } else if(splitLists[2].equals("PLUGOFF")) {
            imageButtonPlug.setImageResource(R.drawable.plug_off);
            imageButtonPlugCheck = false;
        }
    }
}

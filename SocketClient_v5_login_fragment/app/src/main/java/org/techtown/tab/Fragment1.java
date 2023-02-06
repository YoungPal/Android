package org.techtown.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Fragment1 extends Fragment {
    ScrollView scrollView2;
    TextView textView2;
    MainActivity mainActivity;
    final SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view2 = inflater.inflate(R.layout.fragment1, container, false);
        textView2 = view2.findViewById(R.id.textView2);
        EditText editSend = view2.findViewById(R.id.editSend);
        scrollView2 = view2.findViewById(R.id.scrollView2);
        Button buttonSend2 = view2.findViewById(R.id.buttonSend2);
        mainActivity = (MainActivity)getActivity();

        buttonSend2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strSend2 = editSend.getText().toString();
                mainActivity.clientThread.sendData(strSend2);
                editSend.setText("");
            }
        });
        return view2;
    }
    void recvDataProcess2(String data){
        Date date2 = new Date();
        String strDate2 = dataFormat.format(date2);
        strDate2 = strDate2 + " " + data+'\n';
        textView2.append(strDate2);
        scrollView2.fullScroll(View.FOCUS_DOWN);
    }

}

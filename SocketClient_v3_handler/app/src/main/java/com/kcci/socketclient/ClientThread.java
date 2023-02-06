package com.kcci.socketclient;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientThread extends Thread{
    Socket socket = null;

    @Override
    public void run() {

        try {
            socket = new Socket();
            displayText("[연결 요청]");
            socket.connect(new InetSocketAddress("10.10.141.2", 5000));
            displayText("[연결 성공]");
            String message = null;
            byte[] bytes = new byte[100];
//            message = "hello client";
//            sendData(message);
            InputStream is = socket.getInputStream();
            while(true){
                int readByteCount = is.read(bytes);
                if(readByteCount <=0)
                    break;
                message = new String(bytes, 0, readByteCount, "UTF-8");
                displayText("[데이터 받기 성공]: " + message);
            }
            is.close();

        } catch (Exception e) {
            displayText("서버가 중지되었습니다");
        }

        if (!socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e1) {
            }
        }
        displayText("클라이언트가 종료되었습니다");
    }

    synchronized void sendData(String data) { // final data
        Thread sendThread = new Thread() {
            @Override
            public void run() {
                try {
                    byte[] bytes = data.getBytes("UTF-8");
                    OutputStream os = socket.getOutputStream();
                    os.write(bytes);
                    os.flush();
                    displayText("데이터 보내기 성공");
                } catch (Exception e) {
                    displayText("서버를 확인하세요");
                }
            }
        };
        sendThread.start();
    }
    synchronized void sendMainActivity(String text){
        Message message = MainActivity.mainHandler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("msg",text);
        message.setData(bundle);
        MainActivity.mainHandler.sendMessage(message);
    }
    synchronized void displayText(String text){
        Log.d("displayText", text);

    }
}

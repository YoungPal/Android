# <ver4 → ver5 변경점>
- 우측상단 추가메뉴에서 login(connect Server & input_ID&PASSWD)
- fragment1에서 Server로 sendMessage
- Mainactivity에 존재하는 ClientThread를 받아서 구현
    - MainActivity mainActivity;
    - mainActivity = (MainActivity)getActivity();
    - mainActivity.clientThread.sendData(strSend2);
- Main내 handler에서 fragment1과 fragement3 충돌
    - fragment1에서 Main에서 선언된 state변수로 상태값 구분
- LoginActivtiy.java를 위한 AndroidManifest에 Activity 추가

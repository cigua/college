package com.example.qinc0.college;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by qincong on 2016/9/18.
 */
public class FragmentName extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        final View view = inflater.inflate(R.layout.stu_name, container, false);
        TextView name1 = (TextView) view.findViewById(R.id.name_1);
        TextView name2 = (TextView) view.findViewById(R.id.name_2);
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                String s = bundle.getString("a");
                Log.w("getS", s);
                if (s.equals("00")) {
                    name1.setVisibility(View.INVISIBLE);
                    name2.setVisibility(View.INVISIBLE);
                }
                if (s.equals("10")) {
                    name1.setText("王强");
                    name2.setVisibility(View.INVISIBLE);
                }
                if (s.equals("01")) {
                    name1.setText("李明");
                    name2.setVisibility(View.INVISIBLE);
                }
                if (s.equals("11")) {
                    name1.setText("李明");
                    name2.setText("王强");
                }
            }
        };
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String inputLine = null, temp;
                while (true) {
                    try {
                        Log.w("qc", "this");
                        URL url = new URL("http://119.29.173.76/college_php/card.php");
                        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                        InputStreamReader in = new InputStreamReader(urlConn.getInputStream());
                        BufferedReader buffer = new BufferedReader(in);

                        //使用循环来读取获得的数据
                        inputLine = buffer.readLine();
                        Log.w("card", inputLine);
                        Log.w("qc", "long " + inputLine.length());
                        Bundle bundle = new Bundle();
                        bundle.putString("a", inputLine);
                        Message msg = new Message();
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                        in.close();
                        urlConn.disconnect();
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        Log.w("qc", e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        return view;
    }
}

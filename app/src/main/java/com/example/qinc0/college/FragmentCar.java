package com.example.qinc0.college;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by qinc0 on 2016/6/27.
 */
public class FragmentCar extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        final View view = inflater.inflate(R.layout.car, container, false);
        final ImageView seat[] = new ImageView[9];
        seat[0] = (ImageView) view.findViewById(R.id.seat1);
        seat[1] = (ImageView) view.findViewById(R.id.seat2);
        seat[2] = (ImageView) view.findViewById(R.id.seat3);
        seat[3] = (ImageView) view.findViewById(R.id.seat4);
        seat[4] = (ImageView) view.findViewById(R.id.seat5);
        seat[5] = (ImageView) view.findViewById(R.id.seat6);
        seat[6] = (ImageView) view.findViewById(R.id.seat7);
        seat[7] = (ImageView) view.findViewById(R.id.seat8);
        seat[8] = (ImageView) view.findViewById(R.id.seat9);
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                int i = bundle.getInt("a");
                if (i == 0) {
                    seat[0].setImageResource(R.drawable.kong);
                } else {
                    seat[0].setImageResource(R.drawable.you);
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
                        URL url = new URL("http://119.29.173.76/college_php/seat.php");
                        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                        InputStreamReader in = new InputStreamReader(urlConn.getInputStream());
                        BufferedReader buffer = new BufferedReader(in);

                        //使用循环来读取获得的数据
                        inputLine = buffer.readLine();
                        Log.w("qc", "long " + inputLine.length());
                        if (inputLine.charAt(1) == '0') {
                            Log.w("qc", "set 0");
                            Bundle bundle = new Bundle();
                            bundle.putInt("a", 0);
                            Message msg = new Message();
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        } else {
                            Log.w("qc", "set 1");
                            Bundle bundle = new Bundle();
                            bundle.putInt("a", 1);
                            Message msg = new Message();
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }
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


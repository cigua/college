package com.example.qinc0.college;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by qinc0 on 2016/8/8.
 */
public class ClassTable extends AppCompatActivity {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.class_table);
        WebView webView=(WebView)findViewById(R.id.webview);
        String res="";
        try{
             res=readFile("classTable.txt");
        }
        catch (IOException e) {
        }
        webView.getSettings().setJavaScriptEnabled(true);
        //加载HTML字符串进行显示
        webView.loadData(res, "text/html; charset=UTF-8", null);
        TextView textView=(TextView)findViewById(R.id.test_text);
        //textView.setText(res);
    }
    public String readFile(String fileName) throws IOException {
        String res="";
        try{
            FileInputStream fin = openFileInput(fileName);
            int length = fin.available();
            byte [] buffer = new byte[length];
            fin.read(buffer);
            res = new String(buffer,"UTF-8");
            fin.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return res;

    }
}

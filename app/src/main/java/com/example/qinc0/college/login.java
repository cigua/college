package com.example.qinc0.college;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import okhttp3.OkHttpClient;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by qinc0 on 2016/8/8.
 */
public class login extends AppCompatActivity {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.login);
        Log.w("qc1","one");
        Toolbar toolbar=(Toolbar)findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("登录");
        Observable observable_getimg = Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap=null;
                try{
                    String url="http://119.29.173.76/college_php/get_img.php";
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder().url(url).build();
                    ResponseBody body=client.newCall(request).execute().body();
                    InputStream in=body.byteStream();
                    bitmap= BitmapFactory.decodeStream(in);
                }
                catch (Exception e) {
                    Log.w("qc1","网络错误");
                }
                subscriber.onNext(bitmap);
            }
        });
        Action1<Bitmap> onNextAction=new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                if(bitmap!=null) {
                    ImageView imageView=(ImageView)findViewById(R.id.img_checkword);
                    imageView.setImageBitmap(bitmap);
                }
            }
        };
        observable_getimg.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNextAction);
        Button bt_login=(Button)findViewById(R.id.button_login);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextStudentNumber=(EditText)findViewById(R.id.student_number);
                String studentNumber=editTextStudentNumber.getText().toString();
                EditText editTextStudentName=(EditText)findViewById(R.id.student_name);
                String studentName=editTextStudentName.getText().toString();
                EditText editTextPassword=(EditText)findViewById(R.id.student_password);
                String password=editTextPassword.getText().toString();
                EditText editTextCheckCode=(EditText)findViewById(R.id.checkword);
                String checkCode=editTextCheckCode.getText().toString();
                Action1<String> writeClass=new Action1<String>() {
                    @Override
                    public void call(String s) {
                        try{
                            writeFile("classTable.txt",s);
                            Log.w("write",s);
                        }
                        catch (IOException e) {

                        }
                    }
                    public void writeFile(String fileName,String writestr) throws IOException {
                        try{

                            FileOutputStream fout =openFileOutput(fileName, MODE_PRIVATE);

                            byte [] bytes = writestr.getBytes("UTF-8");

                            fout.write(bytes);

                            fout.close();
                        }

                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                Observable observable_login = Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        String res="";
                        try{
                            String url="http://119.29.173.76/college_php/getlogin.php?number="+studentNumber+"&password="+password+"&checkcode="+checkCode+"&name="+studentName;
                            Log.w("wri",url);
                            OkHttpClient client=new OkHttpClient();
                            Request request=new Request.Builder().url(url).build();
                            ResponseBody body=client.newCall(request).execute().body();
                            byte[] b = body.bytes();
                            res=new String(b,"GB2312");
                            Log.w("wri",res);
                            Log.w("wri","what?");
                        }
                        catch (Exception e) {
                            Log.w("qc1","网络错误");
                        }
                        subscriber.onNext(res);
                    }
                });
                observable_login.subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe(writeClass);
                Toast.makeText(login.this, "登录成功！", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}

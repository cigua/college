package com.example.qinc0.college;

import android.app.Fragment;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {
    Fragment[] fragments;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragments=new android.app.Fragment[2];
        final Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        fragments[0]=getFragmentManager().findFragmentById(R.id.one);
        fragments[1]=getFragmentManager().findFragmentById(R.id.two);
        getFragmentManager().beginTransaction().show(fragments[0]).hide(fragments[1]).commit();
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        final NavigationView navigationView=(NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.classroom:
                        getFragmentManager().beginTransaction().show(fragments[0]).hide(fragments[1]).commit();
                        drawerLayout.closeDrawers();
                        toolbar.setTitle("教室");
                        break;
                    case R.id.car:
                        toolbar.setTitle("停车位");
                        getFragmentManager().beginTransaction().show(fragments[1]).hide(fragments[0]).commit();
                        drawerLayout.closeDrawers();
                        break;
                }
                item.setChecked(true);
                return false;
            }
        });
    }
}


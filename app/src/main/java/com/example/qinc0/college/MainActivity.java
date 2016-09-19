package com.example.qinc0.college;

import android.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    Fragment[] fragments;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragments=new android.app.Fragment[3];
        final Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        fragments[0]=getFragmentManager().findFragmentById(R.id.one);
        fragments[1]=getFragmentManager().findFragmentById(R.id.two);
        fragments[2]=getFragmentManager().findFragmentById(R.id.three);
        getFragmentManager().beginTransaction().show(fragments[0]).hide(fragments[1]).commit();
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        actionBarDrawerToggle.syncState();
        final NavigationView navigationView=(NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch(item.getItemId()) {
                case R.id.classroom:
                    getFragmentManager().beginTransaction().show(fragments[0]).hide(fragments[1]).hide(fragments[2]).commit();
                    drawerLayout.closeDrawers();
                    toolbar.setTitle("教室");
                    break;
                case R.id.car:
                    toolbar.setTitle("停车位");
                    getFragmentManager().beginTransaction().show(fragments[1]).hide(fragments[0]).hide(fragments[2]).commit();
                    drawerLayout.closeDrawers();
                    break;
                case R.id.qiandao:
                    toolbar.setTitle("签到");
                    getFragmentManager().beginTransaction().show(fragments[2]).hide(fragments[1]).hide(fragments[0]).commit();
                    drawerLayout.closeDrawers();
                    break;

//                case R.id.menu_login:
//                    drawerLayout.closeDrawers();
//                    Log.w("qc1","one");
//                    Intent intent=new Intent(MainActivity.this,login.class);
//                    startActivity(intent);
//                    break;
//                case R.id.classTable:
//                    drawerLayout.closeDrawers();
//                    Log.w("qc1","one");
//                    Intent intent1=new Intent(MainActivity.this,ClassTable.class);
//                    startActivity(intent1);
//                    break;
            }
            item.setChecked(true);
            return false;
        });
    }
}


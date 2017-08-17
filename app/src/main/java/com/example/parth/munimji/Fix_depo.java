package com.example.parth.munimji;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class Fix_depo extends AppCompatActivity {
    DrawerLayout myDrawerLayout;
    NavigationView myNavigationView;
    FragmentManager myFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fix_depo);

        myDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutfd);
        myNavigationView = (NavigationView) findViewById(R.id.nav_drawerfd);
        ///set up navigation drawer
        myFragmentManager = getSupportFragmentManager();
        myNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem selectedMenuItem) {
                myDrawerLayout.closeDrawers();
                if (selectedMenuItem.getItemId() == R.id.nav_item_home) {
                    android.support.v4.app.FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, new HomeFragment()).commit();
                }
                if (selectedMenuItem.getItemId() == R.id.nav_FIX_DEPO) {
                    Intent i=new Intent(getApplicationContext(),Fix_depo.class);
                    startActivity(i);
                }
                return false;
            }
        });

        //setup toolbar

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbarfd);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, myDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);
        myDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "settings", Toast.LENGTH_SHORT).show();
            Intent j = new Intent(this, Settings.class);
            startActivity(j);
            return true;

        }
        if (id == R.id.aboutus) {
            Toast.makeText(getApplicationContext(), "Aboutus", Toast.LENGTH_SHORT).show();

            /*Intent j=new Intent(this,Aboutus.class);
            startActivity(j);
            return true;*/
            return true;
        }
        return true;

    }
}

package com.example.parth.munimji;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import static com.example.parth.munimji.HomeFragment.myAdapter;
import static com.example.parth.munimji.HomeFragment.viewPager;

/**
 * Created by Jauhar xlr on 4/18/2016.
 * mycreativecodes.in
 */
public class MainActivity extends AppCompatActivity implements Dialogfragment_modifydeletecall.Communicator {
    DrawerLayout myDrawerLayout;
    NavigationView myNavigationView;
    FragmentManager myFragmentManager;
    private ProgressDialog prgDialog;
    boolean doubleBackToExitPressedOnce = false;
    public static final int progress_bar_type = 0;
    android.support.v4.app.FragmentTransaction myFragmentTransaction;
    HomeFragment homeFragment;

    public String openFragment;

    public MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         *Setup the DrawerLayout and NavigationView
         */
        myDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        myNavigationView = (NavigationView) findViewById(R.id.nav_drawer);
        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the HomeFragment as the first Fragment
         */
        homeFragment = new HomeFragment();

        myFragmentManager = getSupportFragmentManager();
        myFragmentTransaction = myFragmentManager.beginTransaction();
        myFragmentTransaction.replace(R.id.containerView, homeFragment, "home").commit();

        if(Build.VERSION.SDK_INT >= 21){

        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this ,R.color.orange));}

        /**
         * Setup click events on the Navigation View Items.
         */
        myNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem selectedMenuItem) {
                myDrawerLayout.closeDrawers();
                if (selectedMenuItem.getItemId() == R.id.nav_item_home) {
                    android.support.v4.app.FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, homeFragment).commit();
                }
                if (selectedMenuItem.getItemId() == R.id.nav_FIX_DEPO) {
                   Intent i=new Intent(getApplicationContext(),Fix_depo.class);
                    startActivity(i);
                }
                return false;
            }
        });
        /**
         * Setup Drawer Toggle of the Toolbar
         */
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, myDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);
        myDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    @Override
    public void onDialogmsg(String s, String tag) {
        Bundle bundle = new Bundle();
        bundle.putString("s", s);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //getItem(0) gets the SAVE button on the action bar
        menuItem = (MenuItem) menu.getItem(0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_save){

            openFragment = String.valueOf(viewPager.getAdapter().getPageTitle(viewPager.getCurrentItem()));

            if(openFragment.matches("Owner Info")){
                Toast.makeText(this, openFragment, Toast.LENGTH_SHORT).show();
                    Addnewowner_index fragment = (Addnewowner_index) HomeFragment.myAdapter.getCurrentFragment();
                    fragment.saveInfo();
                }
            else if(openFragment.matches("Credits")){
                Toast.makeText(this, openFragment, Toast.LENGTH_SHORT).show();
                Paymentin fragment = (Paymentin) HomeFragment.myAdapter.getCurrentFragment();
                fragment.saveInfo();
            }
            else if(openFragment.matches("Debits")){
                Toast.makeText(this, openFragment, Toast.LENGTH_SHORT).show();
                Paymentout fragment = (Paymentout) HomeFragment.myAdapter.getCurrentFragment();
                fragment.saveInfo();
            }
            else if(openFragment.matches("List Credits")){
                Toast.makeText(this, openFragment, Toast.LENGTH_SHORT).show();
            }
            else if(openFragment.matches("List Debits")){
                Toast.makeText(this, openFragment, Toast.LENGTH_SHORT).show();
            }
            else if(openFragment.matches("Tenant Info")){
                Toast.makeText(this, openFragment, Toast.LENGTH_SHORT).show();
                Tenantinfo fragment = (Tenantinfo) HomeFragment.myAdapter.getCurrentFragment();
                fragment.saveInfo();
            }
            else if(openFragment.matches("Company Info")){
                Toast.makeText(this, openFragment, Toast.LENGTH_SHORT).show();
                Companyinfo fragment = (Companyinfo) HomeFragment.myAdapter.getCurrentFragment();
                fragment.saveInfo();
            }
            else if(openFragment.matches("Helplines")){
                Toast.makeText(this, openFragment, Toast.LENGTH_SHORT).show();
            }
            else if(openFragment.matches("Mobile Nos")){
                Toast.makeText(this, openFragment, Toast.LENGTH_SHORT).show();
            }
            else if(openFragment.matches("Vehicle Info")){
                Toast.makeText(this, openFragment, Toast.LENGTH_SHORT).show();
            }

        }
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

        }
        if (id == R.id.ntype) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Payment Type");
            builder.setMessage("Enter new payment type");
            final EditText input = new EditText(getApplicationContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            builder.setView(input);
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }

            });
            builder.setPositiveButton("add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String ptype = input.getText().toString();
                    DataBaseHelper myDb = new DataBaseHelper(getApplicationContext());
                    boolean ans = myDb.insertData(ptype);
                    Toast.makeText(getApplicationContext(), "data inserted=" + ans, Toast.LENGTH_SHORT).show();

                    //Intent i = new Intent(getApplicationContext(), Detail_paymentin.class);
                    // startActivity(i);
                }

            });
            builder.show();
        }
        if (id == R.id.port_settings)
        {
            new port().execute();
        }
        if(id==R.id.dtype)
        {
            DataBaseHelper m1=new DataBaseHelper(this);
            Cursor cursor=m1.getAllData("type");
            String[] result = new String[cursor.getCount()];
            int i=0;
            while(cursor.moveToNext())
            {result[i] = cursor.getString(0);
            i++;
            }
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, result);
            ListView lv=new ListView(this);
            lv.setAdapter(adapter);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(" delete Payment Type");
            builder.setView(lv);
     lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {

         }
     });
            builder.show();

        }
        if(id==R.id.restore_settings)
        {
            new restore().execute();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    class port extends AsyncTask< Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Shows Progress Bar Dialog and then call doInBackground method
            showDialog(progress_bar_type);
        }

        @Override
        protected Void doInBackground(Void... params) {

            DataBaseHelper mydb = new DataBaseHelper(getApplicationContext());
            try {

                String state = Environment.getExternalStorageState();
//check if the external directory is availabe for writing
                File exportDir;
                if (!Environment.MEDIA_MOUNTED.equals(state)) {
                    return null;
                } else {
                    exportDir = new File(Environment.getExternalStorageDirectory() + File.separator + "munimji");
                }

//if the external storage directory does not exists, we create it
                if (!exportDir.exists()) {
                    exportDir.mkdirs();
                }
                //
                String filename = "tbpayin.csv";
                File saveFile = new File(exportDir, filename);
                FileWriter fw = new FileWriter(saveFile);
                BufferedWriter bw = new BufferedWriter(fw);
                Cursor cursor = mydb.getAllData("tbpayin");
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    if (i != cursor.getColumnCount() - 1) {
                        bw.write(cursor.getColumnName(i) + ",");
                    } else {
                        bw.write(cursor.getColumnName(i));
                    }
                }
                bw.newLine();
                while (cursor.moveToNext()) {
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        if (i != cursor.getColumnCount() - 1) {
                            bw.write(cursor.getString(i) + ",");
                        } else {
                            bw.write(cursor.getString(i));
                        }
                    }//tbtenant    tbcompanyinfo  HelpLine
                bw.newLine();
                }

              //  bw.flush();
                bw.close();
                fw.close();

                String filename1 = "tbpayout.csv";
                File saveFile1 = new File(exportDir, filename1);
                FileWriter fw1 = new FileWriter(saveFile1);
                BufferedWriter bw1 = new BufferedWriter(fw1);
                Cursor cursor1 = mydb.getAllData("tbpayout");
                for (int i = 0; i < cursor1.getColumnCount(); i++) {
                    if (i != cursor1.getColumnCount() - 1) {
                        bw1.write(cursor1.getColumnName(i) + ",");
                    } else {
                        bw1.write(cursor1.getColumnName(i));
                    }
                }
                bw1.newLine();
                while (cursor1.moveToNext()) {
                    for (int i = 0; i < cursor1.getColumnCount(); i++) {
                        if (i != cursor1.getColumnCount() - 1) {
                            bw1.write(cursor1.getString(i) + ",");
                        } else {
                            bw1.write(cursor1.getString(i));
                        }
                    }
                    bw1.newLine();
                }

                bw1.flush();
                bw1.close();
                fw1.close();
                String filename2 = "tbowner.csv";
                File saveFile2 = new File(exportDir, filename2);
                FileWriter fw2 = new FileWriter(saveFile2);
                BufferedWriter bw2 = new BufferedWriter(fw2);
                Cursor cursor2 = mydb.getAllData("tbowner");
                for (int i = 1; i < cursor2.getColumnCount(); i++) {
                    if (i != cursor2.getColumnCount() - 1) {
                        bw2.write(cursor2.getColumnName(i) + ",");
                    } else {
                        bw2.write(cursor2.getColumnName(i));
                    }

                }
                bw2.write("Contact Number");
                bw2.write("Email");
                bw2.write("Vehicle Number");
                bw2.newLine();

                while (cursor2.moveToNext()) {
                    String Line= new String();
                    for (int i = 1; i < cursor2.getColumnCount(); i++) {
                        if (i != cursor2.getColumnCount() - 1) {
                            //bw2.write(cursor2.getString(i) + ",");
                            Line=Line+cursor2.getString(i) + ",";
                        } else {
                            //bw2.write(cursor2.getString(i));
                            Line=Line+cursor2.getString(i);
                        }
                    }
                    Cursor temp = mydb.get_mobile_own(cursor2.getInt(0));
                    Line=Line+",";
                    //bw2.write("," );
                    int x=temp.getCount();
                    while (temp.moveToNext()) {
                        //bw2.write(temp.getString(0) );
                        Line=Line+temp.getString(0);
                        x--;
                        if(x>0)
                            Line=Line+"/";
                            //bw2.write("/");
                    }
                    temp.close();
                    temp = mydb.get_email_own(cursor2.getInt(0));
                    Line=Line+",";
                    x=temp.getCount();
                    while (temp.moveToNext()) {
                        Line=Line+temp.getString(0);
                        x--;
                        if(x>0) Line=Line+"/";
                    }
                    temp = mydb.get_vehicle_own(cursor2.getInt(0));
                    Line=Line+",";
                    //bw2.write("," );
                    x=temp.getCount();
                    while (temp.moveToNext()) {
                        //bw2.write(temp.getString(0)+"("+temp.getString(1)+")" );
                        Line=Line+temp.getString(0)+"("+temp.getString(1)+")";
                        x--;
                        if(x>0) Line=Line+"/";
                            //bw2.write("/");
                    }
                    temp.close();
                    System.out.println(Line);
                    bw2.write(Line);
                    bw2.newLine();
                }
                cursor2.close();
               // bw2.flush();
                bw2.close();
                fw2.close();

                String filename4 = "tbpersonalinfo.csv";
                File saveFile4 = new File(exportDir, filename4);
                FileWriter fw4 = new FileWriter(saveFile4);
                BufferedWriter bw4 = new BufferedWriter(fw4);
                Cursor cursor4 = mydb.getAllData("tbpersonalinfo");
                for (int i = 0; i < cursor4.getColumnCount(); i++) {
                    if (i != cursor4.getColumnCount() -1) {
                        bw4.write(cursor4.getColumnName(i) + ",");
                    } else {
                        bw4.write(cursor4.getColumnName(i));
                    }
                }
                bw4.newLine();
                while (cursor4.moveToNext()) {
                    for (int i = 0; i < cursor4.getColumnCount(); i++) {
                        if (i != cursor4.getColumnCount() - 1) {
                            bw4.write(cursor4.getString(i) + ",");
                        } else {
                            bw4.write(cursor4.getString(i));
                        }
                    }
                    bw4.newLine();
                }

                //bw4.flush();
                cursor4.close();
                bw4.close();
                fw4.close();

                String filename3 = "tbtenant.csv";
                File saveFile3 = new File(exportDir, filename3);
                FileWriter fw3 = new FileWriter(saveFile3);
                BufferedWriter bw3 = new BufferedWriter(fw3);
                Cursor cursor3 = mydb.getAllData("tbtenant");
                for (int i = 0; i < cursor3.getColumnCount(); i++) {
                    if (i != cursor3.getColumnCount() -1) {
                        bw3.write(cursor3.getColumnName(i) + ",");
                    } else {
                        bw3.write(cursor3.getColumnName(i));
                    }
                }
                bw3.newLine();
                while (cursor3.moveToNext()) {
                    for (int i = 0; i < cursor3.getColumnCount(); i++) {
                        if (i != cursor3.getColumnCount() - 1) {
                            bw3.write(cursor3.getString(i) + ",");
                        } else {
                            bw3.write(cursor3.getString(i));
                        }
                    }
                    bw3.newLine();
                }

               // bw3.flush();
                cursor3.close();
                bw3.close();
                fw3.close();
                String filename5 = "tbcompanyinfo.csv";
                File saveFile5 = new File(exportDir, filename5);
                FileWriter fw5 = new FileWriter(saveFile5);
                BufferedWriter bw5 = new BufferedWriter(fw5);
                Cursor cursor5 = mydb.getAllData("tbcompanyinfo");
                for (int i = 0; i < cursor5.getColumnCount(); i++) {
                    if (i != cursor5.getColumnCount() -1) {
                        bw5.write(cursor5.getColumnName(i) + ",");
                    } else {
                        bw5.write(cursor5.getColumnName(i));
                    }
                }
                bw5.newLine();
                while (cursor5.moveToNext()) {
                    for (int i = 0; i < cursor5.getColumnCount(); i++) {
                        if (i != cursor5.getColumnCount() - 1) {
                            bw5.write(cursor5.getString(i) + ",");
                        } else {
                            bw5.write(cursor5.getString(i));
                        }
                    }
                    bw5.newLine();
                }
                cursor5.close();
                bw5.flush();
                bw5.close();
                fw5.close();

                String filename6 = "HelpLine.csv";
                File saveFile6 = new File(exportDir, filename6);
                FileWriter fw6 = new FileWriter(saveFile6);
                BufferedWriter bw6 = new BufferedWriter(fw6);
                Cursor cursor6 = mydb.getAllData("HelpLine");
                for (int i = 0; i < cursor6.getColumnCount(); i++) {
                    if (i != cursor6.getColumnCount() -1) {
                        bw6.write(cursor6.getColumnName(i) + ",");
                    } else {
                        bw6.write(cursor6.getColumnName(i));
                    }
                }
                bw6.newLine();
                while (cursor6.moveToNext()) {
                    for (int i = 0; i < cursor6.getColumnCount(); i++) {
                        if (i != cursor6.getColumnCount() - 1) {
                            bw6.write(cursor6.getString(i) + ",");
                        } else {
                            bw6.write(cursor6.getString(i));
                        }
                    }
                    bw6.newLine();
                }

               // bw6.flush();
                cursor6.close();
                bw6.close();
                fw6.close();



                ///////
                String filename7 = "tbtenantinfo.csv";
                File saveFile7 = new File(exportDir, filename7);
                FileWriter fw7 = new FileWriter(saveFile7);
                BufferedWriter bw7 = new BufferedWriter(fw7);
                Cursor cursor7 = mydb.getAllData("tbtenantinfo");
                for (int i = 0; i < cursor7.getColumnCount(); i++) {
                    if (i != cursor7.getColumnCount() -1) {
                        bw7.write(cursor7.getColumnName(i) + ",");
                    } else {
                        bw7.write(cursor7.getColumnName(i));
                    }
                }
                bw7.newLine();
                while (cursor7.moveToNext()) {
                    for (int i = 0; i < cursor7.getColumnCount(); i++) {
                        if (i != cursor7.getColumnCount() - 1) {
                            bw7.write(cursor7.getString(i) + ",");
                        } else {
                            bw7.write(cursor7.getString(i));
                        }
                    }
                    bw7.newLine();
                }

                //bw7.flush();
                cursor7.close();
                bw7.close();
                fw7.close();
            } catch (Exception e) {

            Log.e("error",e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

           dismissDialog(progress_bar_type);
            Toast.makeText(getApplicationContext(), "porting successfull", Toast.LENGTH_LONG).show();

        }
    }

    class restore extends AsyncTask<Void, Void, Void>
    {
        boolean ans,ans1,ans2,ans3,ans4,ans5,ans6,ans7;
        String name, phno;
        @Override
        protected Void doInBackground(Void... params) {

            try{
                //Toast.makeText(getApplicationContext(), "importing!!!!! ", Toast.LENGTH_SHORT).show();
                //System.out.println("Importing!!");
                String state = Environment.getExternalStorageState();
                File exportDir;
                if (!Environment.MEDIA_MOUNTED.equals(state)) {
                    return null;
                } else {
                    exportDir = new File(Environment.getExternalStorageDirectory() + File.separator + "munimji");
                }
                //Toast.makeText(getApplicationContext(), "importing!!!!! ", Toast.LENGTH_SHORT).show();
                name="";
                phno="";
                String filename2 = "tbowner.csv";
                File saveFile2 = new File(exportDir, filename2);
                FileReader fw2 = new FileReader(saveFile2);
                BufferedReader br2 = new BufferedReader(fw2);
                String line=br2.readLine();
                DataBaseHelper mydb=new DataBaseHelper(getApplicationContext());
                while ((line = br2.readLine()) != null) {
                    String[] str = line.split(",");
                     ans= mydb.tb_owner_ins(str[0],str[1],str[2]);
                    //System.out.println(str[0]+"  "+str[1]+"  "+str[2]+"  "+str[3]+"  "+str[4]+"  "+str[5]);
                    //System.out.println(str[1]);
                    //Toast.makeText(getApplicationContext(), "importing "+str[1], Toast.LENGTH_SHORT).show();
                    //wait(1000);

                    if(ans)
                    {
                        name=name + " "+str[1];
                        Cursor res2 = mydb.tb_owner_getid(str[0]);
                        if (res2.moveToNext()) {
                            int id = res2.getInt(res2.getColumnIndex("id"));
                            if(str.length>=4) {
                                if (!str[3].isEmpty()) {
                                    String[] mobile = str[3].split("/");
                                    int mobileCount=0;
                                    while (mobileCount<mobile.length) {
                                        phno = phno + " " + mobile[mobileCount];
                                        ans1 = mydb.tb_persinfo_ins(id, 1, mobile[mobileCount]);        //1-mobile
                                        mobileCount++;
                                    }
                                }
                            }
                            if(str.length>=5) {
                                if (!str[4].isEmpty()){
                                    String[] email = str[4].split("/");
                                int emailCount=0;
                                while (emailCount<email.length) {
                                    //phno = phno + " " + email[emailCount];
                                    ans2 = mydb.tb_persinfo_ins(id, 2, email[emailCount]);        //1-mobile
                                    //ans2 = mydb.tb_persinfo_ins(id, 2, str[4]);         //2-email
                                    emailCount++;
                                }
                            }
                            }
                            if(str.length>=6) {
                                if(!str[5].isEmpty()) {
                                    String[] vehicle = str[5].split("/");
                                    int vehicleCount=0;
                                    while (vehicleCount<vehicle.length) {
                                        //phno = phno + " " + vehicle[vehicleCount];
                                        //ans3 = mydb.tb_persinfo_ins(id, 3, str[5]);
                                        String temp = vehicle[vehicleCount].substring(vehicle[vehicleCount].indexOf("(")+1,vehicle[vehicleCount].indexOf(")"));
                                        String temp1=vehicle[vehicleCount].substring(0,vehicle[vehicleCount].indexOf("("));
                                        phno= phno+  "  "+ temp1+"  "+ temp;
                                        ans3 = mydb.tb_persinfo_ins(id, Integer.parseInt(temp), temp1);        //1-mobile
                                        vehicleCount++;
                                    }
                                }
                            }
                        }
                        res2.close();
                    }
                    //db.execSQL("create table tbowner (id INTEGER PRIMARY KEY ,flatno TEXT UNIQUE,name TEXT);");
                }


                /*
                create table  (id INTEGER,type INTEGER,info TEXT);");
        db.execSQL("create table  (
        db.execSQL("create table tbtenant (tid INTEGER PRIMARY KEY,flatid INTEGER,name TEXT,joindate TEXT,leftdate TEXT);");
        db.execSQL("create table tbcompanyinfo (companyid INTEGER PRIMARY KEY,name TEXT UNIQUE,contactperson TEXT,mobile TEXT,description TEXT); ");
        db.execSQL("create table HelpLine (id INTEGER PRIMARY KEY ,desc TEXT,mobile TEXT,email TEXT); ");
                * */
/*
                String filename = "tbpersonalinfo.csv";
                File saveFile = new File(exportDir, filename);
                FileReader fw = new FileReader(saveFile);
                BufferedReader br= new BufferedReader(fw);
                 line=br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] str = line.split(",",3);
                    int id=Integer.parseInt(str[0]);
                    int type=Integer.parseInt(str[1]);
                    ans1= mydb.tb_persinfo_ins(id,type,str[2]);
                    //db.execSQL("create table tbowner (id INTEGER PRIMARY KEY ,flatno TEXT UNIQUE,name TEXT);");

                }
*/
//_id INTEGER PRIMARY KEY,personid INTEGER,transdate TEXT,AMOUNT INTEGER,MODE TEXT,CHECKNO INTEGER,BRANCH TEXT,DATE TEXT,TYPE TEXT,receiptno TEXT);");
                /*
                String fname="tbpayin.csv";
                File thisfile=new File(exportDir,fname);
                FileReader fileReader=new FileReader(thisfile);
                BufferedReader bufferedReader=new BufferedReader(fileReader);
                line=bufferedReader.readLine();
                while((line=bufferedReader.readLine()) !=null)
                {
                    String str1[]=line.split(",",10);
                    int persoid=Integer.parseInt(str1[1]);
                    int amount=Integer.parseInt(str1[3]);
                    String checkno=str1[5];
//                    ans2=mydb.insertData_tbpayin(persoid,amount,str1[2],str1[4],checkno,str1[6],str1[7],str1[8],str1[9]);
                    ans2=mydb.insertData_tbpayin(persoid,amount,str1[2],str1[4],checkno,str1[6],str1[7],str1[8]);
                }
 //db.execSQL("create table tbtenant (tid INTEGER PRIMARY KEY,flatid INTEGER,name TEXT,joindate TEXT,leftdate TEXT);");
                String fname1="tbtenant.csv";
                File thisfile1=new File(exportDir,fname1);
                FileReader fileReader1=new FileReader(thisfile1);
                BufferedReader bufferedReader1=new BufferedReader(fileReader1);
                line=bufferedReader1.readLine();
                while((line=bufferedReader1.readLine()) !=null)
                {
                    String str1[]=line.split(",",5);
                    int flatid=Integer.parseInt(str1[1]);
                    ans3=mydb.tb_tenant_ins(flatid,str1[2],str1[3],str1[4]);
                }

//db.execSQL("create table tbpayout (id INTEGER PRIMARY KEY,companyid INTEGER,AMOUNT INTEGER,transdate TEXT,MODE TEXT,CHECKNO INTEGER,BRANCH TEXT,date TEXT);");
                String fname2="tbpayout.csv";
                File thisfile2=new File(exportDir,fname2);
                FileReader fileReader2=new FileReader(thisfile2);
                BufferedReader bufferedReader2=new BufferedReader(fileReader2);
                line=bufferedReader2.readLine();
                while((line=bufferedReader2.readLine()) !=null)
                {
                    String str1[]=line.split(",",8);
                   int cid=Integer.parseInt(str1[1]);
                    int amunt=Integer.parseInt(str1[2]);
                    int checkno=Integer.parseInt(str1[5]);
                    ans4=mydb.insertData(cid,amunt,str1[3],str1[4],checkno,str1[6],str1[7]);
                }
// tbcompanyinfo (companyid INTEGER PRIMARY KEY,name TEXT UNIQUE,contactperson TEXT,mobile TEXT,description TEXT); ");
                String fname3="tbcompanyinfo.csv";
                File thisfile3=new File(exportDir,fname3);
                FileReader fileReader3=new FileReader(thisfile3);
                BufferedReader bufferedReader3=new BufferedReader(fileReader3);
                line=bufferedReader3.readLine();
                while((line=bufferedReader3.readLine()) !=null)
                {
                    String str1[]=line.split(",",5);
                    ans5=mydb.insertcompanyinfo(str1[1],str1[2],str1[3],str1[4]);
                }
///HelpLine (id INTEGER PRIMARY KEY ,desc TEXT,mobile TEXT,email TEXT); ");
                String fname4="HelpLine.csv";
                File thisfile4=new File(exportDir,fname4);
                FileReader fileReader4=new FileReader(thisfile4);
                BufferedReader bufferedReader4=new BufferedReader(fileReader4);
                line=bufferedReader4.readLine();
                while((line=bufferedReader4.readLine()) !=null)
                {
                    String str1[]=line.split(",",4);
                    ans6=mydb.addhelp(str1[1],str1[2],str1[3]);
                }
                */
            }catch (Exception e)
            {
                //Toast.makeText(getApplicationContext(), "ERROR!!!!"+ans+ans1+ans2+ans3+ans4+ans5+ans6, Toast.LENGTH_LONG).show();
                //System.out.println(e.getMessage());
                name="ERROR "+ e.toString();
                Log.e("error",e.toString());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(), "importing "+name + "   "+ phno+" "+ans+ans1+ans2+ans3+ans4+ans5+ans6, Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case progress_bar_type:
                prgDialog = new ProgressDialog(this);
                prgDialog.setMessage("porting process running...");
                prgDialog.setIndeterminate(false);
                prgDialog.setMax(100);
                prgDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                prgDialog.setCancelable(false);
                prgDialog.show();
                return prgDialog;
            default:
                return null;
        }
    }
}
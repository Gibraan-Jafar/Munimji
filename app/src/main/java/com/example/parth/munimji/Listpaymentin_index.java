package com.example.parth.munimji;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by parth on 1/7/16.
 */
public class Listpaymentin_index extends Fragment implements NumberPicker.OnValueChangeListener {
    //for sharepref
    String sharedfile="iniSettings";
    String Sowner="owner";
    String Stenant="tenant";



    DataBaseHelper myDb;
    Spinner spinner_to, spinner_from;
    ExpandableListView lv;
    int rowno;
    final int DATE_PICKER_ID = 1111;
    Button bt_submit,bt_exp;
    TextView tvflatno, tvamount, tvjan, tvfeb, tvmarch, tvapril, tvmay, tvjune, tvjuly, tvaugest, tvsept, tvoct, tvnov, tvdec;
    int Tamount, year,grantotal,tjan,tfeb,tmarch,tapril,tmay,tjune,tjuly,taugust,tsept,toct,tnov,tdec;
    final int tamountexpectedT = 12 * 1200;
    Spinner spinner;
    int rentt ;
    int rento ;
    List<String> categories = new ArrayList<String>();
    ArrayList<String> years = new ArrayList<>();
    String ptype;
    final int tamountexpectednoT = 12 * 1000;
    TableLayout tbl;
    TableRow tbrow1;
    static int mode = 0;//1 for to//2 for from
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listpaymentin,null);
        return rootView;    
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences pref = getContext().getSharedPreferences(sharedfile, Context.MODE_PRIVATE);
        rentt=pref.getInt(Stenant,1000);
        rento=pref.getInt(Sowner,800);
        myDb = new DataBaseHelper(getActivity());

        tbl = (TableLayout) view.findViewById(R.id.tl_listpin);
        tbl.setClickable(true);
        spinner_from = (Spinner) view.findViewById(R.id.spinner_from_lispin);
        spinner_to = (Spinner) view.findViewById(R.id.spinner_to_listpin);
        bt_submit = (Button) view.findViewById(R.id.btsubmit_listpin);
        bt_exp=(Button)view.findViewById(R.id.btexp_listpayin);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        int temp = calendar.get(Calendar.MONTH);
        //System.out.println(temp);

        for(int i = 2010; i<2030; i++){
            years.add(String.valueOf(i));
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,years);

        spinner_from.setAdapter(arrayAdapter);
        spinner_to.setAdapter(arrayAdapter);

        bt_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String state = Environment.getExternalStorageState();
//check if the external directory is availabe for writing
                    File exportDir;
                    if (!Environment.MEDIA_MOUNTED.equals(state)) {
                        return;
                    } else {
                        exportDir = new File(Environment.getExternalStorageDirectory() + File.separator + "abc");
                    }

//if the external storage directory does not exists, we create it
                    if (!exportDir.exists()) {
                        exportDir.mkdirs();
                    }

                    String filename = "listpayin.csv";
                    File saveFile = new File(exportDir, filename);
                    FileWriter fw = new FileWriter(saveFile);
                    BufferedWriter bw = new BufferedWriter(fw);
                    TableLayout tb = (TableLayout) view.findViewById(R.id.tl_listpin);
                    if (tb.getChildCount() != 0) {

                        for(int i=0;i<tb.getChildCount();i++)
                        {
                            TableRow row= (TableRow) tb.getChildAt(i);
                            for(int j=0;j<row.getChildCount();j++)
                            {
                                TextView tv=(TextView)row.getChildAt(j);
                                if (i != row.getChildCount() - 1) {
                                    bw.write(tv.getText().toString()+",");
                                } else {
                                    bw.write(tv.getText().toString()+".");
                                }

                            }
                            bw.newLine();
                        }

                    }
                    bw.flush();
                    bw.close();
                    fw.close();
                    Toast.makeText(getActivity(), "exported successfully", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });




        //spinner
        spinner=(Spinner)view.findViewById(R.id.spinner_listpayin);
        Cursor x=myDb.getalldata();
       // System.out.println(x.getCount());
        categories.clear();
        if(x.getCount()!=0)
        {
            while(x.moveToNext())
            {
                categories.add(x.getString(0));
            }
        }
        categories.add("all");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,categories);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        int position = spinner.getSelectedItemPosition();
                        ptype = categories.get(+position);
                       // Toast.makeText(getActivity(), "You have selected " + categories.get(+position), Toast.LENGTH_SHORT).show();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stu
                       // Toast.makeText(getActivity(), "You have selected " + categories.get(1), Toast.LENGTH_SHORT).show();


                    }

                }
        );

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                grantotal=0;
                tjan=0;
                tfeb=0;
                tmarch=0;
                tapril=0;
                tmay=0;
                tdec=0;
                tjune=0;
                tjuly=0;
                taugust=0;
                tsept=0;
                toct=0;
                tnov=0;

                try { //tbl.setVisibility(View.VISIBLE);
                    tbl.removeViews(1, tbl.getChildCount() - 1);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.toString() + "1", Toast.LENGTH_LONG).show();
                }
                int a[] = new int[13];   //to know which column are vistited
                for (int k = 0; k <= 12; k++)
                    a[k] = 0;
                try {
                    List<String> bufferflatno = new ArrayList<String>();
                    List<Integer> buffertenant = new ArrayList<Integer>();
                    List<Integer>bufferflatid=new ArrayList<Integer>();
                    Cursor res = myDb.tnown_join_tbtenant();

                    String jan = null;
                    String feb = null;
                    String march = null;
                    String april = null;
                    String may = null;
                    String june = null;
                    String july = null;
                    String aug = null;
                    String sept = null;
                    String oct = null;
                    String nov = null;
                    String dec = null;
                    final String flatno = null;
                    int tenant = 0;
                    Cursor res1;
                    if (res.getCount() == 0) {
                        //no data
                        Toast.makeText(getActivity(), "nothing found in tb owner", Toast.LENGTH_LONG).show();

                    } else {
                        //flatno,tbowner.name,tid
                        while (res.moveToNext()) {
                            bufferflatno.add(res.getString(res.getColumnIndex("flatno")));
                            //buffertenant.add(res.getInt(res.getColumnIndex("tid")));
                            bufferflatid.add(res.getInt(res.getColumnIndex("id")));
                        }
                        res.close();
                    }

                    Iterator iteratorf = bufferflatno.iterator();
                    //Iterator iteratort = buffertenant.iterator();
                    Iterator iteratorflatid=bufferflatid.iterator();

                    while (iteratorf.hasNext()) {
                        Tamount = 0;
                        // Initializing a ShapeDrawable
                        ShapeDrawable sd = new ShapeDrawable();

                        // Specify the shape of ShapeDrawable
                        sd.setShape(new RectShape());

                        // Specify the border color of shape
                        sd.getPaint().setColor(Color.BLACK);

                        // Set the border width
                        sd.getPaint().setStrokeWidth(2f);

                        // Specify the style is a Stroke
                        sd.getPaint().setStyle(Paint.Style.STROKE);


                        tvflatno = new TextView(getActivity());

                        tvflatno.setTextSize(25);
                        //tvflatno.setPadding(5,5,5,5);
                        tvflatno.setBackgroundResource(R.drawable.shape);
                        tvflatno.setTextColor(Color.BLACK);


                        tvamount = new TextView(getActivity());

                        tvamount.setTextSize(25);
                        tvamount.setBackgroundResource(R.drawable.shape);
                        tvamount.setTextColor(Color.BLACK);


                        tvjan = new TextView(getActivity());

                        tvjan.setTextSize(25);
                        //tvjan.setBackground(sd);
                        //tvjan.setBackgroundColor(Color.RED);
                        tvjan.setTextColor(Color.BLACK);
                        tvjan.setBackgroundResource(R.drawable.green);

                        tvfeb = new TextView(getActivity());

                        tvfeb.setTextSize(25);
                        tvfeb.setBackgroundResource(R.drawable.green);
                        //  tvfeb.setBackgroundColor(Color.RED);
                        tvfeb.setTextColor(Color.BLACK);

                        tvmarch = new TextView(getActivity());

                        tvmarch.setTextSize(25);
                        tvmarch.setBackgroundResource(R.drawable.green);
                        // tvmarch.setBackgroundColor(Color.RED);
                        tvmarch.setTextColor(Color.BLACK);

                        tvapril = new TextView(getActivity());

                        tvapril.setTextSize(25);
                        tvapril.setBackgroundResource(R.drawable.green);
                        // tvapril.setBackgroundColor(Color.RED);
                        tvapril.setTextColor(Color.BLACK);

                        tvmay = new TextView(getActivity());

                        tvmay.setTextSize(25);
                        //tvmay.setBackground(sd);
                        tvmay.setBackgroundResource(R.drawable.green);
                        tvmay.setTextColor(Color.BLACK);

                        tvjune = new TextView(getActivity());

                        tvjune.setTextSize(25);
                        // tvjune.setBackground(sd);
                        tvjune.setBackgroundResource(R.drawable.green);
                        tvjune.setTextColor(Color.BLACK);

                        tvjuly = new TextView(getActivity());

                        tvjuly.setTextSize(25);
                        tvjuly.setBackgroundResource(R.drawable.green);
                        // tvjuly.setBackgroundColor(Color.RED);
                        tvjuly.setTextColor(Color.BLACK);

                        tvaugest = new TextView(getActivity());

                        tvaugest.setTextSize(25);
                        // tvaugest.setBackground(sd);
                        tvaugest.setBackgroundResource(R.drawable.green);
                        //  tvaugest.setTextColor(Color.BLACK);
                        tvaugest.setTextColor(Color.BLACK);

                        tvsept = new TextView(getActivity());

                        tvsept.setTextSize(25);
                        tvsept.setBackgroundResource(R.drawable.green);
                        //  tvsept.setBackgroundColor(Color.RED);
                        tvsept.setTextColor(Color.BLACK);

                        tvoct = new TextView(getActivity());

                        tvoct.setTextSize(25);
                        tvoct.setBackgroundResource(R.drawable.green);
                        //tvoct.setBackgroundColor(Color.RED);
                        tvoct.setTextColor(Color.BLACK);

                        tvnov = new TextView(getActivity());

                        tvnov.setTextSize(25);
                        tvnov.setBackgroundResource(R.drawable.green);
                        //  tvnov.setBackgroundColor(Color.RED);
                        tvnov.setTextColor(Color.BLACK);

                        tvdec = new TextView(getActivity());
                        tvdec.setTextSize(25);
                        tvdec.setGravity(Gravity.CENTER);
                        tvdec.setBackgroundResource(R.drawable.green);
                        //  tvdec.setBackgroundColor(Color.RED);
                        tvdec.setTextColor(Color.BLACK);

                        tvamount.setText(String.valueOf(Tamount));

                        int flatid=Integer.valueOf((Integer) iteratorflatid.next());
                        String tempflatno = iteratorf.next().toString();
                        //tenant = (Integer)iteratort.next();
                       // System.out.println(flatid + " , "+spinner_from.getText().toString() + " , "+spinner_to.getText().toString()+"  "+ptype.toString());


                        //
                        if(ptype.equalsIgnoreCase("all")) {
                            //System.out.println("hello showing all");
                            res1 = myDb.listpaymentin(flatid, spinner_from.getSelectedItem().toString(), spinner_to.getSelectedItem().toString());

                        }
                        else
                        {    Toast.makeText(getActivity(),ptype, Toast.LENGTH_LONG).show();
                            res1=myDb.listpaymentin(flatid, spinner_from.getSelectedItem().toString(), spinner_to.getSelectedItem().toString(),ptype);
                        }


                        //System.out.println("query out listpaymentin ::"+res1.getCount());
                        if (res1.getCount() == 0) {
                            //no data
                            Toast.makeText(getActivity(), "nothing found in tb paymentin ", Toast.LENGTH_LONG).show();

                        } else {rowno=10000;
                            //System.out.println("Count:"+res1.getCount());
                            while (res1.moveToNext()) {
                                //String date1 = null;
                                int paymentMonth;
                                //System.out.println("line"+456);
                                //System.out.println("Hello:"+res1.getString(res1.getColumnIndex("entryDate")));
                                //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                SimpleDateFormat formatter1 = new SimpleDateFormat("MM");
                                Date x;
                                //Convert string entryDate to Date Format
                                x= formatter.parse(res1.getString(res1.getColumnIndex("entryDate")));
                                //date1 = res1.getString(res1.getColumnIndex("entryDate")).substring(5,7);
                                //System.out.println("Date value:"+formatter.format(x));
                                paymentMonth=Integer.parseInt(formatter1.format(x));
                                //paymentMonth=strftime("%m",res1.getString(res1.getColumnIndex("entryDate")));
                                  //System.out.println("date=" + res1.getString(1) + " amount " + res1.getInt(0)+" date1 "+paymentMonth+" Month:"+res1.getString(3));
                                //System.out.println("date=" + res1.getString(1) + " amount " + res1.getInt(0)+" date1 "+paymentMonth);
                                switch(paymentMonth)
                                {
                                    //if (paymentMonth==1) {
                                    case 1:
                                        int n = res1.getInt(0);
                                        jan = String.valueOf(n);
                                        a[1] = 1;

                                        if(res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("cash"))
                                        {
                                            tvjan.setTextColor(Color.YELLOW);
                                        }
                                        else if(res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("online"))
                                        {
                                            tvjan.setTextColor(Color.WHITE);
                                        }
                                        else
                                        {
                                            tvjan.setTextColor(Color.BLUE);
                                        }
                                        break;
    //                                } else if (date1.equals("02")) {
                                    //} else if (paymentMonth==2) {
                                    case 2:
                                        feb = (String.valueOf(res1.getInt(0)));
                                        a[2] = 1;
                                        if(res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("cash"))
                                        {
                                            tvfeb.setTextColor(Color.YELLOW);
                                        }
                                        else if(res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("online"))
                                        {
                                            tvfeb.setTextColor(Color.WHITE);
                                        }
                                        else
                                        {
                                            tvfeb.setTextColor(Color.BLUE);
                                        }
                                        break;
    //                                } else if (date1.equals("03")) {
                                    //} else if (paymentMonth==3) {
                                    case 3:
                                        march = (String.valueOf(res1.getInt(0)));
                                        a[3] = 1;
                                        if(res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("cash"))
                                        {
                                            tvmarch.setTextColor(Color.YELLOW);
                                        }
                                        else if(res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("online"))
                                        {
                                            tvmarch.setTextColor(Color.WHITE);
                                        }
                                        else
                                        {
                                            tvmarch.setTextColor(Color.BLUE);
                                        }
                                        break;
    //                                } else if (date1.equals("04")) {
                                    //} else if (paymentMonth==4) {
                                    case 4:
                                        april = (String.valueOf(res1.getInt(0)));
                                        a[4] = 1;
                                        if(res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("cash"))
                                        {
                                            tvapril.setTextColor(Color.YELLOW);
                                        }
                                        else if(res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("online"))
                                        {
                                            tvapril.setTextColor(Color.WHITE);
                                        }
                                        else
                                        {
                                            tvapril.setTextColor(Color.BLUE);
                                        }
                                        break;
    //                                } else if (date1.equals("05")) {
                                    //} else if (paymentMonth==5) {
                                    case 5:
                                        may = (String.valueOf(res1.getInt(0)));
                                        a[5] = 1;
                                        if(res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("cash"))
                                        {
                                            tvmay.setTextColor(Color.YELLOW);
                                        }
                                        else if(res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("online"))
                                        {
                                            tvmay.setTextColor(Color.WHITE);
                                        }
                                        else
                                        {
                                            tvmay.setTextColor(Color.BLUE);
                                        }
                                        break;
    //                                } else if (date1.equals("06")) {
                                    //} else if (paymentMonth==6) {
                                    case 6:
                                        june = (String.valueOf(res1.getInt(0)));
                                        a[6] = 1;
                                        if(res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("cash"))
                                        {
                                            tvjune.setTextColor(Color.YELLOW);
                                        }
                                        else if(res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("online"))
                                        {
                                            tvjune.setTextColor(Color.WHITE);
                                        }
                                        else
                                        {
                                            tvjune.setTextColor(Color.BLUE);
                                        }
                                        break;
    //                                } else if (date1.equals("07")) {
                                    //} else if (paymentMonth==7) {
                                    case 7:
                                        july = (String.valueOf(res1.getInt(0)));
                                        a[7] = 1;
                                        if(res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("cash"))
                                        {
                                            tvjuly.setTextColor(Color.YELLOW);
                                        }
                                        else if(res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("online"))
                                        {
                                            tvjuly.setTextColor(Color.WHITE);
                                        }
                                        else
                                        {
                                            tvjuly.setTextColor(Color.BLUE);
                                        }
                                        break;
    //                                } else if (date1.equals("08")) {
                                    //} else if (paymentMonth==8) {
                                    case 8:
                                        aug = (String.valueOf(res1.getInt(0)));
                                        a[8] = 1;
                                        if(res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("cash"))
                                        {
                                            tvaugest.setTextColor(Color.YELLOW);
                                        }
                                        else if(res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("online"))
                                        {
                                            tvaugest.setTextColor(Color.WHITE);
                                        }
                                        else
                                        {
                                            tvaugest.setTextColor(Color.BLUE);
                                        }
                                        break;
    //                                } else if (date1.equals("09")) {
                                    //} else if (paymentMonth==9) {
                                    case 9:
                                        sept = (String.valueOf(res1.getInt(0)));
                                        a[9] = 1;
                                        if(res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("cash"))
                                        {
                                            tvsept.setTextColor(Color.YELLOW);
                                        }
                                        else if(res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("online"))
                                        {
                                            tvsept.setTextColor(Color.WHITE);
                                        }
                                        else
                                        {
                                            tvsept.setTextColor(Color.BLUE);
                                        }
                                        break;
    //                                } else if (date1.equals("10")) {
                                    //} else if (paymentMonth==10) {
                                    case 10:
                                        oct = (String.valueOf(res1.getInt(0)));
                                        a[10] = 1;
                                        if(res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("cash"))
                                        {
                                            tvoct.setTextColor(Color.YELLOW);
                                        }
                                        else if(res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("online"))
                                        {
                                            tvoct.setTextColor(Color.WHITE);
                                        }
                                        else
                                        {
                                            tvoct.setTextColor(Color.BLUE);
                                        }
                                        break;
    //                                } else if (date1.equals("11")) {
                                    //} else if (paymentMonth==11) {
                                    case 11:
                                        nov = (String.valueOf(res1.getInt(0)));
                                        a[11] = 1;
                                        if(res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("cash"))
                                        {
                                            tvnov.setTextColor(Color.YELLOW);
                                        }
                                        else if(res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("online"))
                                        {
                                            tvnov.setTextColor(Color.WHITE);
                                        }
                                        else
                                        {
                                            tvnov.setTextColor(Color.BLUE);
                                        }
                                        break;
    //                                } else if (date1.equals("12")) {
                                    //} else if (paymentMonth==12) {
                                    case 12:
                                        dec = (String.valueOf(res1.getInt(0)));
                                        a[12] = 1;
                                        if(res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("cash"))
                                        {
                                            tvdec.setTextColor(Color.YELLOW);
                                        }
                                        else if(res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("online"))
                                        {
                                            tvdec.setTextColor(Color.WHITE);
                                        }
                                        else
                                        {
                                            tvdec.setTextColor(Color.BLUE);
                                        }
                                }
                            }
                        }
                        //System.out.println("out of if else value of jan"+jan);
                        tvflatno.setText(tempflatno);

                        for (int i = 1; i <= 12; i++) {
                            if (a[i] == 0 && i == 1)
                                jan = "0";
                            else if (a[i] == 0 && i == 2)
                                feb = "0";
                            else if (a[i] == 0 && i == 3)
                                march = "0";
                            else if (a[i] == 0 && i == 4)
                                april = "0";
                            else if (a[i] == 0 && i == 5)
                                may = "0";
                            else if (a[i] == 0 && i == 6)
                                june = "0";
                            else if (a[i] == 0 && i == 7)
                                july = "0";
                            else if (a[i] == 0 && i == 8)
                                aug = "0";
                            else if (a[i] == 0 && i == 9)
                                sept = "0";
                            else if (a[i] == 0 && i == 10)
                                oct = "0";
                            else if (a[i] == 0 && i == 11)
                                nov = "0";
                            else if (a[i] == 0 && i == 12)
                                dec = "0";
                        }
                        for (int m = 0; m <= 12; m++) {
                            a[m] = 0;
                        }
                        //System.out.println("value jan1=" + Integer.parseInt(jan));
                        tbrow1 = new TableRow(getActivity());
                        Tamount = Integer.parseInt(jan) + Integer.parseInt(feb) + Integer.parseInt(march) + Integer.parseInt(april) + Integer.parseInt(may) + Integer.parseInt(dec);
                        //System.out.println("value total=" +Tamount);

                        Tamount = Tamount + Integer.parseInt(june) + Integer.parseInt(july) + Integer.parseInt(aug) + Integer.parseInt(sept) + Integer.parseInt(oct) + Integer.parseInt(nov);
                        //System.out.println("value total=" +Tamount);

                        grantotal=grantotal+Tamount;
                        tjan=tjan+Integer.parseInt(jan);
                        tfeb=tfeb+ Integer.parseInt(feb) ;
                        tmarch=tmarch+ Integer.parseInt(march);
                        tapril=tapril+ Integer.parseInt(april) ;
                        tmay=tmay+ Integer.parseInt(may);
                        tdec=tdec+ Integer.parseInt(dec);
                        tjune=tjune+Integer.parseInt(june);
                        tjuly=tjuly+ Integer.parseInt(july) ;
                        taugust=taugust+Integer.parseInt(aug);
                        tsept=tsept+ Integer.parseInt(sept);
                        toct=toct+ Integer.parseInt(oct);
                        tnov=tnov+ Integer.parseInt(nov);
                        if (tenant>0) {
                            int mnth = Tamount / rentt;
                            while (mnth > 0) {
                                if (mnth >= 12) {
                                    tvmarch.setBackgroundResource(R.drawable.red);
                                    mnth--;
                                } else if (mnth >= 11) {
                                    tvfeb.setBackgroundResource(R.drawable.red);
                                    mnth--;
                                } else if (mnth >= 10) {
                                    tvjan.setBackgroundResource(R.drawable.red);
                                    mnth--;
                                } else if (mnth >= 9) {
                                    tvdec.setBackgroundResource(R.drawable.red);
                                    mnth--;
                                } else if (mnth >= 8) {
                                    tvnov.setBackgroundResource(R.drawable.red);
                                    mnth--;
                                } else if (mnth >= 7) {
                                    tvoct.setBackgroundResource(R.drawable.red);
                                    mnth--;
                                } else if (mnth >= 6) {
                                    tvsept.setBackgroundResource(R.drawable.red);
                                    mnth--;
                                } else if (mnth >= 5) {
                                    tvaugest.setBackgroundResource(R.drawable.red);
                                    mnth--;
                                } else if (mnth >= 4) {
                                    tvjuly.setBackgroundResource(R.drawable.red);
                                    mnth--;
                                } else if (mnth >= 3) {
                                    tvjune.setBackgroundResource(R.drawable.red);
                                    mnth--;
                                } else if (mnth >= 2) {
                                    tvmay.setBackgroundResource(R.drawable.red);
                                    mnth--;
                                } else if (mnth >= 1) {
                                    tvapril.setBackgroundResource(R.drawable.red);
                                    mnth--;
                                    break;
                                }

                            }
                        } else {
                            int mnth = Tamount / rento;
                            while (mnth > 0) {
                                if (mnth >= 12) {
                                    tvmarch.setBackgroundResource(R.drawable.red);
                                    mnth--;
                                } else if (mnth >= 11) {
                                    tvfeb.setBackgroundResource(R.drawable.red);
                                    mnth--;
                                } else if (mnth >= 10) {
                                    tvjan.setBackgroundResource(R.drawable.red);
                                    mnth--;
                                } else if (mnth >= 9) {
                                    tvdec.setBackgroundResource(R.drawable.red);
                                    mnth--;
                                } else if (mnth >= 8) {
                                    tvnov.setBackgroundResource(R.drawable.red);
                                    mnth--;
                                } else if (mnth >= 7) {
                                    tvoct.setBackgroundResource(R.drawable.red);
                                    mnth--;
                                } else if (mnth >= 6) {
                                    tvsept.setBackgroundResource(R.drawable.red);
                                    mnth--;
                                } else if (mnth >= 5) {
                                    tvaugest.setBackgroundResource(R.drawable.red);
                                    mnth--;
                                } else if (mnth >= 4) {
                                    tvjuly.setBackgroundResource(R.drawable.red);
                                    mnth--;
                                } else if (mnth >= 3) {
                                    tvjune.setBackgroundResource(R.drawable.red);
                                    mnth--;
                                } else if (mnth >= 2) {
                                    tvmay.setBackgroundResource(R.drawable.red);
                                    mnth--;
                                } else if (mnth >= 1) {
                                    tvapril.setBackgroundResource(R.drawable.red);
                                    mnth--;
                                    break;
                                }

                            }
                        }

                        tvamount.setText(String.valueOf(Tamount));
                        tvjan.setText(jan);
                        tvfeb.setText(feb);
                        tvmarch.setText(march);
                        tvapril.setText(april);
                        tvmay.setText(may);
                        tvjune.setText(june);
                        tvjuly.setText(july);
                        tvaugest.setText(aug);
                        tvsept.setText(sept);
                        tvoct.setText(oct);
                        tvnov.setText(nov);
                        tvdec.setText(dec);

                        tbrow1.addView(tvflatno);
                        tbrow1.addView(tvamount);
                        tbrow1.addView(tvapril);
                        tbrow1.addView(tvmay);
                        tbrow1.addView(tvjune);
                        tbrow1.addView(tvjuly);
                        tbrow1.addView(tvaugest);
                        tbrow1.addView(tvsept);
                        tbrow1.addView(tvoct);
                        tbrow1.addView(tvnov);
                        tbrow1.addView(tvdec);
                        tbrow1.addView(tvjan);
                        tbrow1.addView(tvfeb);
                        tbrow1.addView(tvmarch);
                        tbl.addView(tbrow1);


                    }
                    TableRow tb=new TableRow(getActivity());
                    tb.setClickable(true);
                    TextView Grantt=new TextView(getActivity());
                    Grantt.setTextSize(25);
                    Grantt.setTextColor(Color.BLACK);
                    Grantt.setBackgroundResource(R.drawable.shape);
                    Grantt.setTypeface(null, Typeface.BOLD);
                    TextView dummy=new TextView(getActivity());
                    TextView t1jan=new TextView((getActivity()));
                    t1jan.setTextSize(25);
                    t1jan.setTextColor(Color.BLACK);
                    t1jan.setTypeface(null, Typeface.BOLD);
                    t1jan.setBackgroundResource(R.drawable.shape);
                    TextView t1feb=new TextView((getActivity()));
                    t1feb.setTextSize(25);
                    t1feb.setTextColor(Color.BLACK);
                    t1feb.setBackgroundResource(R.drawable.shape);
                    t1feb.setTypeface(null, Typeface.BOLD);
                    TextView t1march=new TextView((getActivity()));
                    t1march.setTextSize(25);
                    t1march.setTextColor(Color.BLACK);
                    t1march.setBackgroundResource(R.drawable.shape);
                    t1march.setTypeface(null, Typeface.BOLD);
                    TextView t1april=new TextView((getActivity()));
                    t1april.setTextSize(25);
                    t1april.setTextColor(Color.BLACK);
                    t1april.setBackgroundResource(R.drawable.shape);
                    t1april.setTypeface(null, Typeface.BOLD);
                    TextView t1may=new TextView((getActivity()));
                    t1may.setTextSize(25);
                    t1may.setTextColor(Color.BLACK);
                    t1may.setBackgroundResource(R.drawable.shape);
                    t1may.setTypeface(null, Typeface.BOLD);
                    TextView t1june=new TextView((getActivity()));
                    t1june.setTextSize(25);
                    t1june.setTextColor(Color.BLACK);
                    t1june.setBackgroundResource(R.drawable.shape);
                    t1june.setTypeface(null, Typeface.BOLD);
                    TextView t1july=new TextView((getActivity()));
                    t1july.setTextSize(25);
                    t1july.setTextColor(Color.BLACK);
                    t1july.setBackgroundResource(R.drawable.shape);
                    t1july.setTypeface(null, Typeface.BOLD);
                    TextView t1aug=new TextView((getActivity()));
                    t1aug.setTextSize(25);
                    t1aug.setTextColor(Color.BLACK);
                    t1aug.setBackgroundResource(R.drawable.shape);
                    t1aug.setTypeface(null, Typeface.BOLD);
                    TextView t1sept=new TextView((getActivity()));
                    t1sept.setTextSize(25);
                    t1sept.setTextColor(Color.BLACK);
                    t1sept.setBackgroundResource(R.drawable.shape);
                    t1sept.setTypeface(null, Typeface.BOLD);
                    TextView t1oct=new TextView((getActivity()));
                    t1oct.setTextSize(25);
                    t1oct.setTextColor(Color.BLACK);
                    t1oct.setBackgroundResource(R.drawable.shape);
                    t1oct.setTypeface(null, Typeface.BOLD);
                    TextView t1nov=new TextView((getActivity()));
                    t1nov.setTextSize(25);
                    t1nov.setTextColor(Color.BLACK);
                    t1nov.setBackgroundResource(R.drawable.shape);
                    t1nov.setTypeface(null, Typeface.BOLD);
                    TextView t1dec=new TextView((getActivity()));
                    t1dec.setTextSize(25);
                    t1dec.setTextColor(Color.BLACK);
                    t1dec.setBackgroundResource(R.drawable.shape);
                    t1dec.setTypeface(null, Typeface.BOLD);

                    dummy.setText("TOTAL");
                    dummy.setTextSize(25);
                    dummy.setTextColor(Color.BLACK);
                    dummy.setBackgroundResource(R.drawable.shape);
                    dummy.setTypeface(null, Typeface.BOLD);

                    //setting vale of textviews
                    Grantt.setText(String.valueOf(grantotal));
                    t1april.setText(String.valueOf(tapril));
                    t1may.setText(String.valueOf(tmay));
                    t1june.setText(String.valueOf(tjune));
                    t1july.setText(String.valueOf(tjuly));
                    t1aug.setText(String.valueOf(taugust));
                    t1sept.setText(String.valueOf(tsept));
                    t1oct.setText(String.valueOf(toct));
                    t1nov.setText(String.valueOf(tnov));
                    t1dec.setText(String.valueOf(tdec));
                    t1jan.setText(String.valueOf(tjan));
                    t1feb.setText(String.valueOf(tfeb));
                    t1march.setText(String.valueOf(tmarch));
                    //adding to table row
                    tb.addView(dummy);
                    tb.addView(Grantt);
                    tb.addView(t1april);
                    tb.addView(t1may);
                    tb.addView(t1june);
                    tb.addView(t1july);
                    tb.addView(t1aug);
                    tb.addView(t1sept);
                    tb.addView(t1oct);
                    tb.addView(t1nov);
                    tb.addView(t1dec);
                    tb.addView(t1jan);
                    tb.addView(t1feb);
                    tb.addView(t1march);
                    //adding to table
                    tbl.addView(tb);


                    int rowNumCount = tbl.getChildCount();
                    for(int count = 1; count < rowNumCount; count++) {
                        View v1 = tbl.getChildAt(count);
                        if(v1 instanceof TableRow) {
                            final TableRow clickRow = (TableRow)v1;
                            int rowCount = clickRow.getChildCount();
                            v1.setOnLongClickListener(new View.OnLongClickListener() {

                                @Override
                                public boolean onLongClick(View v1) {
                                    TableRow row = (TableRow) v1;
                                    final TextView tv = (TextView) row.getChildAt(0);
                                    TextView tv2 = (TextView) row.getChildAt(1);
                                    CharSequence text = "Lot VALUE Selected: " + tv.getText() + "**" + tv2.getText();
                                    int duration = Toast.LENGTH_SHORT;
                                    // Toast.makeText(getActivity(), text, duration).show();
                                    final  AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                    builder.setTitle("Details of Flatno="+tv.getText().toString());
                                    builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }

                                    });
                                    builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                           Intent i=new Intent(getActivity(),Detail_paymentin.class);
                                            i.putExtra("flatno",tv.getText().toString());
                                            i.putExtra("from", spinner_from.getSelectedItem().toString());
                                            i.putExtra("to", spinner_to.getSelectedItem().toString());
                                            i.putExtra("type",ptype);
                                            startActivity(i);
                                        }

                                    });
                                    builder.show();
                                    return true;
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.toString() + "2", Toast.LENGTH_LONG).show();
                }
            }// submit onclick
        });
        myDb.close();
    }
    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        Log.i("value is", "" + newVal);

    }
    public void show(int id) {
        final int id1 = id;
        final Dialog d = new Dialog(getActivity());
        d.setTitle("NumberPicker");
        d.setContentView(R.layout.dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(2200);
        np.setValue(year);
        np.setMinValue(2000);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        b2.setOnClickListener(new View.OnClickListener()

                              {
                                  @Override
                                  public void onClick(View v) {
                                      d.dismiss();
                                  }
                              }

        );
        d.show();


    }

}

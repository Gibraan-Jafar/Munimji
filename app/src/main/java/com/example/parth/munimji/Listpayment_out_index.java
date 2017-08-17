package com.example.parth.munimji;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by parth on 1/7/16.
 */
public class Listpayment_out_index extends Fragment implements NumberPicker.OnValueChangeListener{
    EditText et_to, et_from;
    Button bt_from, bt_to, bt_submit,exp;
    TextView tvcname, tvamount, tvjan, tvfeb, tvmarch, tvapril, tvmay, tvjune, tvjuly, tvaugest, tvsept, tvoct, tvnov, tvdec;
    final int DATE_PICKER_ID = 1111;
    String buffer[];
    DataBaseHelper mydb;
    static int mode = 0;
    int year, cname_count, Tamount,grantotal,tjan,tfeb,tmarch,tapril,tmay,tjune,tjuly,taugust,tsept,toct,tnov,tdec;
    TableLayout tbl, tblnew;
    TableRow tbrow1, tbr2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listpaymentout,null);
        return rootView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //mycode
        mydb = new DataBaseHelper(getActivity());


        exp=(Button)view.findViewById(R.id.btexp_listpayout);

        exp.setOnClickListener(new View.OnClickListener() {
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

                    String filename = "Listpayout.csv";
                    File saveFile = new File(exportDir, filename);
                    FileWriter fw = new FileWriter(saveFile);
                    BufferedWriter bw = new BufferedWriter(fw);

                    TableLayout tb = (TableLayout) view.findViewById(R.id.tl_listpout);
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

        //tvcname=(TextView)findViewById(R.id.tvcname_listpout);

        tbl = (TableLayout) view.findViewById(R.id.tl_listpout);
        // tbl.setVisibility(View.VISIBLE);
        tbl.setClickable(true);


        et_from = (EditText) view.findViewById(R.id.et_from_lispout);
        et_to = (EditText) view.findViewById(R.id.et_to_listpout);
        bt_from = (Button) view.findViewById(R.id.btdatepick_listpout);
        bt_to = (Button) view.findViewById(R.id.bt_to_listpout);
        bt_submit = (Button) view.findViewById(R.id.btsubmit_listpout);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        int temp = calendar.get(Calendar.MONTH);
        System.out.println(temp);
        if(temp<3){
            et_from.setText(String.valueOf(year - 1));
            et_to.setText(String.valueOf(year));}
        else
        {
            et_from.setText(String.valueOf(year ));
            et_to.setText(String.valueOf(year+1));
        }
        bt_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = 1;//to year
                show(v.getId());
            }
        });
        bt_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = 2;//from
                // showDialog(DATE_PICKER_ID);
                show(v.getId());

            }
        });

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {

                try {
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
                    // tbl.setVisibility(View.VISIBLE);
                    tbl.removeViews(1, tbl.getChildCount() - 1);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }
                int abc = 0;
                int a[] = new int[13];
                for (int k = 0; k <= 12; k++)
                    a[k] = 0;
                try {//Toast.makeText(getActivity(), "Submit button pressed", Toast.LENGTH_LONG).show();
                    //TextView tvf = (TextView) findViewById(R.id.tvf);
                    // tvcname.setText("");
                    List<Integer> array = new ArrayList<Integer>();
                    List<String> date = new ArrayList<String>();
                    List<Integer> amount = new ArrayList<Integer>();
                    Cursor res = mydb.distcname(et_from.getText().toString(), et_to.getText().toString());

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
                    String cname = null;

                    Cursor res1;
                    if (res.getCount() == 0) {
                        //no data
                        Toast.makeText(getActivity(), "nothing found", Toast.LENGTH_LONG).show();

                    } else {
                        System.out.println("Size:" + res.getCount());
                        //buffer[res.getCount()]=new String();
                        cname_count = res.getCount();
                        int i = 0;
                        while (res.moveToNext()) {


                            array.add(res.getInt(0));
                            // buffer[i] = res.getString(0);

                        }
                        System.out.println("Cid:" + array);
                        //tvcname.setText(array);
                        res.close();
                    }
                    System.out.println(array.size());
                    Iterator iterator = array.iterator();
                    while (iterator.hasNext()) {
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


                        tvcname = new TextView(getActivity());
                        tvcname.setWidth(80);
                        tvcname.setBackgroundResource(R.drawable.shape);
                        tvcname.setTextSize(25);
                        tvcname.setTextColor(Color.BLACK);

                        tvamount = new TextView(getActivity());
                        tvamount.setWidth(80);
                        tvamount.setBackgroundResource(R.drawable.shape);
                        tvamount.setTextSize(25);
                        tvamount.setTextColor(Color.BLACK);

                        tvjan = new TextView(getActivity());
                        tvjan.setWidth(60);
                        tvjan.setBackgroundResource(R.drawable.shape);
                        tvjan.setTextSize(25);
                        tvjan.setTextColor(Color.BLACK);

                        tvfeb = new TextView(getActivity());
                        tvfeb.setWidth(60);
                        tvfeb.setBackgroundResource(R.drawable.shape);
                        tvfeb.setTextSize(25);
                        tvfeb.setTextColor(Color.BLACK);

                        tvmarch = new TextView(getActivity());
                        tvmarch.setWidth(60);
                        tvmarch.setBackgroundResource(R.drawable.shape);
                        tvmarch.setTextSize(25);
                        tvmarch.setTextColor(Color.BLACK);

                        tvapril = new TextView(getActivity());
                        tvapril.setWidth(60);
                        tvapril.setBackgroundResource(R.drawable.shape);
                        tvapril.setTextSize(25);
                        tvapril.setTextColor(Color.BLACK);

                        tvmay = new TextView(getActivity());
                        tvmay.setWidth(60);
                        tvmay.setBackgroundResource(R.drawable.shape);
                        tvmay.setTextSize(25);
                        tvmay.setTextColor(Color.BLACK);

                        tvjune = new TextView(getActivity());
                        tvjune.setWidth(60);
                        tvjune.setBackgroundResource(R.drawable.shape);
                        tvjune.setTextSize(25);
                        tvjune.setTextColor(Color.BLACK);

                        tvjuly = new TextView(getActivity());
                        tvjuly.setWidth(60);
                        tvjuly.setBackgroundResource(R.drawable.shape);
                        tvjuly.setTextSize(25);
                        tvjuly.setTextColor(Color.BLACK);

                        tvaugest = new TextView(getActivity());
                        tvaugest.setWidth(60);
                        tvaugest.setBackgroundResource(R.drawable.shape);
                        tvaugest.setTextSize(25);
                        tvaugest.setTextColor(Color.BLACK);

                        tvsept = new TextView(getActivity());
                        tvsept.setWidth(60);
                        tvsept.setBackgroundResource(R.drawable.shape);
                        tvsept.setTextSize(25);
                        tvsept.setTextColor(Color.BLACK);

                        tvoct = new TextView(getActivity());
                        tvoct.setWidth(60);
                        tvoct.setBackgroundResource(R.drawable.shape);
                        tvoct.setTextSize(25);
                        tvoct.setTextColor(Color.BLACK);

                        tvnov = new TextView(getActivity());
                        tvnov.setWidth(60);
                        tvnov.setBackgroundResource(R.drawable.shape);
                        tvnov.setTextSize(25);
                        tvnov.setTextColor(Color.BLACK);

                        tvdec = new TextView(getActivity());
                        tvdec.setWidth(60);
                        tvdec.setTextSize(25);
                        tvdec.setTextColor(Color.BLACK);
                        tvdec.setGravity(Gravity.CENTER);
                        tvdec.setBackgroundResource(R.drawable.shape);
                        tvamount.setText(String.valueOf(Tamount));
                        tvamount.setTypeface(null, Typeface.BOLD);


                       int x = (Integer)iterator.next();
                        String temp=null;
                        try {

                            Cursor cursor=mydb.tb_owner_getcompanyname(x);
                            if(cursor.moveToNext())
                                temp=cursor.getString(cursor.getColumnIndex("name"));
                            // quarter=rb_Quarter.getText().toString();
                            // quarteri=Integer.parseInt(quarter);
                        }catch(Exception e)
                        {
                            System.out.println("error name"+e.toString());
                        }

                        res1 = mydb.listpaymentout(x, et_from.getText().toString(), et_to.getText().toString());

                        System.out.println("out of the dbhelper");
                        if (res1.getCount() == 0) {
                            //no data
                            Toast.makeText(getActivity(), "nothing found", Toast.LENGTH_LONG).show();

                        } else {
                            while (res1.moveToNext()) {

                                String date1 = res1.getString(res1.getColumnIndex("transdate")).substring(5, 7);
                                System.out.println(date1);
                                abc++;

                                if (date1.equals("01")) {
                                    int n = res1.getInt(0);
                                    //Tamount=+n;
                                    jan = String.valueOf(n);
                                    // tvjan.setText(n12);
                                    // tvcname.append(iteratorcname.next().toString());
                                    a[1] = 1;
                                    if (res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("cash")) {
                                        tvjan.setTextColor(Color.RED);
                                    } else if (res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("online")) {
                                        tvjan.setTextColor(Color.GREEN);
                                    } else {
                                        tvjan.setTextColor(Color.BLUE);
                                    }

                                } else if (date1.equals("02")) {
                                    feb = (String.valueOf(res1.getInt(0)));
                                    // Tamount=+res1.getInt(0);
                                    a[2] = 1;
                                    if (res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("cash")) {
                                        tvfeb.setTextColor(Color.RED);
                                    } else if (res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("online")) {
                                        tvfeb.setTextColor(Color.GREEN);
                                    } else {
                                        tvfeb.setTextColor(Color.BLUE);
                                    }


                                } else if (date1.equals("03")) {
                                    march = (String.valueOf(res1.getInt(0)));
                                    // Tamount=+res1.getInt(0);
                                    // tvcname.setText(iteratorcname.next().toString());
                                    a[3] = 1;

                                    if (res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("cash")) {
                                        tvmarch.setTextColor(Color.RED);
                                    } else if (res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("online")) {
                                        tvmarch.setTextColor(Color.GREEN);
                                    } else {
                                        tvmarch.setTextColor(Color.BLUE);
                                    }

                                } else if (date1.equals("04")) {
                                    april = (String.valueOf(res1.getInt(0)));
                                    // Tamount=+res1.getInt(0);
                                    // tvcname.setText(iteratorcname.next().toString());
                                    a[4] = 1;

                                    if (res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("cash")) {
                                        tvapril.setTextColor(Color.RED);
                                    } else if (res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("online")) {
                                        tvapril.setTextColor(Color.GREEN);
                                    } else {
                                        tvapril.setTextColor(Color.BLUE);
                                    }

                                } else if (date1.equals("05")) {
                                    may = (String.valueOf(res1.getInt(0)));
                                    // Tamount=+res1.getInt(0);
                                    // tvcname.setText(iteratorcname.next().toString());
                                    a[5] = 1;
                                    if (res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("cash")) {
                                        tvmay.setTextColor(Color.RED);
                                    } else if (res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("online")) {
                                        tvmay.setTextColor(Color.GREEN);
                                    } else {
                                        tvmay.setTextColor(Color.BLUE);
                                    }

                                } else if (date1.equals("06")) {
                                    june = (String.valueOf(res1.getInt(0)));
                                    // Tamount=+res1.getInt(0);
                                    // tvcname.setText(iteratorcname.next().toString());
                                    a[6] = 1;
                                    if (res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("cash")) {
                                        tvjune.setTextColor(Color.RED);
                                    } else if (res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("online")) {
                                        tvjune.setTextColor(Color.GREEN);
                                    } else {
                                        tvjune.setTextColor(Color.BLUE);
                                    }

                                } else if (date1.equals("07")) {
                                    july = (String.valueOf(res1.getInt(0)));
                                    //Tamount=+res1.getInt(0);
                                    // tvcname.setText(iteratorcname.next().toString());
                                    a[7] = 1;
                                    if (res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("cash")) {
                                        tvjuly.setTextColor(Color.RED);
                                    } else if (res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("online")) {
                                        tvjuly.setTextColor(Color.GREEN);
                                    } else {
                                        tvjuly.setTextColor(Color.BLUE);
                                    }

                                } else if (date1.equals("08")) {
                                    aug = (String.valueOf(res1.getInt(0)));
                                    // Tamount=+res1.getInt(0);
                                    //tvcname.setText(iteratorcname.next().toString());
                                    a[8] = 1;

                                    if (res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("cash")) {
                                        tvaugest.setTextColor(Color.RED);
                                    } else if (res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("online")) {
                                        tvaugest.setTextColor(Color.GREEN);
                                    } else {
                                        tvaugest.setTextColor(Color.BLUE);
                                    }

                                } else if (date1.equals("09")) {
                                    sept = (String.valueOf(res1.getInt(0)));
                                    // Tamount=+res1.getInt(0);
                                    //  tvcname.setText(iteratorcname.next().toString());
                                    a[9] = 1;
                                    if (res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("cash")) {
                                        tvsept.setTextColor(Color.RED);
                                    } else if (res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("online")) {
                                        tvsept.setTextColor(Color.GREEN);
                                    } else {
                                        tvsept.setTextColor(Color.BLUE);
                                    }

                                } else if (date1.equals("10")) {
                                    oct = (String.valueOf(res1.getInt(0)));
                                    //Tamount=+res1.getInt(0);
                                    // tvcname.setText(iteratorcname.next().toString());
                                    a[10] = 1;
                                    if (res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("cash")) {
                                        tvoct.setTextColor(Color.RED);
                                    } else if (res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("online")) {
                                        tvoct.setTextColor(Color.GREEN);
                                    } else {
                                        tvoct.setTextColor(Color.BLUE);
                                    }

                                } else if (date1.equals("11")) {
                                    nov = (String.valueOf(res1.getInt(0)));
                                    //Tamount=+res1.getInt(0);
                                    // tvcname.setText(iteratorcname.next().toString());
                                    a[11] = 1;
                                    if (res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("cash")) {
                                        tvnov.setTextColor(Color.RED);
                                    } else if (res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("online")) {
                                        tvnov.setTextColor(Color.GREEN);
                                    } else {
                                        tvnov.setTextColor(Color.BLUE);
                                    }

                                } else if (date1.equals("12")) {
                                    dec = (String.valueOf(res1.getInt(0)));
                                    //Tamount=+res1.getInt(0);

                                    // tvcname.setText(iteratorcname.next().toString());
                                    a[12] = 1;

                                    if (res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("cash")) {
                                        tvdec.setTextColor(Color.RED);
                                    } else if (res1.getString(res1.getColumnIndex("MODE")).equalsIgnoreCase("online")) {
                                        tvdec.setTextColor(Color.GREEN);
                                    } else {
                                        tvdec.setTextColor(Color.BLUE);
                                    }

                                }

                            }
                        }
                        int count = 0;

                        tvcname.setText(temp);
                        for (int i = 1; i <= 12; i++) {
                            if (a[i] == 0 && i == 1) {
                                jan = "0";
                            } else if (a[i] == 0 && i == 2) {
                                feb = "0";
                            } else if (a[i] == 0 && i == 3) {
                                march = "0";
                            } else if (a[i] == 0 && i == 4) {
                                april = "0";
                            } else if (a[i] == 0 && i == 5) {
                                may = "0";
                            } else if (a[i] == 0 && i == 6) {
                                june = "0";
                            } else if (a[i] == 0 && i == 7) {
                                july = "0";
                            } else if (a[i] == 0 && i == 8) {
                                aug = "0";
                            } else if (a[i] == 0 && i == 9) {
                                sept = "0";
                            } else if (a[i] == 0 && i == 10) {
                                oct = "0";
                            } else if (a[i] == 0 && i == 11) {
                                nov = "0";
                            } else if (a[i] == 0 && i == 12) {
                                dec = "0";
                            }
                        }
                        for (int m = 0; m <= 12; m++) {
                            a[m] = 0;
                        }
                        tbrow1 = new TableRow(getActivity());
                        int id = 0;
                        tbrow1.setId(id);


                        Tamount = Integer.parseInt(jan) + Integer.parseInt(feb) + Integer.parseInt(march) + Integer.parseInt(april) + Integer.parseInt(may) + Integer.parseInt(dec);
                        Tamount = Tamount + Integer.parseInt(june) + Integer.parseInt(july) + Integer.parseInt(aug) + Integer.parseInt(sept) + Integer.parseInt(oct) + Integer.parseInt(nov);


                        grantotal = grantotal + Tamount;
                        tjan = tjan + Integer.parseInt(jan);
                        tfeb = tfeb + Integer.parseInt(feb);
                        tmarch = tmarch + Integer.parseInt(march);
                        tapril = tapril + Integer.parseInt(april);
                        tmay = tmay + Integer.parseInt(may);
                        tdec = tdec + Integer.parseInt(dec);
                        tjune = tjune + Integer.parseInt(june);
                        tjuly = tjuly + Integer.parseInt(july);
                        taugust = taugust + Integer.parseInt(aug);
                        tsept = tsept + Integer.parseInt(sept);
                        toct = toct + Integer.parseInt(oct);
                        tnov = tnov + Integer.parseInt(nov);


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

                        tbrow1.addView(tvcname);
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
                        tbl.setClickable(true);

                    }
                    TableLayout tbl1 = (TableLayout) view.findViewById(R.id.tl_listpout);
                    TableRow tb = new TableRow(getActivity());

                    TextView Grantt = new TextView(getActivity());
                    Grantt.setTextSize(25);
                    Grantt.setBackgroundResource(R.drawable.shape);
                    Grantt.setTypeface(null, Typeface.BOLD);
                    Grantt.setTextColor(Color.BLACK);

                    TextView dummy = new TextView(getActivity());

                    TextView t1jan = new TextView((getActivity()));
                    t1jan.setTextSize(25);
                    t1jan.setTextColor(Color.BLACK);
                    t1jan.setTypeface(null, Typeface.BOLD);
                    t1jan.setBackgroundResource(R.drawable.shape);

                    TextView t1feb = new TextView((getActivity()));
                    t1feb.setTextSize(25);
                    t1feb.setTextColor(Color.BLACK);
                    t1feb.setBackgroundResource(R.drawable.shape);
                    t1feb.setTypeface(null, Typeface.BOLD);

                    TextView t1march = new TextView((getActivity()));
                    t1march.setTextSize(25);
                    t1march.setTextColor(Color.BLACK);
                    t1march.setBackgroundResource(R.drawable.shape);
                    t1march.setTypeface(null, Typeface.BOLD);

                    TextView t1april = new TextView((getActivity()));
                    t1april.setTextSize(25);
                    t1april.setTextColor(Color.BLACK);
                    t1april.setBackgroundResource(R.drawable.shape);
                    t1april.setTypeface(null, Typeface.BOLD);

                    TextView t1may = new TextView((getActivity()));
                    t1may.setTextSize(25);
                    t1may.setTextColor(Color.BLACK);
                    t1may.setBackgroundResource(R.drawable.shape);
                    t1may.setTypeface(null, Typeface.BOLD);

                    TextView t1june = new TextView((getActivity()));
                    t1june.setTextSize(25);
                    t1june.setTextColor(Color.BLACK);
                    t1june.setBackgroundResource(R.drawable.shape);
                    t1june.setTypeface(null, Typeface.BOLD);

                    TextView t1july = new TextView((getActivity()));
                    t1july.setTextSize(25);
                    t1july.setTextColor(Color.BLACK);
                    t1july.setBackgroundResource(R.drawable.shape);
                    t1july.setTypeface(null, Typeface.BOLD);

                    TextView t1aug = new TextView((getActivity()));
                    t1aug.setTextSize(25);
                    t1aug.setTextColor(Color.BLACK);
                    t1aug.setBackgroundResource(R.drawable.shape);
                    t1aug.setTypeface(null, Typeface.BOLD);

                    TextView t1sept = new TextView((getActivity()));
                    t1sept.setTextSize(25);
                    t1sept.setTextColor(Color.BLACK);
                    t1sept.setBackgroundResource(R.drawable.shape);
                    t1sept.setTypeface(null, Typeface.BOLD);

                    TextView t1oct = new TextView((getActivity()));
                    t1oct.setTextSize(25);
                    t1oct.setTextColor(Color.BLACK);
                    t1oct.setBackgroundResource(R.drawable.shape);
                    t1oct.setTypeface(null, Typeface.BOLD);

                    TextView t1nov = new TextView((getActivity()));
                    t1nov.setTextSize(25);
                    t1nov.setTextColor(Color.BLACK);
                    t1nov.setBackgroundResource(R.drawable.shape);
                    t1nov.setTypeface(null, Typeface.BOLD);

                    TextView t1dec = new TextView((getActivity()));
                    t1dec.setTextSize(25);
                    t1dec.setTextColor(Color.BLACK);
                    t1dec.setBackgroundResource(R.drawable.shape);
                    t1dec.setTypeface(null, Typeface.BOLD);

                    dummy.setText("TOTAL");
                    dummy.setTextSize(25);
                    dummy.setBackgroundResource(R.drawable.shape);
                    dummy.setTypeface(null, Typeface.BOLD);
                    dummy.setTextColor(Color.BLACK);


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
                    tbl1.addView(tb);

                    int rowNumCount = tbl.getChildCount();
                    for (int count = 1; count < rowNumCount; count++) {
                        View v1 = tbl.getChildAt(count);
                        if (v1 instanceof TableRow) {
                            final TableRow clickRow = (TableRow) v1;
                            int rowCount = clickRow.getChildCount();
                            v1.setOnLongClickListener(new View.OnLongClickListener() {

                                @Override
                                public boolean onLongClick(View v1) {
                                    TableRow row = (TableRow) v1;
                                    final TextView tv = (TextView) row.getChildAt(0);
                                    TextView tv2 = (TextView) row.getChildAt(1);
                                    CharSequence text = "Lot VALUE Selected: " + tv.getText() + "**" + tv2.getText();
//                                        int duration = Toast.LENGTH_SHORT;
                                    //                                      Toast.makeText(getActivity(), text, duration).show();

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
                                            Intent i=new Intent(getActivity(),Detail_payout.class);
                                            i.putExtra("flatno",tv.getText().toString());
                                            i.putExtra("from",et_from.getText().toString());
                                            i.putExtra("to",et_to.getText().toString());
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
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        mydb.close();
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
                if (mode == 1) {
                    et_to.setText(String.valueOf(np.getValue()));
                    d.dismiss();
                } else if (mode == 2) {
                    et_from.setText(String.valueOf(np.getValue()));
                    d.dismiss();
                } else {
                    Toast.makeText(getActivity(), "pls select year", Toast.LENGTH_LONG).show();

                }
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

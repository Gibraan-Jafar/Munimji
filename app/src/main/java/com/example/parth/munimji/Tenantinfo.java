package com.example.parth.munimji;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Calendar;



/**
 * Created by parth on 7/7/16.
 */
public class Tenantinfo extends Fragment {
    int pos = 0;
    Button btdatejoin,btdateleft,bt_add_vechno;
    EditText etname,etleftdate,etjoindate,etmobile,etemail,etvechnum;
    AutoCompleteTextView etflatno;
    int year,month,day;//for date picker
    int year1,month1,day1;//for date picker
    String name;
    String flatno,mobile,email,vechnum;
    LinearLayout hzll, vertll;
    //Button btsubmit;

    MenuItem menuItem;

    int id,tid;
    String joindate,leftdate;

    DataBaseHelper myDb;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tenant_info,null);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menuItem = (MenuItem) menu.findItem(R.id.action_save);
        menuItem.setVisible(true);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {



        myDb=new DataBaseHelper(getActivity());
        super.onViewCreated(view, savedInstanceState);
        bt_add_vechno = (Button) view.findViewById(R.id.bt_addvech_newtenant);

        etemail=(EditText)view.findViewById(R.id.et_email_tenant);
        etmobile=(EditText)view.findViewById(R.id.et_mobile_tenant);
        etvechnum=(EditText)view.findViewById(R.id.et_vech_num_tenant);
        etname=(EditText)view.findViewById(R.id.et_name_tenant);
        etflatno=(AutoCompleteTextView) view.findViewById(R.id.et_flatno_tenant);
        etjoindate=(EditText)view.findViewById(R.id.et_joindate_tenant);
        etleftdate=(EditText)view.findViewById(R.id.et_leftdate_tenant);
        btdatejoin=(Button)view.findViewById(R.id.bt_joindate_tenant);
        btdateleft=(Button)view.findViewById(R.id.bt_leftdate_tenant);
        //btsubmit=(Button)view.findViewById(R.id.bt_submit_tenant);


        vertll=(LinearLayout)view.findViewById(R.id.ly_add_numvech_tenant);
        Cursor res=myDb.distflatno();
        String[] result = new String[res.getCount()+1];
        res.moveToFirst();
        result[res.getCount()]="";
        for(int i = 0; i < res.getCount(); i++)
        {   System.out.println(res.getString(0));
            //You can here manipulate a single string as you please
            result[i] = res.getString(0);
            res.moveToNext();
        }
        ArrayAdapter<String> adapter  = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, result);
        etflatno.setThreshold(1);
        etflatno.setAdapter(adapter);

        Calendar calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        day=calendar.get(Calendar.DATE);
        month=calendar.get(Calendar.MONTH);
        month++;


        year1=calendar.get(Calendar.YEAR);
        day1=calendar.get(Calendar.DATE);
        month1=calendar.get(Calendar.MONTH);
        month1++;
        etjoindate.setText(day+"-"+month+"-"+year);
        etleftdate.setText(day1+"-"+month1+"-"+year1);

        btdatejoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                etjoindate.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, year, month, day);
                dpd.show();

            }
        });
        btdateleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                etleftdate.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, year1, month1, day1);
                dpd.show();

            }
        });
        bt_add_vechno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos++;
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                EditText textView = new EditText(getActivity());
                textView.setEms(4);
                textView.setId(getId());

                Button button = new Button(getActivity());
                //button.setLayoutParams(layoutParams);
                button.setBackgroundResource(R.drawable.minus);
                button.setId(getId());
                int size=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, getResources().getDisplayMetrics());
                button.setLayoutParams(new LinearLayout.LayoutParams(size,size));

                //button.setBackgroundResource(R.drawable.refresh);
                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.addView(textView);
                linearLayout.addView(button);
                vertll.addView(linearLayout);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vertll.removeViewAt(pos);
                        pos--;
                    }
                });

            }
        });
       /* btsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    name = etname.getText().toString().trim();
                    flatno = etflatno.getText().toString().trim();
                    email=etemail.getText().toString().trim();
                    mobile=etmobile.getText().toString().trim();
                    vechnum=etvechnum.getText().toString().trim();
                    Cursor cursor = myDb.tb_owner_getid(flatno);
                    if (cursor.moveToNext())
                        id = cursor.getInt(cursor.getColumnIndex("id"));



                    String a, b;
                    a = String.format("%02d", month);
                    b = String.format("%02d", day);

                    String a1, b1;
                    a1 = String.format("%02d", month1);
                    b1 = String.format("%02d", day1);

                    joindate = year + "-" + a + "-" + b;
                    leftdate = year1 + "-" + a1 + "-" + b1;

                    boolean ans1=false,ans2=false,ans3=false,ans = myDb.tb_tenant_ins(id, name, joindate, leftdate);
                    Cursor cursor1=myDb.tb_tenant_getid(id);
                    if (cursor1.moveToNext())
                        tid = cursor1.getInt(cursor1.getColumnIndex("tid"));
                    if(ans==true)
                    {
                        etflatno.setText("");
                        etname.setText("");
                        etmobile.setText("");
                        etvechnum.setText("");
                        etemail.setText("");

                            ans1 = myDb.tb_persinfo_ins(id, 1, mobile);
                            ans2 = myDb.tb_persinfo_ins(id, 2, email);
                            ans3 = myDb.tb_persinfo_ins(id, 3, vechnum);

                    }
                    Toast.makeText(getContext(),""+ans1+"*"+ans2+"*"+ans3,Toast.LENGTH_SHORT).show();
                }catch (Exception e)
                {
                    System.out.println(e.toString()+123);
                }
            }
        });*/
        Button bt=(Button)view.findViewById(R.id.bt_show_teant);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData("tbtenant");
                if (res.getCount() == 0) {
                    //no data
                    showMessage("error", "nothing found");

                }
                //        db.execSQL("create table tbtenant (tid INTEGER PRIMARY KEY,flatid INTEGER,name TEXT,joindate TEXT,leftdate TEXT);");

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("id:" + res.getInt(res.getColumnIndex("tid")) );
                    buffer.append("Flatno:" + res.getInt(res.getColumnIndex("flatid")));
                    buffer.append("name:" + res.getString(res.getColumnIndex("name")) );
                    buffer.append("date:" + res.getString(res.getColumnIndex("joindate")) + "\n");

                }
                showMessage("Data:\n", buffer.toString());
            }
        });
        myDb.close();
    }

    public void saveInfo(){
        try {
            name = etname.getText().toString().trim();
            flatno = etflatno.getText().toString().trim();
            email=etemail.getText().toString().trim();
            mobile=etmobile.getText().toString().trim();
            vechnum=etvechnum.getText().toString().trim();
            Cursor cursor = myDb.tb_owner_getid(flatno);
            if (cursor.moveToNext())
                id = cursor.getInt(cursor.getColumnIndex("id"));



            String a, b;
            a = String.format("%02d", month);
            b = String.format("%02d", day);

            String a1, b1;
            a1 = String.format("%02d", month1);
            b1 = String.format("%02d", day1);

            joindate = year + "-" + a + "-" + b;
            leftdate = year1 + "-" + a1 + "-" + b1;

            boolean ans1=false,ans2=false,ans3=false,ans = myDb.tb_tenant_ins(id, name, joindate, leftdate);
            Cursor cursor1=myDb.tb_tenant_getid(id);
            if (cursor1.moveToNext())
                tid = cursor1.getInt(cursor1.getColumnIndex("tid"));
            if(ans==true)
            {
                etflatno.setText("");
                etname.setText("");
                etmobile.setText("");
                etvechnum.setText("");
                etemail.setText("");

                ans1 = myDb.tb_persinfo_ins(id, 1, mobile);
                ans2 = myDb.tb_persinfo_ins(id, 2, email);
                ans3 = myDb.tb_persinfo_ins(id, 3, vechnum);

            }
            Toast.makeText(getContext(),""+ans1+"*"+ans2+"*"+ans3,Toast.LENGTH_SHORT).show();
        }catch (Exception e)
        {
            System.out.println(e.toString()+123);
        }
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

}

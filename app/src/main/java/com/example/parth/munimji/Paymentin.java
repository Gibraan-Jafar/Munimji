package com.example.parth.munimji;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pdfjet.Color;
import com.pdfjet.CoreFont;
import com.pdfjet.Font;
import com.pdfjet.Letter;
import com.pdfjet.PDF;
import com.pdfjet.Page;
import com.pdfjet.TextLine;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by parth on 11/5/16.
 */
public class Paymentin extends  Fragment{
    SharedPreferences pref=null;
    String sharedfile="iniSettings";
    String Suserknow="userknow";
    String Sowner="owner";
    String Stenant="tenant";
    int personid;
    int mode=2;//1 for cheque 2 for cash 3 for online
    int year,month,day,year1,month1,day1;//for date picker
    static final int DATE_PICKER_ID = 1111;
    EditText etamount,etcheckno, etbranck,etransid,datepick,etcheckdate,etOnlineDate,etEntryDate;
    RelativeLayout rlcheque, rlonline;
    AutoCompleteTextView etmid;
    Button btsubmit,btlist, btcheque,btcash,btonline,btdatepic,btcheckdate, btonlineDate;
    TextView tvPersonName;
    boolean error;
    String mid=null;
    String amount=null;
    String checkno=null;
    String transid=null;
    String branch=null;
    String mode1=null;
    String ptype=null;
    String checkdated=null;

    int midi=0;
    String checknoi="";
    int transidi=0;
    int amounti=0;
    int quarteri=0;

    Spinner spinner;

    DataBaseHelper myDb;

    List<String> categories = new ArrayList<String>();



    @Nullable
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.paymentin_index,null);
        myDb=new DataBaseHelper(getActivity());
        categories.clear();
        Cursor x=myDb.getalldata();
        if(x.getCount()!=0)
        {
            while(x.moveToNext())
            {
                categories.add(x.getString(1));
            }
        }
        return rootView;
}

    @Override
    public void onResume() {
        super.onResume();
        categories.clear();
        Cursor x=myDb.getalldata();
        if(x.getCount()!=0)
        {
            while(x.moveToNext())
            {
                categories.add(x.getString(1));
            }
        }

    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        //lv=(ExpandableListView)findViewById(R.id.elv_payin);
        SharedPreferences pref = getContext().getSharedPreferences(sharedfile, Context.MODE_PRIVATE);

        boolean first=pref.getBoolean("firstrun",true);
        if(first==true)
        {
            pref.edit().putBoolean("firstrun",false).apply();
            pref.edit().putInt(Sowner,800).apply();
            pref.edit().putInt(Stenant,1000).apply();

            Toast.makeText(getActivity(),"firstrun",Toast.LENGTH_SHORT).show();
            DataBaseHelper myDb=new DataBaseHelper(getActivity());
            boolean ans=myDb.insertData("MAINTENANCE");


        }


        spinner=(Spinner)view.findViewById(R.id.spinner_payin);


        /*categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");*/
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
                        ptype=categories.get(+position);
                        //Toast.makeText(getActivity(), "You have selected " + categories.get(+position), Toast.LENGTH_SHORT).show();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }

                }
        );
        tvPersonName=(TextView)view.findViewById(R.id.tv_personName);
        etEntryDate=(EditText) view.findViewById(R.id.etdatepicker_pin);
        etcheckdate=(EditText)view.findViewById(R.id.et_check_date_pin);
        etOnlineDate=(EditText)view.findViewById(R.id.et_online_date);
        btonlineDate=(Button)view.findViewById(R.id.bt_online_date);
        btcheckdate=(Button)view.findViewById(R.id.bt_check_date_pin);
        //button for listing and submitting
        btsubmit=(Button)view.findViewById(R.id.btsubmit_pin);
        //btlist=(Button)view.findViewById(R.id.bt_List_pin);
        //buttons for mode selection
        btcheque=(Button)view.findViewById(R.id.bt_cheque_pin);
        btcash=(Button)view.findViewById(R.id.btcash_pin);
        btonline=(Button)view.findViewById(R.id.btonline_pin);
        //for changing date
        btdatepic=(Button)view.findViewById(R.id.btdate);
        mode1=btcash.getText().toString().trim();
        //payment details
        etmid=(AutoCompleteTextView)view.findViewById(R.id.etmid_pin);
        Cursor res=myDb.distflatno();
        String[] result = new String[res.getCount()+1];
        res.moveToFirst();
        result[res.getCount()]="";
        for(int i = 0; i < res.getCount(); i++)
        {   //System.out.println(res.getString(0));
            //You can here manipulate a single string as you please
            result[i] = res.getString(0);
            res.moveToNext();
        }
        ArrayAdapter<String> adapter  = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, result);
        etmid.setThreshold(1);

        //Set adapter to AutoCompleteTextView
        etmid.setAdapter(adapter);


        btcash.setBackgroundColor(getResources().getColor(R.color.darkgreen));
        btonline.setBackgroundColor(getResources().getColor(R.color.lightgreen));
        btcheque.setBackgroundColor(getResources().getColor(R.color.lightgreen));





        etamount=(EditText)view.findViewById(R.id.etamount_pin);
        //for quarter selection
        //rg_quarter=(RadioGroup)findViewById(R.id.rgquarter);


        datepick=(EditText)view.findViewById(R.id.etdatepicker_pin);
        //to set current date value in date field
        Calendar calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        day=calendar.get(Calendar.DATE);
        month=calendar.get(Calendar.MONTH);
        //month++;
        datepick.setText(new StringBuilder()
                // Month is 0 based, just add 1bt__tenant
                .append(day).append("-").append(month).append("-")
                .append(year).append(" "));


/*        etmid.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        System.out.println("Hello Left Flat No");
                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                actionId == EditorInfo.IME_ACTION_DONE ||
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            Toast.makeText(getActivity(),"Hello",Toast.LENGTH_LONG).show();
                        }
                        return false; // pass on to other listeners.
                    }
                });

*/
        etmid.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
    /* When focus is lost check that the text field
    * has valid values.
    */
                if (!hasFocus) {
                   // System.out.println("Flat Number:"+etmid.getText());
                    Cursor cursor=myDb.getOwnerName(etmid.getText().toString());
                    if (cursor.moveToNext())
                        tvPersonName.setText(cursor.getString(0));
                        //System.out.println("Person name:"+cursor.getString(0));
                }
            }
        });
        //datepick button onclick listner

        btdatepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                datepick.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, year, month, day);
                dpd.show();

            }
        });

        etcheckdate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day).append("-").append(month).append("-")
                .append(year).append(" "));
        btcheckdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                etcheckdate.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, year1, month1, day1);
                dpd.show();
            }
        });

        etOnlineDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day).append("-").append(month).append("-")
                .append(year).append(" "));
        btonlineDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                etOnlineDate.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, year1, month1, day1);
                dpd.show();

            }
        });

        //points to extra layout
        rlcheque=(RelativeLayout)view.findViewById(R.id.ly_cheque_pin);

        rlonline=(RelativeLayout)view.findViewById(R.id.ly_online_pin);
        rlcheque.setVisibility(View.GONE);
        rlonline.setVisibility(View.GONE);





        //onclilistener for mode buttons
        btcheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btcheque.setBackgroundColor(getResources().getColor(R.color.darkgreen));
                btcash.setBackgroundColor(getResources().getColor(R.color.lightgreen));
                btonline.setBackgroundColor(getResources().getColor(R.color.lightgreen));
                mode1=btcheque.getText().toString().trim();
                rlcheque.setVisibility(View.VISIBLE);
                rlonline.setVisibility(View.GONE);
                etcheckno=(EditText)view.findViewById(R.id.etcheckno_pin);
                etbranck=(EditText)view.findViewById(R.id.etbranch_pin);
                mode=1;
                etcheckno.requestFocus();
                //Toast.makeText(getActivity(),"cheque"+mode,Toast.LENGTH_LONG).show();

            }
        });
        btcash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btcash.setBackgroundColor(getResources().getColor(R.color.darkgreen));
                btonline.setBackgroundColor(getResources().getColor(R.color.lightgreen));
                btcheque.setBackgroundColor(getResources().getColor(R.color.lightgreen));
                mode=2;
                mode1=btcash.getText().toString().trim();
                rlcheque.setVisibility(View.GONE);
                rlonline.setVisibility(View.GONE);
                //Toast.makeText(getActivity(),"cash"+mode,Toast.LENGTH_LONG).show();
            }
        });
        btonline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btonline.setBackgroundColor(getResources().getColor(R.color.darkgreen));
                btcash.setBackgroundColor(getResources().getColor(R.color.lightgreen));
                btcheque.setBackgroundColor(getResources().getColor(R.color.lightgreen));
                mode=3;
                mode1=btonline.getText().toString().trim();
                rlcheque.setVisibility(View.GONE);
                rlonline.setVisibility(View.VISIBLE);

                etransid=(EditText)view.findViewById(R.id.et_transid_pin);
                etransid.requestFocus();
                //Toast.makeText(getActivity(),"online"+mode,Toast.LENGTH_LONG).show();
            }
        });

        //list and submit button onclick lisyners
/*
        btlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("bt ist");
                Cursor res = myDb.getAllData("tbpayin");
                StringBuffer buffer = new StringBuffer();
                if (res.getCount() == 0) {
                    //no data
                    builder.setMessage("error nothing found");

                }
                //.execSQL("create table tbpayin (_id INTEGER PRIMARY KEY,personid INTEGER,transdate TEXT,AMOUNT INTEGER,MODE TEXT,CHECKNO INTEGER,BRANCH TEXT,DATE TEXT,TYPE TEXT,receiptno TEXT);");
                else {

                    while (res.moveToNext()) {
                        buffer.append("id:" + res.getInt(res.getColumnIndex("_id")));
                        buffer.append("Flatno:" + res.getInt(res.getColumnIndex("personid")));
                        buffer.append("name:" + res.getString(res.getColumnIndex("MODE")));
                        buffer.append("date:" + res.getString(res.getColumnIndex("transdate")) + "\n");

                    }

                }
                builder.setMessage(buffer.toString());
            builder.show();}
        });
        /// add date in the data base
*/
        btsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    try {//int selectedId = rg_quarter.getCheckedRadioButtonId();

                        // find the radiobutton by returned id
                        //rb_Quarter = (RadioButton) findViewById(selectedId);
                        mid = etmid.getText().toString().trim();
                        //System.out.println(mid);
                        Cursor cursor=myDb.tb_owner_getid(mid);
                        if(cursor.moveToNext())
                        personid=cursor.getInt(cursor.getColumnIndex("id"));
                        // quarter=rb_Quarter.getText().toString();
                        // quarteri=Integer.parseInt(quarter);
                    }catch(Exception e)
                    {   error=true;
                        //System.out.println("error mid"+e.toString());
                    }
                    if(mode==1)
                    {
                        checkno=etcheckno.getText().toString().trim();

                    }
                    else if(mode==3)
                    {
                        transid=etransid.getText().toString().trim();
                    }
                    try {
                        amount = etamount.getText().toString();
                        amounti = Integer.parseInt(amount);
                    }catch(Exception e)
                    {       error=true;
                        //Toast.makeText(getActivity(),"error parsing data amount"+mode,Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e)
                {
                    Toast.makeText(getActivity(),"error parsing data from edittext1 amount or member id not mentioned"+mode,Toast.LENGTH_LONG).show();
                }





                if(mode==1)     //Cheque
                {
                    try {
                        //checknoi=Integer.parseInt(checkno);
                        checknoi = checkno;
                        branch = etbranck.getText().toString().trim();
                        checkdated = etcheckdate.getText().toString().trim();
                        String a, b;
                        a = String.format("%02d", month1);
                        b = String.format("%02d", day1);
                        checkdated = year1 + "-" + a + "-" + b;

                    } catch (Exception e) {
                        error = true;
//System.out.println(e.toString()+402);                }
                        transidi = 0;
                    }
                }else if(mode==2)        //Cash

                {   //Toast.makeText(getActivity(),amount , Toast.LENGTH_LONG).show();
                    checknoi="";
                    branch=null;
                }
                else if(mode==3)        //Online
                {   try{
                    //checknoi=Integer.parseInt(transid);
                    checknoi=transid;

                    branch=null;
                    checkdated=etcheckdate.getText().toString().trim();
                    String a,b;
                    a = String.format("%02d", month1);
                    b = String.format("%02d", day1);
                    checkdated=year1+"-"+a+"-"+b;

                }catch(Exception e)
                {       error=true;
                    //Toast.makeText(getActivity(),"pls provide transid", Toast.LENGTH_LONG).show();
                }

                }
                else
                {
                    //Toast.makeText(getActivity(),"pls select mode of payment",Toast.LENGTH_LONG).show();
                }
                String months=null;
                String days=null;
                try {
                    try {
                        months = String.format("%02d", month);
                        days = String.format("%02d", day);
                    } catch (Exception e) {
                        error = true;
                        //Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                    }

                    String date = year + "-" + months + "-" + days;

                    //Toast.makeText(getActivity(), "rrr" + mid + "*" + amounti + "*" + mode1 + "*" + checknoi + "*" + branch + "*" + transidi + "*" + date, Toast.LENGTH_LONG).show();
                    if (error == true) {
                        Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
                    } else {
                        // public boolean insertData_tbpayin(int personid, int amount,String transdate, String mode, int checkno, String branch, String date,String ptype,String receipt)
//                       String receipt="aaa";
//                        boolean flag = myDb.insertData_tbpayin(personid, amounti,date, mode1, checknoi, branch, checkdated,ptype,receipt);
                        //DatePicker temp = (DatePicker) datepick.getText();

                        //System.out.println(temp.getYear()+" "+temp.getMonth()+ " "+temp.getDayOfMonth());
                        String[] tempDate = etEntryDate.getText().toString().split("-");
                        //System.out.println(tempDate[2]+"  "+tempDate[1]+"  "+tempDate[0]);
                        String finalDate = tempDate[2]+"-"+String.format("%02d", Integer.parseInt(tempDate[1]))+"-"+String.format("%02d", Integer.parseInt(tempDate[0]));
                        //System.out.println("1 EntryDate:"+etEntryDate.getText().toString().trim()+" Cheque Date:"+etcheckdate.getText().toString().trim()+" ID:-"+personid+ " Final Date:"+finalDate);
                        //boolean flag = myDb.insertData_tbpayin(personid, amounti,date, mode1, checknoi, branch, checkdated,ptype);
                        boolean flag = myDb.insertData_tbpayin(personid, amounti,finalDate, mode1, checknoi, branch, checkdated,ptype);
//                        boolean flag = myDb.insertData_tbpayin(personid, amounti,etEntryDate.getText().toString().trim(), mode1, checknoi, branch, etcheckdate.getText().toString().trim(),ptype);
//                        boolean flag = myDb.insertData_tbpayin(personid, amounti,etEntryDate.getText().toString().trim(), mode1, checknoi, branch, checkdated,ptype);
                        if (flag)
                            Toast.makeText(getActivity(), "Added Data Successfully" , Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getActivity(), "Sorry! Error while adding Data" , Toast.LENGTH_LONG).show();
                    }

                    etamount.setText("");
                    etmid.setText("");
                    if(mode==1)
                    {
                        etbranck.setText("");
                        etcheckno.setText("");
                    }
                    if(mode==3)
                    {etransid.setText("");}

                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setTitle("want to generate receipt");
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }

                    });
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            try{
                                final File exportDir;
                                String state = Environment.getExternalStorageState();
//check if the external directory is availabe for writing
                                if (!Environment.MEDIA_MOUNTED.equals(state)) {
                                    return;
                                }
                                else {
                                    exportDir = new File(Environment.getExternalStorageDirectory() + File.separator + "abc");
                                }

//if the external storage directory does not exists, we create it
                                if (!exportDir.exists()) {
                                    exportDir.mkdirs();
                                }
                                File file;
                                file = new File(exportDir, "Budget.pdf");
//PDF is a class of the PDFJET library
                                PDF pdf= new PDF(new FileOutputStream(file));
//instructions to create the pdf file content
                                //first we create a page with portrait orientation
                                Page page = new Page(pdf, Letter.PORTRAIT);

//font of the title
                                Font f1 = new Font(pdf, CoreFont.HELVETICA_BOLD);
                                f1.setSize(7.0f);

//title: font f1 and color blue
                                TextLine title = new TextLine(f1, "RECEIPT");
                                title.setFont(f1);
                                title.setColor(Color.blue);

//center the title horizontally on the page
                                title.setPosition(page.getWidth()/2-title.getWidth()/2, 40f);

//draw the title on the page
                                title.drawOn(page);

                                Font f2 = new Font(pdf, CoreFont.HELVETICA);
                                f2.setSize(8);

                                TextLine textLine = new TextLine(f2);
                                textLine.setText("flatno="+mid);
                                textLine.setColor(Color.black);
                                textLine.setLocation(page.getWidth()/2-90,60f);
                                textLine.drawOn(page);


                                TextLine textLine1 = new TextLine(f2);
                                textLine1.setText("AMOUNT="+amount);
                                textLine1.setColor(Color.black);
                                textLine1.setLocation(page.getWidth()/2-90,80f);
                                textLine1.drawOn(page);

                                TextLine textLine2 = new TextLine(f2);
                                textLine2.setText("DATE="+day+"/"+month+"/"+year);
                                textLine2.setColor(Color.black);
                                textLine2.setLocation(page.getWidth()/2-90,100f);
                                textLine2.drawOn(page);
                                pdf.flush();


                                Intent intent=new Intent();
                                intent.setType("application/pdf");
                                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                                startActivity(intent);

                            }catch (Exception e)
                            {
                                //System.out.println("receipterror");

                            }
                        }

                    });
                    builder.show();
                }
                catch(Exception e){Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();}
            }
        });
        myDb.close();
    }

}
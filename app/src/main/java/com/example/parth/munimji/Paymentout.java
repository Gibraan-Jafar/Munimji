package com.example.parth.munimji;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by parth on 11/5/16.
 */
public class Paymentout extends Fragment{

    int day,year,month,amounti,mode=3,checknoi;//1 for cheque,2 for online ,3 for cash
    String cname,amount,months,days,months1,days1,branch,tmode,checkno,transid;
    final int DATE_PICKER_ID=1111;
    DataBaseHelper myDb;
    RelativeLayout rlcheque,rlonline;
    int day1,year1,month1;
    boolean error;

    AutoCompleteTextView et_cname;
    EditText et_amount,et_date,et_cheque,et_branch,et_online,checkdated;
    Button bt_submit,bt_date,bt_list,bt_cash,bt_cheque,bt_online,bt_checkdated;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.paymentout_index,null);
        return rootView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_amount=(EditText)view.findViewById(R.id.et_amount_pout);

        et_date=(EditText)view.findViewById(R.id.et_date_pout);
        myDb=new DataBaseHelper(getActivity());
        bt_date=(Button)view.findViewById(R.id.bt_date_pout);
        bt_list=(Button)view.findViewById(R.id.btlistpout);
        bt_submit=(Button)view.findViewById(R.id.btsubmitpout);






        bt_cash=(Button)view.findViewById(R.id.bt_cash_pout);
        bt_cheque=(Button)view.findViewById(R.id.bt_cheque_pout);
        bt_online=(Button)view.findViewById(R.id.bt_online_pout);

        tmode=bt_cash.getText().toString().trim();
        rlcheque=(RelativeLayout)view.findViewById(R.id.rl_cheque_pout);
        rlonline=(RelativeLayout)view.findViewById(R.id.rl_online_pout);
        rlonline.setVisibility(View.GONE);
        rlcheque.setVisibility(View.GONE);

        bt_cash.setBackgroundColor(getResources().getColor(R.color.darkgreen));
        bt_online.setBackgroundColor(getResources().getColor(R.color.lightgreen));
        bt_cheque.setBackgroundColor(getResources().getColor(R.color.lightgreen));
        bt_cheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_cheque.setBackgroundColor(getResources().getColor(R.color.darkgreen));
                bt_cash.setBackgroundColor(getResources().getColor(R.color.lightgreen));
                bt_online.setBackgroundColor(getResources().getColor(R.color.lightgreen));
                mode = 1;
                et_branch = (EditText) view.findViewById(R.id.et_branch_pout);
                et_cheque = (EditText) view.findViewById(R.id.et_check_pout);
                rlcheque.setVisibility(View.VISIBLE);
                rlonline.setVisibility(View.GONE);
                tmode = (String) bt_cheque.getText();
                Toast.makeText(getActivity(),"cheque"+mode,Toast.LENGTH_LONG).show();
            }
        });
        bt_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_online.setBackgroundColor(getResources().getColor(R.color.darkgreen));
                bt_cash.setBackgroundColor(getResources().getColor(R.color.lightgreen));
                bt_cheque.setBackgroundColor(getResources().getColor(R.color.lightgreen));
                mode = 2;
                rlonline.setVisibility(View.VISIBLE);
                rlcheque.setVisibility(View.GONE);
                et_online = (EditText) view.findViewById(R.id.et_transid_pout);
                tmode = (String) bt_online.getText();
                Toast.makeText(getActivity(), "online" + mode, Toast.LENGTH_LONG).show();
            }
        });
        bt_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_cash.setBackgroundColor(getResources().getColor(R.color.darkgreen));
                bt_online.setBackgroundColor(getResources().getColor(R.color.lightgreen));
                bt_cheque.setBackgroundColor(getResources().getColor(R.color.lightgreen));
                mode=3;
                tmode=(String)bt_cash.getText();
                rlonline.setVisibility(View.GONE);
                rlcheque.setVisibility(View.GONE);
                Toast.makeText(getActivity(),"cash"+mode,Toast.LENGTH_LONG).show();
            }
        });

        //auto fill setup
        et_cname=(AutoCompleteTextView)view.findViewById(R.id.et_cname_pout);
        Cursor res=myDb.distcname();
        String[] result = new String[res.getCount()];
        res.moveToFirst();
        for(int i = 0; i < res.getCount(); i++)
        {
            //You can here manipulate a single string as you please
            result[i] = res.getString(0);
            res.moveToNext();
        }
        ArrayAdapter<String> adapter  = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, result);
        //adapter.add("");
        et_cname.setThreshold(1);

        //Set adapter to AutoCompleteTextView
        et_cname.setAdapter(adapter);
        //  et_cname.setOnItemSelectedListener(this);
        // et_cname.setOnItemClickListener(this);




        Calendar calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        day=calendar.get(Calendar.DATE);
        month=calendar.get(Calendar.MONTH);
        month++;
        months=String.format("%02d",month);
        days=String.format("%02d",day);
        et_date.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(days).append("-").append(months).append("-")
                .append(year).append(" "));
        bt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                et_date.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, year, month, day);
                dpd.show();
            }
        });



        year1=calendar.get(Calendar.YEAR);
        day1=calendar.get(Calendar.DATE);
        month1=calendar.get(Calendar.MONTH);
        month1++;
        months1=String.format("%02d",month);
        days1=String.format("%02d",day);
        checkdated=(EditText)view.findViewById(R.id.et_checkdatepout);
        checkdated.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(days1).append("-").append(months1).append("-")
                .append(year1).append(" "));
        bt_checkdated=(Button)view.findViewById(R.id.btcheckdated_pout);
        bt_checkdated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                checkdated.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, year1, month1, day1);
                dpd.show();

            }
        });



        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkdate="";

                months=String.format("%02d",month);
                days=String.format("%02d",day);
                branch=null;
                //Toast.makeText(getApplicationContext(), "submitbutton pressed", Toast.LENGTH_LONG).show();

                try{

                    if(mode==1)
                    {
                        checkno=et_cheque.getText().toString().trim();
                        checknoi=Integer.parseInt(checkno);
                        branch=et_branch.getText().toString();
                        months1=String.format("%02d",month);
                        days1=String.format("%02d",day);
                        checkdate=year1+"-"+months1+"-"+days1;
                    }
                    else if(mode==2)
                    {
                        transid=et_online.getText().toString().trim();
                        checknoi=Integer.parseInt(transid);
                        branch=null;
                    }
                    else {
                        branch=null;
                        checknoi=0;
                    }
                    cname=et_cname.getText().toString().trim();
                    amount=et_amount.getText().toString().trim();
                    amounti=Integer.parseInt(amount);
                    //Toast.makeText(getApplicationContext(),amount+cname, Toast.LENGTH_LONG).show();

                }catch (Exception e)
                {   error=true;
                    Toast.makeText(getActivity(), "name or money not entered**"+e.toString(), Toast.LENGTH_LONG).show();
                }
                try {
                    Toast.makeText(getActivity(),"rrr"+"*"+cname+"*"+amounti+"*"+day+"*"+month+"*"+year+"*"+checknoi+"*"+branch,Toast.LENGTH_LONG).show();
                    String date=year+"-"+months+"-"+days;
                    //String cname,int amount,String date,String mode,int checkno,String branch

                    if(error==true)
                    {
                        Toast.makeText(getActivity(),"sss"+false,Toast.LENGTH_LONG).show();
                    }
                    else {int id=0;
                        Cursor res2=myDb.tb_owner_getcompanyid(String.valueOf(cname));
                        //System.out.println(ans);
                        System.out.println(res2.getColumnName(0));
                        if(res2.moveToNext())
                           id =res2.getInt(res2.getColumnIndex("companyid"));


                        boolean flag=myDb.insertData(id, amounti,date,tmode,checknoi,branch,checkdate);
                        Toast.makeText(getActivity(),"sss"+flag,Toast.LENGTH_LONG).show();
                    }
                    et_amount.setText("");
                    if(mode==1) {
                        et_cheque.setText("");
                        et_branch.setText("");
                    }
                    if(mode==2)
                        et_online.setText("");

                    et_cname.setText("");

                }
                catch(Exception e){Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();}
            }
        });

        bt_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                   /* Intent i=new Intent(Paymentout_index.this,Listpayment_out_index.class);
                    startActivity(i);*/}catch (Exception e)
                {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
        myDb.close();
    }
}

package com.example.parth.munimji;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by parth on 11/7/16.
 */
public class Companyinfo extends Fragment {
    EditText etcname,etname,etmobile,etdesc;
    String name,number,companyname,desc;
    //Button btsubmit;
    MenuItem menuItem;

    DataBaseHelper myDb;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.companyinfo,null);
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
        super.onViewCreated(view, savedInstanceState);
        myDb=new DataBaseHelper(getActivity());

        etcname=(EditText)view.findViewById(R.id.et_companyname_cinfo);
        etname=(EditText)view.findViewById(R.id.et_contactperson_cinfo);
        etmobile=(EditText)view.findViewById(R.id.et_contactphone_cinfo);
        etdesc=(EditText)view.findViewById(R.id.et_desc_cinfo);
        //btsubmit=(Button)view.findViewById(R.id.btsubmint_cinfo);

        /*btsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=etname.getText().toString();
                companyname=etcname.getText().toString();
                number=etmobile.getText().toString();
                desc=etdesc.getText().toString();
               boolean ans= myDb.insertcompanyinfo(companyname,name,number,desc);
                if(ans==true)
                {
                    etname.setText("");
                    etcname.setText("");
                    etmobile.setText("");
                    etdesc.setText("");
                }
                Toast.makeText(getActivity(),""+ans,Toast.LENGTH_SHORT).show();
            }
        });*/

        Button bt=(Button)view.findViewById(R.id.btshow_cinfo);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Cursor res = myDb.getAllData("tbcompanyinfo");
                    if (res.getCount() == 0) {
                        //no data
                        showMessage("error", "nothing found");

                    }
//        db.execSQL("create table tbcompanyinfo (companyid INTEGER PRIMARY KEY,name TEXT,contactperson TEXT,mobile TEXT,description TEXT); ");


                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()) {
                        buffer.append("id:" + res.getInt(res.getColumnIndex("companyid")));
                        buffer.append("cname:" + res.getString(res.getColumnIndex("name")));
                        buffer.append("name:" + res.getString(res.getColumnIndex("contactperson")));
                        buffer.append("description:" + res.getString(res.getColumnIndex("description")));
                        buffer.append("mobile:" + res.getString(res.getColumnIndex("mobile")) + "\n");

                    }
                    showMessage("Data:\n", buffer.toString());
                }catch (Exception e)
                {
                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void saveInfo(){

        name=etname.getText().toString();
        companyname=etcname.getText().toString();
        number=etmobile.getText().toString();
        desc=etdesc.getText().toString();
        boolean ans= myDb.insertcompanyinfo(companyname,name,number,desc);
        if(ans==true)
        {
            etname.setText("");
            etcname.setText("");
            etmobile.setText("");
            etdesc.setText("");
        }
        Toast.makeText(getActivity(),""+ans,Toast.LENGTH_SHORT).show();

    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }
}

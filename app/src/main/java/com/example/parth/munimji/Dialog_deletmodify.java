package com.example.parth.munimji;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by parth on 25/4/16.
 */



public class Dialog_deletmodify extends DialogFragment implements AdapterView.OnItemClickListener {
    ListView l;
    String[] options={"delete","modify"};
    Communicator1 comm;
    String flatno,date;
    int transid;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        comm=(Communicator1)activity;
       // flatno=getArguments().getString("flatno");
        //date=getArguments().getString("date");
        transid= getArguments().getInt("transid");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View v= inflater.inflate(R.layout.del_modify_call_dialog,null);
        l=(ListView)v.findViewById(R.id.ls_addowner);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,options);
        l.setAdapter(adapter);
        l.setOnItemClickListener(this);
        builder.setView(v);
        builder.setTitle("select the action for \nTransid="+transid);
        //setCancelable(false);
        Dialog dialog=builder.create();
        return  dialog;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("listview cliclked " + position);
        TextView tv= (TextView) view;

        comm.onDialogmsg1(tv.getText().toString());
        dismiss();

    }
    interface Communicator1
    {
        public void onDialogmsg1(String s);
    }
}

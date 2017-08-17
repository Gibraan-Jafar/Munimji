package com.example.parth.munimji;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by parth on 17/4/16.
 */
public class Dialogfragment_modifydeletecall extends DialogFragment implements AdapterView.OnItemClickListener {
    ListView l;

    String[] options={"call","delete","modify"};
    String Tag;
    Communicator comm;
    int resultCode=1;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
   comm=(Communicator)activity;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Tag=getArguments().getString("fname");
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View v= inflater.inflate(R.layout.del_modify_call_dialog,null);
        l=(ListView)v.findViewById(R.id.ls_addowner);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,options);
        l.setAdapter(adapter);
        l.setOnItemClickListener(this);
        builder.setView(v);
        builder.setTitle("please select the action ");
        //setCancelable(false);
        Dialog dialog=builder.create();
        return  dialog;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("listview cliclked " + position);
            TextView tv= (TextView) view;

       comm.onDialogmsg(tv.getText().toString(),Tag);
        /*Intent intent = new Intent();
        intent.putExtra("listdata", tv.getText().toString());
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
        getDialog().dismiss();*/
        dismiss();

    }
    interface Communicator
    {
        public void onDialogmsg(String s,String tag);
    }
}

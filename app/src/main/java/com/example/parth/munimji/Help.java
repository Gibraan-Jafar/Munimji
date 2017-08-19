package com.example.parth.munimji;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by parth on 11/7/16.
 */
public class Help extends Fragment {
    EditText desc, number, email;
    Button add;
    TableLayout tb;
    DataBaseHelper myDb;
    MenuItem menuItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.helplineno, null);
        myDb = new DataBaseHelper(getActivity());
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menuItem = (MenuItem) menu.findItem(R.id.action_save);
        menuItem.setVisible(false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        desc = (EditText) view.findViewById(R.id.et_desc_help);
        number = (EditText) view.findViewById(R.id.et_cno_help);
        email = (EditText) view.findViewById(R.id.et_email_help);
        add = (Button) view.findViewById(R.id.bt_add_help);
        tb = (TableLayout) view.findViewById(R.id.table_help);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ans = myDb.addhelp(desc.getText().toString(), number.getText().toString(), email.getText().toString());
                Toast.makeText(getActivity(), "" + ans, Toast.LENGTH_SHORT);
                if(ans==true) {
                    disp();
                desc.setText("");
                    number.setText("");
                    email.setText("");
                }
            }
        });
        disp();
    }
    void disp() {
        try {
           if (tb.getChildCount() != 0) {
              tb.removeViews(0, tb.getChildCount()-1);
           }
            String help = "HelpLine";
            Cursor res1 = myDb.getAllData(help);
            if (res1.getCount() == 0) {
                //no data
                //.setText("no data in db");
            } else {
                while (res1.moveToNext()) {
                    TableRow tbrow = new TableRow(getActivity());
                    TextView desc = new TextView(getActivity());
                    TextView mobile = new TextView(getActivity());
                    TextView email = new TextView(getActivity());
                    desc.setBackgroundResource(R.color.lightgreen);
                    mobile.setBackgroundResource(R.color.lightgreen);
                    email.setBackgroundResource(R.color.lightgreen);
                    desc.setTypeface(null, Typeface.BOLD);
                    desc.setTextColor(Color.BLACK);
                    // flat.setTextSize(25);
                    email.setTypeface(null, Typeface.BOLD);
                    email.setTextColor(Color.BLACK);
                    //  mobile.setTextSize(25);
                    mobile.setTypeface(null, Typeface.BOLD);
                    mobile.setTextColor(Color.BLACK);
                    email.setText(res1.getString(res1.getColumnIndex("email")));
                    desc.setText(res1.getString(res1.getColumnIndex("desc")));
                    mobile.setText(res1.getString(res1.getColumnIndex("mobile")));
                    tbrow.addView(desc);
                    tbrow.addView(mobile);
                    tbrow.addView(email);
                    tb.addView(tbrow);


                }


            }
        }catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }
}

package com.example.parth.munimji;

/**
 * Created by THAVARE on 20/06/2017.
 */
import android.app.Activity;
//import android.app.Fragment;

import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;
import android.widget.Toast;

public class showPhone extends Fragment {

    String companies[] = {"Google","Windows","iPhone","Nokia","Samsung",
            "Google","Windows","iPhone","Nokia","Samsung",
            "Google","Windows","iPhone","Nokia","Samsung"};
    String os[]       =  {"Android","Mango","iOS","Symbian","Bada",
            "Android","Mango","iOS","Symbian","Bada",
            "Android","Mango","iOS","Symbian","Bada"};

    TableLayout tl;
    TableRow tr;
    TextView companyTV,valueTV, mobileNumber;
    Button searchPhone;
    EditText phoneNumber;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("Hello1");
        View rootView = inflater.inflate(R.layout.mobile_numbers,null);
        System.out.println("Hello2");
        return rootView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {

//        @Override
//    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.mobile_numbers);
        System.out.println("Hello3");
        tl = (TableLayout) view.findViewById(R.id.telephonetable);
        System.out.println("Hello4");
        addHeaders();
        System.out.println("Hello5");
        addData();
        System.out.println("Hello6");


        searchPhone = (Button) view.findViewById(R.id.bt_searchVehicle);
        phoneNumber=(EditText)view.findViewById(R.id.et_searchVehicle);

        searchPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Hi");
                //TableLayout temp123 = (TableLayout)v.findViewById(R.id.telephonetable);
                System.out.println(tl.getChildCount());
                //System.out.println(temp123.getChildCount());
                if (tl != null && tl instanceof TableLayout)
                {
                    System.out.println("Hi1");
                    for(int i=2;i<tl.getChildCount();i++)
                    {
                        //System.out.println("Hi2");
                        TableRow row = (TableRow) tl.getChildAt(i);
                        if (row != null && row instanceof TableRow) {
                            //System.out.println("Hello "+row.getChildCount());

                            TextView y = (TextView) row.getChildAt(0);
                            //System.out.println(y.getText().toString());
                            TextView x = (TextView) row.getChildAt(2);

                            if (x != null && x instanceof TextView) {
                                String tempVehNo = x.getText().toString();
                                //System.out.println(tempVehNo);
                                System.out.println(tempVehNo + "  "+ phoneNumber.getText().toString());
                                if(tempVehNo.contains(phoneNumber.getText().toString().trim())) {
                                    row.setBackgroundColor(Color.GRAY);
                                    System.out.println("Found");
                                    // Toast.makeText(getActivity(), "Vehicle Number Found. Flat No:" + ((TextView) row.getChildAt(0)).getText().toString(), Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    row.setBackgroundColor(Color.WHITE);
                                    System.out.println("Not Found");
                                }
                            }
                            //System.out.println(x.getText().toString());
                        }
                    }
                }
            }});
    }



    /** This function add the headers to the table **/
    public void addHeaders(){

        /** Create a TableRow dynamically **/
        tr = new TableRow(this.getContext());
        tr.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

        /** Creating a TextView to add to the row **/
        TextView companyTV = new TextView(this.getContext());
        companyTV.setText("Flat No.");
        companyTV.setTextColor(Color.WHITE);
        companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        companyTV.setPadding(5, 5, 5, 0);
        tr.addView(companyTV);  // Adding textView to tablerow.

        /** Creating another textview **/
        TextView valueTV = new TextView(this.getContext());
        valueTV.setText("Name");
        valueTV.setTextColor(Color.WHITE);
        valueTV.setPadding(5, 5, 5, 0);
        valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(valueTV); // Adding textView to tablerow.

        TextView valueMobile = new TextView(this.getContext());
        valueMobile.setText("Mobile No");
        valueMobile.setTextColor(Color.WHITE);
        valueMobile.setPadding(5, 5, 5, 0);
        valueMobile.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(valueMobile); // Adding textView to tablerow.
        // Add the TableRow to the TableLayout
        tr.setBackgroundColor(Color.BLACK);
        tl.addView(tr, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

        // we are adding two textviews for the divider because we have two columns
        tr = new TableRow(this.getContext());
        tr.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

        /** Creating another textview **/
/*        TextView divider = new TextView(this.getContext());
        divider.setText("-----------------");
        divider.setTextColor(Color.GREEN);
        divider.setPadding(5, 0, 0, 0);
        divider.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(divider); // Adding textView to tablerow.

        TextView divider2 = new TextView(this.getContext());
        divider2.setText("-------------------------");
        divider2.setTextColor(Color.GREEN);
        divider2.setPadding(5, 0, 0, 0);
        divider2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(divider2); // Adding textView to tablerow.
*/
        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
    }

    /** This function add the data to the table **/
    public void addData(){
        DataBaseHelper myDb;
        myDb = new DataBaseHelper(getActivity());
        Cursor temp =  myDb.getAllDataown("tbowner");
        System.out.println("SJT "+temp.getCount());
        if(temp.getCount()==0)
        {
            Toast.makeText(getActivity(), "Sorry! Data Not Available.", Toast.LENGTH_LONG).show();
        }
        else
        {
            while(temp.moveToNext())
            {
                System.out.println(temp.getString(temp.getColumnIndex("flatno"))+ "  "+temp.getString(temp.getColumnIndex("name")));
                tr = new TableRow(this.getContext());
                tr.setLayoutParams(new LayoutParams(
                        LayoutParams.FILL_PARENT,
                        LayoutParams.WRAP_CONTENT));

                /** Creating a TextView to add to the row **/
                System.out.println("Hello20");
                companyTV = new TextView(this.getContext());
                companyTV.setText(temp.getString(temp.getColumnIndex("flatno")));
                companyTV.setTextColor(Color.RED);
                companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                companyTV.setPadding(5, 5, 5, 5);
                tr.addView(companyTV);  // Adding textView to tablerow.
                System.out.println("Hello10");
                /** Creating another textview **/
                valueTV = new TextView(this.getContext());
                valueTV.setText(temp.getString(temp.getColumnIndex("name")));
                valueTV.setTextColor(Color.RED);
                valueTV.setPadding(5, 5, 5, 5);
                valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                tr.addView(valueTV); // Adding textView to tablerow.
                System.out.println("Hello10");
                try {
                    Cursor mobileInfo = myDb.get_mobile_own(temp.getInt(0));
                    if (mobileInfo.getCount() != 0) {
                        mobileNumber = new TextView(this.getContext());
                        mobileNumber.setTextColor(Color.RED);
                        mobileNumber.setPadding(5, 5, 5, 5);
                        mobileNumber.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                        while (mobileInfo.moveToNext()) {
                            mobileNumber.setText(mobileInfo.getString(0));
                        }
                        tr.addView(mobileNumber); // Adding textView to tablerow.
                    }
                    mobileInfo.close();
                }catch (Exception e)
                {
                    System.out.println("error in fetching mobile");
                }
                    System.out.println("Hello10");
                // Add the TableRow to the TableLayout
                tl.addView(tr, new TableLayout.LayoutParams(
                        LayoutParams.FILL_PARENT,
                        LayoutParams.WRAP_CONTENT));
                System.out.println("Hello10");
            }
            temp.close();
        }
        //for (int i = 0; i < companies.length; i++)
        //{
        //    /** Create a TableRow dynamically **/
        //    tr = new TableRow(this.getContext());
        //    tr.setLayoutParams(new LayoutParams(
        //LayoutParams.FILL_PARENT,
        //            LayoutParams.WRAP_CONTENT));

            /** Creating a TextView to add to the row **/
        //    companyTV = new TextView(this.getContext());
        //    companyTV.setText(companies[i]);
        //    companyTV.setTextColor(Color.RED);
        //    companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        //    companyTV.setPadding(5, 5, 5, 5);
        //   tr.addView(companyTV);  // Adding textView to tablerow.

            /** Creating another textview **/
        //    valueTV = new TextView(this.getContext());
        //    valueTV.setText(os[i]);
        //    valueTV.setTextColor(Color.GREEN);
        //    valueTV.setPadding(5, 5, 5, 5);
        //    valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        //    tr.addView(valueTV); // Adding textView to tablerow.

            // Add the TableRow to the TableLayout
        //    tl.addView(tr, new TableLayout.LayoutParams(
        //            LayoutParams.FILL_PARENT,
        //            LayoutParams.WRAP_CONTENT));
        //}
    }
}
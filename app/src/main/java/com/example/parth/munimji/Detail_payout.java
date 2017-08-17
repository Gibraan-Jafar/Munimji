package com.example.parth.munimji;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by parth on 28/4/16.
 */
public class Detail_payout extends AppCompatActivity implements Dialog_deletmodify.Communicator1 {
    TextView tv_flatno, tv_amount, tv_date, tv_branch, tv_check, tv_mode,tv_name;
    TableLayout tbl;
    Button exp;
    DataBaseHelper myDb;
    String dflatno, ddate;
    String flatno ,to, from;
    int dtransid;
    TextView tflatno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb = new DataBaseHelper(this);

        setContentView(R.layout.content_detail_paymentin);






        tv_name=(TextView)findViewById(R.id.tv_name_detpayin);
        tv_name.setText("COMPANYNAME");
        tflatno=(TextView)findViewById(R.id.tv_flat_detpayin);
        Intent intent = getIntent();
        flatno = intent.getStringExtra("flatno");//incase this case flatno holds the value of cname
        to = intent.getStringExtra("to");
        from = intent.getStringExtra("from");
        System.out.println(flatno);
        System.out.println(to);
        System.out.println(from);


        exp=(Button)findViewById(R.id.bt_expdet);

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

                    String filename = "detpayout.csv";
                    File saveFile = new File(exportDir, filename);
                    FileWriter fw = new FileWriter(saveFile);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write("for Company="+flatno);
                    bw.newLine();
                    TableLayout tb = (TableLayout) findViewById(R.id.tl_detpin);
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
                    Toast.makeText(getApplicationContext(), "exported successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });


        tbl = (TableLayout) findViewById(R.id.tl_detpin);
        tbl.setClickable(true);
        disp();
        myDb.close();
    }
    public void disp()
    {
        try {
          //  tv_name.setText("CNAME");
            int id=0;
            Cursor res2=myDb.tb_owner_getcompanyid(String.valueOf(flatno));
            //System.out.println(ans);
            System.out.println(res2.getColumnName(0));
            if(res2.moveToNext())
                id =res2.getInt(res2.getColumnIndex("companyid"));
            Cursor res = myDb.detpaymentout(id, from, to);
            if (res.getCount() == 0) {
                //no data


            }
            if (tbl.getChildCount() != 0) {
                tbl.removeViews(1,tbl.getChildCount()-1);
                //tbown.removeViews(0, tbown.getChildCount() - 1);
            }
            System.out.println(res.getColumnCount());


            while (res.moveToNext()) {//FLATNO TEXT ,AMOUNT INTEGER,MODE TEXT,CHECKNO INTEGER,BRANCH TEXT,DATE TEXT
////tbpayout (id INTEGER PRIMARY KEY,companyid INTEGER,AMOUNT INTEGER,transdate TEXT,MODE TEXT,CHECKNO INTEGER,BRANCH TEXT,date TEXT);");

                tflatno.setText(flatno);
                System.out.println(String.valueOf(res.getInt(res.getColumnIndex("AMOUNT"))));
                System.out.println(res.getString(res.getColumnIndex("BRANCH")) + ".");
                System.out.println(String.valueOf(res.getInt(res.getColumnIndex("CHECKNO"))));
                System.out.println(res.getString(res.getColumnIndex("transdate")));
                // System.out.println(res.getString(res.getColumnIndex("FLATNO")));
                System.out.println(res.getString(res.getColumnIndex("MODE")));
                tv_amount = new TextView(this);
                tv_amount.setBackgroundResource(R.drawable.shape);
                tv_amount.setText(String.valueOf(res.getInt(res.getColumnIndex("AMOUNT"))));
                tv_branch = new TextView(this);
                tv_branch.setText(res.getString(res.getColumnIndex("BRANCH")) + ".");
                tv_branch.setBackgroundResource(R.drawable.shape);
                tv_check = new TextView(this);
                tv_check.setText(String.valueOf(res.getInt(res.getColumnIndex("CHECKNO"))));
                tv_check.setBackgroundResource(R.drawable.shape);
                tv_date = new TextView(this);
                tv_date.setText(res.getString(res.getColumnIndex("transdate")));
                tv_date.setBackgroundResource(R.drawable.shape);
                tv_flatno = new TextView(this);
                tv_flatno.setText(res.getString(res.getColumnIndex("id")));
                tv_flatno.setBackgroundResource(R.drawable.shape);
                tv_mode = new TextView(this);
                tv_mode.setText(res.getString(res.getColumnIndex("MODE")));
                tv_mode.setBackgroundResource(R.drawable.shape);
                TableRow tbrow = new TableRow(getApplicationContext());
                tbrow.addView(tv_flatno);
                tbrow.addView(tv_amount);
                tbrow.addView(tv_date);
                tbrow.addView(tv_mode);
                tbrow.addView(tv_check);
                tbrow.addView(tv_branch);
                dtransid=res.getInt(res.getColumnIndex("id"));
                tbl.addView(tbrow);

            }
            int rowNumCount = tbl.getChildCount();
            for (int count = 1; count < rowNumCount; count++) {
                View v = tbl.getChildAt(count);
                if (v instanceof TableRow) {
                    final TableRow clickRow = (TableRow) v;
                    int rowCount = clickRow.getChildCount();
                    v.setOnLongClickListener(new View.OnLongClickListener() {

                        @Override
                        public boolean onLongClick(View v) {
                            TableRow row = (TableRow) v;
                            TextView tv = (TextView) row.getChildAt(0);
                            TextView tv2 = (TextView) row.getChildAt(2);
                            try {
                                dtransid = Integer.parseInt(tv.getText().toString().trim());
                            }catch (Exception e)
                            {
                                System.out.println(e.toString());
                            }
                            dflatno=tflatno.getText().toString();
                            ddate = tv2.getText().toString();
                            Bundle bundle = new Bundle();
//                            bundle.putString("flatno",dflatno);
                            //                          bundle.putString("date",ddate);
                            bundle.putInt("transid",dtransid);
                            FragmentManager manager = getFragmentManager();
                            Dialog_deletmodify mydialog = new Dialog_deletmodify();
                            mydialog.setArguments(bundle);
                            mydialog.show(manager, "mydialog2");
                            Toast.makeText(getApplicationContext(), dflatno + "**" + ddate, Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void onDialogmsg1(String s) {

        // Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT).show();
        if (s.equalsIgnoreCase("delete")) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete");
            builder.setMessage("are u sure u want to delete this entry ");
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.out.println("abc");
                }

            });
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.out.println(dtransid);
                    // Integer ans = myDb.deleteData(dflatno.toString().trim(),ddate.toString().trim());
                    // Toast.makeText(getApplicationContext(), "deletion" + ans, Toast.LENGTH_SHORT).show();
                    try {
                        Integer ans1 = myDb.deleteData("tbpayout", dtransid);
                        Toast.makeText(getApplicationContext(), "deletion" + ans1, Toast.LENGTH_SHORT).show();
                        disp();
                    }catch (Exception e)
                    {
                        System.out.println(e.toString());
                    }
                }

            });
            builder.show();
        }
        else {
        }
    }
}

package com.example.parth.munimji;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.R.drawable.*;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.renderscript.ScriptGroup;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CheckBox;
//import com.pdfjet.CheckBox;
import com.pdfjet.Line;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import static com.example.parth.munimji.R.drawable.minus;

public class Addnewowner_index extends Fragment {
    int pos = 0, twoWheelerCount=0, fourWheelerCount=0;
    static int vehicleCount=0;
    LinearLayout hzll, vertll, vertl2;
    ListView l;
    String[] options = {"name", "call", "delete", "modify"};
    int mobilei, rowno;
    String mobile = "", flatno1 = "", name = "", tenant = "", email = "", vehicleInfo2 = "", vehicleInfo4="";
    TableLayout tbown;
    Button bname, binfo, exp, bt_add_vechno, bt_add_2wheeler;
    String dflatno, dmobile;
    int numvech;
    int choice;
    boolean error;
    EditText etname, flatno, etmobile, etemail, et2vech, et4vech;
    TextView tvname, tvflatno, tvmobile, tvtenant;
    TextView[] twoWheeler= new TextView[25], fourWheeler = new TextView[25];
    RadioGroup rgtenant;
    RadioButton rbtenant;
    DataBaseHelper myDb;
    CheckBox cbOccupancy;
    View rootView;
    TableLayout tb;
    ScrollView infoScroll;
    MenuItem menuItem;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menuItem = (MenuItem) menu.findItem(R.id.action_save);
        menuItem.setVisible(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.addnewowner, container, false);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vertll = (LinearLayout) view.findViewById(R.id.ly_add_numvech);
        vertl2 = (LinearLayout) view.findViewById(R.id.ly_add_numvech2);
        bt_add_vechno = (Button) view.findViewById(R.id.bt_addvech_newown);
        bt_add_2wheeler= (Button) view.findViewById(R.id.bt_addvech2_newown);
        infoScroll= (ScrollView) view.findViewById(R.id.scrollView1);
        exp = (Button) view.findViewById(R.id.bt_exp_own);
        tb = (TableLayout) view.findViewById(R.id.index_own);
        //rgtenant = (RadioGroup) view.findViewById(R.id.rGtenant_own);
        bname = (Button) view.findViewById(R.id.bt_insert);
        binfo = (Button) view.findViewById(R.id.btinfo);
        etname = (EditText) view.findViewById(R.id.etname);
        flatno = (EditText) view.findViewById(R.id.et_flatno);
        etmobile = (EditText) view.findViewById(R.id.etmobile_own);
        cbOccupancy = (CheckBox) view.findViewById(R.id.cbOccupancy);
        etemail = (EditText) view.findViewById(R.id.et_email_own);
        et2vech = (EditText) view.findViewById(R.id.et_2Wheeler);
        et4vech = (EditText) view.findViewById(R.id.et_4Wheeler);
        tbown = (TableLayout) view.findViewById(R.id.index_own);
        tbown.setClickable(true);
        tvname = (TextView) view.findViewById(R.id.tvName_own);



        //mycode

        bt_add_vechno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos++;
                fourWheelerCount++;
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                //EditText textView = new EditText(getActivity());
                //textView.setEms(10);
                //textView.setId(getId());
                fourWheeler[pos-1]= new EditText(getActivity());
                fourWheeler[pos-1].setEms(12);
                fourWheeler[pos-1].setId(getId());
                vehicleCount=vehicleCount+1;
                System.out.println("Id of new Button:"+getId());
                Button button = new Button(getActivity());
                //button.setLayoutParams(layoutParams);
                button.setBackgroundResource(R.drawable.minus);
                button.setId(getId());
                int size=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, getResources().getDisplayMetrics());
                button.setLayoutParams(new LinearLayout.LayoutParams(size,size));

                //button.setBackgroundResource(R.drawable.refresh);
                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.addView(fourWheeler[pos-1]);
                linearLayout.addView(button);
                vertll.addView(linearLayout);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vertll.removeViewAt(fourWheelerCount);
                        pos--;
                        fourWheelerCount--;
                    }
                });

                infoScroll.scrollBy(0,infoScroll.getBottom()/2);
            }
        });

        bt_add_vechno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    bt_add_vechno.performClick();
                }
            }
        });

        bt_add_2wheeler.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    bt_add_2wheeler.performClick();
                }
            }
        });

        bt_add_2wheeler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twoWheelerCount++;
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                //EditText textView = new EditText(getActivity());
                //textView.setEms(12);
                //textView.setId(getId());
                vehicleCount=vehicleCount+1;
                twoWheeler[twoWheelerCount-1]= new EditText(getActivity());
                twoWheeler[twoWheelerCount-1].setEms(12);
                twoWheeler[twoWheelerCount-1].setId(getId());
                System.out.println("Id of new Button:"+getId());
                Button button = new Button(getActivity());
                //button.setLayoutParams(layoutParams);
                button.setBackgroundResource(R.drawable.minus);
                button.setId(getId());
                int size=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, getResources().getDisplayMetrics());
                button.setLayoutParams(new LinearLayout.LayoutParams(size,size));

                //button.setBackgroundResource(R.drawable.refresh);
                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.addView(twoWheeler[twoWheelerCount-1]);
                linearLayout.addView(button);
                vertl2.addView(linearLayout);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vertl2.removeViewAt(twoWheelerCount);
                        twoWheelerCount--;
                    }
                });

                infoScroll.scrollTo(0,infoScroll.getBottom()/2);

            }
        });





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
                        exportDir = new File(Environment.getExternalStorageDirectory() + File.separator + "munimji");
                    }

//if the external storage directory does not exists, we create it
                    if (!exportDir.exists()) {
                        exportDir.mkdirs();
                    }

                    String filename = "Listown.csv";
                    File saveFile = new File(exportDir, filename);
                    FileWriter fw = new FileWriter(saveFile);
                    BufferedWriter bw = new BufferedWriter(fw);


                    if (tb.getChildCount() != 0) {

                        for (int i = 0; i < tb.getChildCount(); i++) {
                            TableRow row = (TableRow) tb.getChildAt(i);
                            for (int j = 0; j < row.getChildCount(); j++) {
                                TextView tv = (TextView) row.getChildAt(j);
                                if (i != row.getChildCount() - 1) {
                                    bw.write(tv.getText().toString() + ",");
                                } else {
                                    bw.write(tv.getText().toString() + ".");
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


        myDb = new DataBaseHelper(getActivity());


        //tvflatno=(TextView)findViewById(R.id.tvflatno_own);
        // tvmobile=(TextView)findViewById(R.id.tvmobile_own);
        //  tvtenant=(TextView)findViewById(R.id.tvtenant_own);

        disp(); ///for displaying elements


        bname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int selectionid;
                    /*Toast.makeText(getActivity()
                            , etemail.getText().toString() + etnumvech.getText().toString() + etvechnum.getText()
                                    .toString(), Toast.LENGTH_SHORT).show();*/
                    //selectionid = rgtenant.getCheckedRadioButtonId();
                    //rbtenant = (RadioButton) view.findViewById(selectionid);
                    //System.out.println("above tenant");
                    //tenant = rbtenant.getText().toString();
                    //System.out.println("below tenant" + tenant);
                    mobile = etmobile.getText().toString().trim();
                    System.out.println("Mobile Number::"+mobile);
                    name = etname.getText().toString().trim();
                    flatno1 = flatno.getText().toString().trim();
                    email = etemail.getText().toString();
                    vehicleInfo2 = et2vech.getText().toString();
                    vehicleInfo4 = et4vech.getText().toString();
                    //System.out.println(fourWheeler[0].getText());
                    //numvech = Integer.parseInt(etnumvech.getText().toString().trim());
                    //System.out.println(pos);
                    email = etemail.getText().toString();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.toString() + "1", Toast.LENGTH_LONG).show();
                    System.out.println(e.toString() + "1");
                }
                try {
//                    if (flatno1.equals("") | mobile.equals("") | name.equals("") | email.equals("")) {
                        if (flatno1.equals("") | name.equals("")) {
                        error = true;
                    }
                    boolean ans;
                    if (error == true) {

                        ans = false;
                    } else {//mobile, tenant,email,numvech,vechno
                        int id = 0;
                        if (cbOccupancy.isChecked() )
                            ans = myDb.tb_owner_ins(flatno1, name,"O");
                        else
                            ans = myDb.tb_owner_ins(flatno1, name,"T");
                        //System.out.println(flatno1);
                        Cursor res2 = myDb.tb_owner_getid(String.valueOf(flatno1));
                        System.out.println(ans);

                        if (res2.moveToNext())
                            id = res2.getInt(res2.getColumnIndex("id"));
                        System.out.println("Flat id:"+id);
                        boolean ans1=false,ans2=false,ans3=false,ans4=false;
                        if(ans==true)
                        {
                            if (mobile.length()>0)  ans1 = myDb.tb_persinfo_ins(id, 1, mobile);        //1-mobile
                            if (email.length()>0) ans2 = myDb.tb_persinfo_ins(id, 2, email);         //2-email
                            if (vehicleInfo2.length()>0)
                            {
                                ans3 = myDb.tb_persinfo_ins(id, 3, vehicleInfo2);        //3-2 wheeler info.
                                if (twoWheelerCount>0) {
                                    System.out.println("Two wheller count "+twoWheelerCount);
                                    for (int i = 0; i < twoWheelerCount; i++) {
                                        System.out.println(twoWheeler[i].getText().toString());
                                        ans3 = myDb.tb_persinfo_ins(id, 3, twoWheeler[i].getText().toString());
                                    }
                                }
                            }
                            if (vehicleInfo4.length()>0)
                            {
                                ans4 = myDb.tb_persinfo_ins(id, 4, vehicleInfo4);   //4-4 wheeler info.
                                if (fourWheelerCount>0) {
                                    System.out.println("Four wheller count "+fourWheelerCount);
                                    for (int i = 0; i < fourWheelerCount; i++) {
                                        System.out.println(fourWheeler[i].getText().toString());
                                        ans3 = myDb.tb_persinfo_ins(id, 4, fourWheeler[i].getText().toString());
                                    }
                                }
                            }
                        }
                        System.out.println("*" + ans1 + "*" + ans2 + "*" + ans3+ "*" + ans4);
                        //boolean ans1=myDb.tb_persinfo_ins()
                    }
                    if (ans == true)
                        Toast.makeText(getActivity(), "Information added successfully", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getActivity(), "Sorry! Failed to add Information.", Toast.LENGTH_LONG).show();
                    System.out.println("Helo 123");
                    etname.setText("");
                    flatno.setText("");
                    etmobile.setText("");
                    etemail.setText("");
                    et2vech.setText("");
                    et4vech.setText("");
                    if (twoWheelerCount>0) {
                        for (int i = 1; i <= twoWheelerCount; i++) {
                            vertl2.removeViewAt(i);
                        }
                        twoWheelerCount=0;
                    }
                    if (fourWheelerCount>0) {
                        for (int i = 1; i <= fourWheelerCount; i++) {
                            vertll.removeViewAt(i);
                        }
                        pos=0;
                        fourWheelerCount=0;
                    }
                    disp();
                } catch (Exception e) {
                    System.out.println(e.toString() + "2");
                }

            }
        });
        binfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cursor res = myDb.getAllData("tbowner");
                Cursor res = myDb.tnown_join_tbtenant();
                if (res.getCount() == 0) {
                    //no data
                    showMessage("error", "nothing found");

                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("id:" + res.getInt(res.getColumnIndex("id")));
                    buffer.append("tid:" + res.getInt(res.getColumnIndex("tid")));
                    buffer.append("flatno:" + res.getString(res.getColumnIndex("flatno")) + "\n");

                    // buffer.append("Flatno:" + res.getString(res.getColumnIndex("flatno"))+"\n");
                    //buffer.append("name:" + res.getString(res.getColumnIndex("name")) + "\n");
                }
                showMessage("Data:\n", buffer.toString());
            }
        });
        myDb.close();
    }

    public void saveInfo(){

        Cursor res = myDb.tnown_join_tbtenant();
        if (res.getCount() == 0) {
            //no data
            showMessage("error", "nothing found");

        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("id:" + res.getInt(res.getColumnIndex("id")));
            buffer.append("tid:" + res.getInt(res.getColumnIndex("tid")));
            buffer.append("flatno:" + res.getString(res.getColumnIndex("flatno")) + "\n");

            // buffer.append("Flatno:" + res.getString(res.getColumnIndex("flatno"))+"\n");
            //buffer.append("name:" + res.getString(res.getColumnIndex("name")) + "\n");
        }
        showMessage("Data:\n", buffer.toString());

    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

    public void disp() {

        try {
            System.out.println("Thawrae Shailesh");
            if (tbown.getChildCount() != 0) {
                tbown.removeViews(1, tbown.getChildCount() - 1);
            }
            //tbown.removeViews(0, tbown.getChildCount() - 1);
            //Cursor res1 = myDb.tnown_join_tbtenant();
            Cursor res1 = myDb.getAllDataown("tbowner");
            if (res1.getCount() == 0) {
                //no data
                tvflatno.setText("no data in db");
            } else {
                Cursor res2;
                System.out.println("Thawrae Shailesh1");
                while (res1.moveToNext()) {
                    TableRow tbrow = new TableRow(getActivity());
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT);
                    //tbrow.setBackground(R.id.et_flatno);
                    tbrow.setBackgroundResource(R.drawable.shape);
                    tbrow.setLayoutParams(lp);
                    tbrow.setPadding(0, 0, 0, 2);
                    TextView flat = new TextView(getActivity());
                    TextView name = new TextView(getActivity());
                    TextView mobile = new TextView(getActivity());
                    //TextView email = new TextView(getActivity());
                    //TextView numvech = new TextView(getActivity());
                    //TextView vechnum = new TextView(getActivity());
                    // name.setTextSize(25);
                    name.setTypeface(null, Typeface.BOLD);
                    name.setTextColor(Color.BLACK);
                    //name.setBackgroundResource(R.drawable.shape);

                    //name.setBackground(context.getResources().getDrawable(R.drawable.shape));
                    // flat.setTextSize(25);
                    flat.setTypeface(null, Typeface.BOLD);
                    flat.setTextColor(Color.BLACK);
                    //flat.setBackgroundResource(R.drawable.shape);
                    //  mobile.setTextSize(25);
                    mobile.setTypeface(null, Typeface.BOLD);
                    mobile.setTextColor(Color.BLACK);

                    //mobile.setBackgroundResource(R.drawable.shape);
                    // email.setText(res1.getString(res1.getColumnIndex("EMAIL")));
                    flat.setText(res1.getString(res1.getColumnIndex("flatno")));
                    name.setText(res1.getString(res1.getColumnIndex("name")));
                    System.out.println("Flat ID::"+res1.getInt(res1.getColumnIndex("id")));
                    res2 = myDb.get_mobile_own(res1.getInt(res1.getColumnIndex("id")));
                    //res2 = myDb.get_mobile_own(10);
                    try {
                        //res2.moveToNext();
                        //System.out.println("Flat Number :; " + res2.getInt(0));
                        //System.out.println("Flat Id:"+res1.getInt(res1.getColumnIndex("id"))+" Name:"+res1.getString(res1.getColumnIndex("name")));
                        //res2.moveToLast();
                        //System.out.println("No.of Mobiles:" + res2.getCount());
                        //res2.moveToFirst();
                        int x = 0;
                        //String y="";
                        while (res2.moveToNext()) {
                            //x = res2.getInt(0);
                            //y=res2.getString(0);
                            //System.out.println("Mobile Number:" + x);
                            //mobile.setText(Integer.toString(x));
                            mobile.setText(res2.getString(0));
                            //mobile.setText(y);
                        }

                        res2.close();
                    }catch (Exception e){}
                    // numvech.setText(res1.getString(res1.getColumnIndex("NUMVECH")) );
                    //vechnum.setText(res1.getString(res1.getColumnIndex("VECHNUM")));

                    if (res1.getString(res1.getColumnIndex("occupancy")).contains("O")) {
                        flat.setBackgroundResource(R.color.lightgreen);
                        name.setBackgroundResource(R.color.lightgreen);
                        mobile.setBackgroundResource(R.color.lightgreen);
                        //email.setBackgroundResource(R.color.lightgreen);
                        //numvech.setBackgroundResource(R.color.lightgreen);
                        //vechnum.setBackgroundResource(R.color.lightgreen);
                    } else {
                        flat.setBackgroundResource(R.color.darkgreen);
                        name.setBackgroundResource(R.color.darkgreen);
                        mobile.setBackgroundResource(R.color.darkgreen);
                        //email.setBackgroundResource(R.color.darkgreen);
                        //numvech.setBackgroundResource(R.color.darkgreen);
                        //vechnum.setBackgroundResource(R.color.darkgreen);
                    }
                    tbrow.addView(flat);
                    tbrow.addView(name);
                    tbrow.addView(mobile);
                    //tbrow.addView(email);
                    //tbrow.addView(numvech);
                    //tbrow.addView(vechnum);
                    tbown.addView(tbrow);
                    //tvtenant.append(res1.getString(res1.getColumnIndex("TENANT"))+"\n");

                }
                res1.close();

                int rowNumCount = tbown.getChildCount();
                for (int count = 0; count < rowNumCount; count++) {
                    View v = tbown.getChildAt(count);
                    if (v instanceof TableRow) {
                        final TableRow clickRow = (TableRow) v;
                        int rowCount = clickRow.getChildCount();
                        v.setOnLongClickListener(new View.OnLongClickListener() {

                            @Override
                            public boolean onLongClick(View v) {
                                TableRow row = (TableRow) v;
                                TextView tv = (TextView) row.getChildAt(0);
                                dflatno = tv.getText().toString();
                                TextView tv1 = (TextView) row.getChildAt(1);
                                TextView tv2 = (TextView) row.getChildAt(2);
                                dmobile = tv2.getText().toString();
                                CharSequence text = "Lot VALUE Selected: " + tv.getText() + "**" + tv2.getText();
                                int dubooleanration = Toast.LENGTH_SHORT;
                                final Dialog d1 = new Dialog(getActivity());
                                d1.setContentView(R.layout.del_modify_call_dialog);
                                l = (ListView) d1.findViewById(R.id.ls_addowner);
                                options[0]=dflatno+ ". "+ tv1.getText().toString()+"  "+dmobile;
                                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, options);
                                l.setAdapter(adapter1);
                                l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        //System.out.println("listview cliclked " + position);
                                        TextView tv = (TextView) view;
                                        action(tv.getText().toString());
                                        d1.dismiss();
                                    }

                                });
                                d1.show();

                                return true;
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString() + 433 + "addnewowner");
        }
    }

    public void check(int n) {
        Toast.makeText(getActivity(), String.valueOf(n), Toast.LENGTH_LONG).show();
    }

    void action(String s) {
        Toast.makeText(getActivity(), s + "*" + dflatno + "*" + dmobile, Toast.LENGTH_SHORT).show();
        if (s.equalsIgnoreCase("delete")) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Delete");
            builder.setMessage("are u sure u want to delete this entry ");
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    choice = 0;

                }

            });
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.out.println(dflatno);

                    Cursor id = myDb.tb_owner_getid(String.valueOf(dflatno));

                    int abc=0;
                    if (id.moveToNext())
                        abc = id.getInt(id.getColumnIndex("id"));
                    System.out.println(abc);
                    Integer ans = myDb.deleteData(dflatno.toString().trim());
                    try {
                        boolean ans1 = myDb.deletefrompersninfo(abc);
                        System.out.println(ans1);
                    }catch (Exception e)
                    {
                        System.out.println(e.toString());
                    }
                   // Toast.makeText(getActivity(), "deletion" + ans+"*"+ans1, Toast.LENGTH_SHORT).show();
                    disp();
                }


            });
            builder.show();
        } else if (s.equalsIgnoreCase("call")) {
            Cursor id = myDb.tb_owner_getid(dflatno);
            int id1=0;
            if (id.moveToNext())
                 id1 = id.getInt(id.getColumnIndex("id"));
            System.out.println(id1);
                Cursor mob = myDb.get_mobile_own(id1);

               if(mob.moveToNext())
                   dmobile = mob.getString(0);
            System.out.println(dmobile);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + dmobile));
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);

            } else if (s.equals("modify")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("MODIFY flatno=" + flatno1);
                builder.setMessage("Flatno");
                EditText flatno = new EditText(getActivity());
            }
        }
    }
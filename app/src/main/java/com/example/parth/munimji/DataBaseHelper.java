package com.example.parth.munimji;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.lang.Integer;
import java.lang.Override;
import java.lang.String;

/**
 * Created by parth on 2/1/2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "society";//db name
    public static final String TB_OWNER = "owner";//table members
    public static final String TB_PAYMENT_IN = "paymentin";//table paymet in
    public static final String TB_PAYMENT_OUT = "paymentout";//table payment out
    public static final int DB_VERSION = 5;
    //for table members
    public static final String NAME = "NAME";
    public static final String FLATNO = "FLATNO";
    public static final String TENANT = "TENANT";
    public static final String MOBILE = "MOBILE";

    public static final String MID = "MID";
    //for table payment in
    public static final String AMOUNT = "AMOUNT";
    public static final String MODE = "MODE";
    public static final String BRANCH = "BRANCH";
    public static final String TRANSID = "TRANSID";
    public static final String CHECKNO = "CHECKNO";
    public static final String DAY = "DAY";
    public static final String MONTH = "MONTH";
    public static final String YEAR = "YEAR";
    public static final String QUARTER = "QUARTER";
    public static final String DATE = "DATE";

    // for table payment out

    public static final String COMPANYNAME = "COMPANYNAME";


    public static final String EMAIL = "email";

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("create table type (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,PAYT TEXT NOT NULL unique);");
       // db.execSQL("create table " + TB_OWNER + "(FLATNO TEXT PRIMARY KEY NOT NULL,NAME TEXT NOT NULL,MOBILE TEXT NOT NULL,TENANT TEXT NOT NULL,EMAIL TEXT,NUMVECH INTEGER,VECHNUM TEXT);");
       // db.execSQL("create table " + TB_PAYMENT_IN + "(_id INTEGER PRIMARY KEY,personid INTEGER,transdate TEXT,AMOUNT INTEGER,MODE TEXT,CHECKNO INTEGER,BRANCH TEXT,DATE TEXT,TYPE TEXT,receiptno TEXT);");
       // db.execSQL("create table " + TB_PAYMENT_OUT + "(_id INTEGER PRIMARY KEY,COMPANYNAME TEXT,AMOUNT INTEGER,DATE TEXT,MODE TEXT,CHECKNO INTEGER,BRANCH TEXT);");

        ////as per new structure
        db.execSQL("create table tbowner (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,flatno TEXT UNIQUE,name TEXT, occupancy TEXT);");
        db.execSQL("create table tbpersonalinfo (id INTEGER,type INTEGER,info TEXT);");
        db.execSQL("create table tbpayin (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,personid INTEGER,transDate TEXT,AMOUNT INTEGER,MODE TEXT,CHECKNO TEXT,BRANCH TEXT,TYPE TEXT,entryDate TEXT);");
        db.execSQL("create table tbtenant (tid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,flatid INTEGER,name TEXT,joindate TEXT,leftdate TEXT);");
        db.execSQL("create table tbpayout (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,companyid INTEGER,AMOUNT INTEGER,transdate TEXT,MODE TEXT,CHECKNO INTEGER,BRANCH TEXT,date TEXT);");
        db.execSQL("create table tbcompanyinfo (companyid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,name TEXT UNIQUE,contactperson TEXT,mobile TEXT,description TEXT); ");
        db.execSQL("create table HelpLine (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,desc TEXT,mobile TEXT,email TEXT); ");
        db.execSQL("create table tbtenantinfo (id INTEGER,type INTEGER,info TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS" + TB_OWNER);
        //db.execSQL("DROP TABLE IF EXISTS" + TB_PAYMENT_IN);
        //db.execSQL("DROP TABLE IF EXISTS" + TB_PAYMENT_OUT);
        db.execSQL("DROP TABLE IF EXISTS type");
        db.execSQL("DROP TABLE IF EXISTS tbowner");
        db.execSQL("DROP TABLE IF EXISTS tbpersonalinfo");
        db.execSQL("DROP TABLE IF EXISTS tbpayin");
        db.execSQL("DROP TABLE IF EXISTS tbtenant");
        db.execSQL("DROP TABLE IF EXISTS tbpayout");
        db.execSQL("DROP TABLE IF EXISTS tbcompanyinfo");
        db.execSQL("DROP TABLE IF EXISTS HelpLine");
        db.execSQL("DROP TABLE IF EXISTS tbtenantinfo");

        onCreate(db);
    }

//        db.execSQL("create table tbcompanyinfo (companyid INTEGER PRIMARY KEY,name TEXT,contactperson TEXT,mobile TEXT,description TEXT); ");

    public boolean insertcompanyinfo(String cname,String name,String mobile,String desc) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues content = new ContentValues();
            content.put("name", cname);
            content.put("contactperson", name);
            content.put("mobile", mobile);
            content.put("description", desc);
            Long ans = db.insert("tbcompanyinfo", null, content);
            if (ans == -1)
                return false;
            else
                return true;
        }catch (Exception e)
        {
            throw e;
        }
    }

    //join on tbowner nd tb tenant
    //db.execSQL("create table tbtenant (tid INTEGER PRIMARY KEY,flatid INTEGER,name TEXT,joindate TEXT,leftdate TEXT);");
//db.execSQL("create table tbowner (id INTEGER PRIMARY KEY ,flatno TEXT UNIQUE,name TEXT);");

public Cursor tnown_join_tbtenant() {
    try {
        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select id,flatno,tbowner.name,tid from tbowner LEFT OUTER JOIN tbtenant ON tbowner.id = tbtenant.flatid group by flatno ",null);
//        Cursor res = db.rawQuery("select id,flatno,tbowner.name,tid from tbowner Order By flatno ",null);
        //Cursor res = db.rawQuery("select * from tbowner Order By flatno ",null);
        Cursor res = db.rawQuery("select * from tbowner Order By id ",null);
        return res;
    } catch (Exception e) {
        throw e;
    }
}


//        db.execSQL("create table tbtenant (tid INTEGER PRIMARY KEY,flatid INTEGER,name TEXT,joindate TEXT,leftdate TEXT");

    public boolean tb_tenant_ins(int flatid,String name,String joindate,String leftdate)
    {SQLiteDatabase db=this.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("flatid",flatid);
        content.put("name",name);
        content.put("joindate",joindate);
        content.put("leftdate",leftdate);
        Long ans=db.insert("tbtenant",null,content);
        if (ans == -1)
            return false;
        else
            return true;

    }
    public boolean tb_owner_ins(String flatno,String name, String occupancy)
    {//id INTEGER PRIMARYKEY,flatno TEXT UNIQUE,NAME TEXT"
        SQLiteDatabase db=this.getWritableDatabase();
     ContentValues content=new ContentValues();
        content.put("flatno",flatno);
        content.put("name",name);
        content.put("occupancy",occupancy);
        Long ans=db.insert("tbowner",null,content);
        if (ans == -1)
            return false;
        else
            return true;

    }
    public Cursor tb_owner_getid(String flatno)
    {   try {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select id from tbowner WHERE flatno =?",new String[]{flatno});
        return res;
    }catch (Exception e) {
        throw e;
    }
    }
//        tbtenant (tid INTEGER PRIMARY KEY,flatid INTEGER,name TEXT,joindate TEXT,leftdate TEXT);");
    public Cursor tb_tenant_getid(int flatno)
    {   try {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select tid from tbtenant WHERE flatid = "+flatno,null);
        return res;
    }catch (Exception e) {
        throw e;
    }
    }
//db.execSQL("create table tbcompanyinfo (companyid INTEGER PRIMARY KEY,name TEXT UNIQUE,contactperson TEXT,mobile TEXT,description TEXT); ");
public Cursor tb_owner_getcompanyname(int cname)
{   try {
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor res = db.rawQuery("select name from tbcompanyinfo WHERE companyid ="+cname,null);
    return res;
}catch (Exception e) {
    throw e;
}
}
    public Cursor get_mobile_own(int id) {//tbpersonalinfo (id INTEGER,type INTEGER,info TEXT)
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            //System.out.println("select  info from tbpersonalinfo WHERE id=" + id +" AND type=" + 1);
            Cursor res = db.rawQuery("select  info from tbpersonalinfo WHERE id=" + id +" AND type=" + 1, null);
            //System.out.println("Record Count:"+res.getCount());
            return res;
        } catch (Exception e) {
            throw e;
        }
    }

    public Cursor get_email_own(int id) {//tbpersonalinfo (id INTEGER,type INTEGER,info TEXT)
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            //System.out.println("select  info from tbpersonalinfo WHERE id=" + id +" AND type=" + 1);
            Cursor res = db.rawQuery("select  info from tbpersonalinfo WHERE id=" + id +" AND type=" + 2, null);
            //System.out.println("Record Count:"+res.getCount());
            return res;
        } catch (Exception e) {
            throw e;
        }
    }

    public Cursor get_vehicle_own(int id) {//tbpersonalinfo (id INTEGER,type INTEGER,info TEXT)
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            //System.out.println("select  info from tbpersonalinfo WHERE id=" + id +" AND type=" + 1);
            //Cursor res = db.rawQuery("select  info,type from tbpersonalinfo WHERE id=" + id +" AND type in (3,4)", null);
            Cursor res = db.rawQuery("select  info,type from tbpersonalinfo WHERE id=" + id +" AND type in (3,4) ORDER BY type", null);
            //System.out.println("Record Count:"+res.getCount());
            return res;
        } catch (Exception e) {
            throw e;
        }
    }

    public Cursor tb_owner_getcompanyid(String cname)
    {   try {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select companyid from tbcompanyinfo WHERE name =?",new String[]{cname});
        return res;
    }catch (Exception e) {
        throw e;
    }
    }
    public boolean tb_persinfo_ins(int id,int type,String info)
    {//create table tbpersonalinfo (id INTEGER,type INTEGER,info TEXT
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("id",id);
        content.put("type",type);
        content.put("info",info);
        Long ans=db.insert("tbpersonalinfo",null,content);
        if (ans == -1)
            return false;
        else
            return true;
    }

    public boolean tb_tenantinfo_ins(int id,int type,String info)
    {//create table tbpersonalinfo (id INTEGER,type INTEGER,info TEXT
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("id",id);
        content.put("type",type);
        content.put("info",info);
        Long ans=db.insert("tbpersonalinfo",null,content);
        if (ans == -1)
            return false;
        else
            return true;
    }



    public boolean tb_tenanat_ins(int id,int type,String info)
    {//create table tbpersonalinfo (id INTEGER,type INTEGER,info TEXT
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("id",id);
        content.put("type",type);
        content.put("info",info);
        Long ans=db.insert("tbpersonalinfo",null,content);
        if (ans == -1)
            return false;
        else
            return true;
    }
    //        db.execSQL("create table tbpersonalinfo (id INTEGER,type INTEGER,info TEXT);");
    public boolean deletefrompersninfo(int id) {
        System.out.println("inside deletefrom pinfo");
        SQLiteDatabase db = this.getWritableDatabase();
      // db.rawQuery("delete from owner where FLATNO =?",new String[]{name});
            int ans = db.delete("tbpersonalinfo", "id = "+id,null);
            if (ans == -1)
                return false;
            else
                return true;


    }


    public boolean insertData(String Ptype) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues content = new ContentValues();
            content.put("PAYT", Ptype);

            long result = db.insert("type", null, content);
            if (result == -1)
                return false;
            else
                return true;

        } catch (Exception e) {
            throw e;
        }
    }








//(_id ,personid ,transdate ,AMOUNT ,MODE ,CHECKNO ,BRANCH TEXT,DATE TEXT,TYPE TEXT,receiptno TEXT);
//    public boolean insertData_tbpayin(int personid, int amount,String transdate, String mode, int checkno, String branch, String date,String ptype,String receipt) {
    public boolean insertData_tbpayin(int personid, int amount,String transdate, String mode, String checkno, String branch, String date,String ptype) {
        try {
            //System.out.println("Entry Date:"+transdate+" Cehckdate:"+date+" ID:"+personid);
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues content = new ContentValues();
            content.put("personid", personid);
            content.put(AMOUNT, amount);
            content.put(MODE, mode);
            content.put(CHECKNO, checkno);
            content.put(BRANCH, branch);
            content.put("entryDate", transdate);
            content.put("transDate",date);
            content.put("TYPE",ptype);
            //content.put("receiptno",receipt);
            long result = db.insert("tbpayin", null, content);
            System.out.println("Entry Date:"+transdate+" Cehckdate:"+date+" ID:"+personid);
            if (result == -1)
                return false;
            else
                return true;
        } catch (Exception e) {
            throw e;
        }

    }
// tbpayout (id INTEGER PRIMARY KEY,companyid INTEGER,AMOUNT INTEGER,transdate TEXT,MODE TEXT,CHECKNO INTEGER,BRANCH TEXT,date TEXT);");

    public boolean insertData(int cname, int amount, String date, String mode, int checkno, String branch,String cdate) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues content = new ContentValues();
            content.put("companyid", cname);
            content.put(AMOUNT, amount);
            content.put("transdate", date);//MODE TEXT,CHECKNO INTEGER,BRANCH TEXT
            content.put(MODE, mode);
            content.put(CHECKNO, checkno);
            content.put(BRANCH, branch);
            content.put("date", cdate);

            long result = db.insert("tbpayout", null, content);
            if (result == -1)
                return false;
            else
                return true;
        } catch (Exception e) {
            throw e;
        }
    }

    public Cursor getAllData(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + name, null);
        return res;


    }

    public Cursor getalldata() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from type" , null);
        return res;
    }

    public Cursor getAllDataown(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        //Cursor res = db.rawQuery("select * from " + name + "  ORDER BY FLATNO", null);
        Cursor res = db.rawQuery("select * from " + name + "  ORDER BY id", null);
        return res;


    }

    public Cursor getOwnerName(String flatNumber) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select name from tbowner where flatno='" + flatNumber + "'", null);
        return res;


    }
//        db.execSQL("create table tbpayout (id INTEGER PRIMARY KEY,companyid INTEGER,AMOUNT INTEGER,transdate TEXT,MODE TEXT,CHECKNO INTEGER,BRANCH TEXT,date TEXT);");
    public Cursor distcname(String from, String to) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res3 = db.rawQuery("select DISTINCT companyid from tbpayout WHERE transdate BETWEEN ? AND ?", new String[]{from + "-04-01", to + "-03-31"});
        return res3;
    }

    public Cursor distcname() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res3 = db.rawQuery("select DISTINCT name from tbcompanyinfo ", null);
        return res3;
    }
//        db.execSQL("create table tbpayout (id INTEGER PRIMARY KEY,companyid INTEGER,AMOUNT INTEGER,transdate TEXT,MODE TEXT,CHECKNO INTEGER,BRANCH TEXT,date TEXT);");

    public Cursor listpaymentout(int name, String from, String to) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor res2 = db.rawQuery("select SUM(AMOUNT), transdate,MODE from tbpayout WHERE companyid= "+name+" AND transdate BETWEEN ? AND ? GROUP BY strftime('%m',transdate) ORDER BY transdate", new String[]{ from + "-04-01", to + "-03-31"});
            // System.out.println(res2.getString(res2.getColumnIndex(DATE)));
            //Cursor cursor = db.rawQuery("select DATE from paymentout",null);
            System.out.println("query in databasehelper");
            return res2;
        } catch (Exception e) {
            throw e;
        }


    }

    public Cursor distflatno(String from, String to) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res3 = db.rawQuery("select flatno,tid from tbowner LEFT OUTER JOIN tbtenant ON tbowner.id = tbtenant.flatid group by flatno ",null);
            return res3;
        } catch (Exception e) {
            throw e;
        }
    }


    public Cursor distflatno() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res3 = db.rawQuery("select DISTINCT FLATNO from tbowner ", null);
            return res3;
        } catch (Exception e) {
            throw e;
        }
    }
//         tbpayin (_id ,personid ,transdate ,AMOUNT ,MODE ,CHECKNO ,BRANCH ,DATE ,TYPE ,receiptno );");

    public Cursor listpaymentin(int flatno, String from, String to) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {

//            Cursor res2 = db.rawQuery("select SUM(AMOUNT),transdate,MODE,receiptno from tbpayin WHERE _id= "+flatno+"  AND transdate BETWEEN ? AND ? GROUP BY strftime('%m',transdate) ORDER BY transdate", new String[]{from + "-04-01", to + "-03-31"});
//           Cursor res2 = db.rawQuery("select SUM(AMOUNT),transdate,MODE from tbpayin WHERE personid= "+flatno+"  AND transdate BETWEEN ? AND ? GROUP BY strftime('%m',transdate) ORDER BY transdate", new String[]{from + "-04-01", to + "-03-31"});
            Cursor res2 = db.rawQuery("select SUM(AMOUNT),date(entryDate) as entryDate,MODE from tbpayin WHERE personid= "+flatno+"  AND entryDate BETWEEN ? AND ? GROUP BY strftime('%m',entryDate) ORDER BY entryDate", new String[]{from + "-04-01", to + "-03-31"});
            //Cursor res2 = db.rawQuery("select SUM(AMOUNT),entryDate,MODE from tbpayin WHERE personid= "+flatno+"  AND entryDate BETWEEN ? AND ? GROUP BY strftime('%m',entryDate) ORDER BY entryDate", new String[]{"01-04-"+from , "31-03-"+to});
            //Cursor res2 = db.rawQuery("select AMOUNT,entryDate,MODE, strftime('%m','07-04-2012') as mth from tbpayin WHERE personid= "+flatno+"  AND entryDate BETWEEN ? AND ? ORDER BY entryDate", new String[]{"01-04-"+from , "31-03-"+to});
            //Cursor res2 = db.rawQuery("select SUM(AMOUNT),entryDate,MODE from tbpayin WHERE personid= "+flatno+"  AND entryDate BETWEEN '"+"01-04-"+from+"' AND '"+"31-03-"+to+"' GROUP BY strftime('%m',entryDate) ORDER BY entryDate", null);
            //Cursor res2 = db.rawQuery("select SUM(AMOUNT),transDate,MODE from tbpayin WHERE personid= "+flatno+"  GROUP BY strftime('%m',transDate) ORDER BY transDate",null);
            //System.out.println("flatno=" + flatno);
            //System.out.println("query in listpaymentin::"+res2.getCount());
            return res2;
        } catch (Exception e) {
            throw e;
        }
    }

    public Cursor listpaymentin(int flatno, String from, String to,String ptype) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {

            Cursor res2 = db.rawQuery("select SUM(AMOUNT), entryDate,MODE,receiptno from tbpayin WHERE _id= "+flatno+" AND TYPE = ?  AND entryDate BETWEEN ? AND ? GROUP BY strftime('%m',entryDate) ORDER BY entryDate", new String[]{ptype, from + "-04-01", to + "-03-31"});
//            Cursor res2 = db.rawQuery("select SUM(AMOUNT), transDate,MODE from tbpayin WHERE personid= "+flatno+" AND TYPE = ?  AND transDate BETWEEN ? AND ? GROUP BY strftime('%m',transDate) ORDER BY transDate", new String[]{ptype, from + "-04-01", to + "-03-31"});
//            Cursor res2 = db.rawQuery("select SUM(AMOUNT), entryDate,MODE from tbpayin WHERE personid= "+flatno+" AND TYPE = ?  AND entryDate BETWEEN ? AND ? GROUP BY strftime('%m',entryDate) ORDER BY entryDate", new String[]{ptype, "01-04-"+from, "31-03-"+to});
            //Cursor res2 = db.rawQuery("select AMOUNT, transDate,MODE, strftime('%m',transDate) as month from tbpayin WHERE personid= "+flatno+" AND TYPE = ? GROUP BY transDate ORDER BY transDate",new String[]{ptype});
            //System.out.println("flatno=" + flatno);
            //System.out.println("query in listpaymentin : "+res2.getCount());
            return res2;
        } catch (Exception e) {
            throw e;
        }

    }

   /* public Cursor getpayinlist(String name)
    {SQLiteDatabase db=this.getWritableDatabase();
       Cursor res=db.q
       return res;

    }*/

    //will do this afterwards
    public boolean updateData(String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(NAME, name);
        content.put(EMAIL, email);
        db.update(TB_OWNER, content, "Name = ?", new String[]{name});
        return true;


    }

    public Integer deleteData(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        try { // db.rawQuery("delete from owner where FLATNO =?",new String[]{name});
            //SQLiteDatabase db = this.getWritableDatabase();
            Cursor res2 = this.tb_owner_getid(name);
            res2.moveToNext();
            int id = res2.getInt(res2.getColumnIndex("id"));
            db.delete("tbowner", "FLATNO = ?", new String[]{name});
            return db.delete("tbpersonalinfo", "id = ?", new String[]{res2.getString(res2.getColumnIndex("id"))});
            //return db.delete(TB_OWNER, FLATNO + "=" +name, null);
        } catch (Exception e) {
            return 0;
        }

    }
    public Integer deleteData(String name,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        try { // db.rawQuery("delete from owner where FLATNO =?",new String[]{name});
            return db.delete(TB_PAYMENT_IN, "FLATNO = ? AND DATE = ?", new String[]{name,date});
            //return db.delete(TB_OWNER, FLATNO + "=" +name, null);
        } catch (Exception e) {
            System.out.println(e.toString());
            return 0;
        }

    }
    public Integer deleteData(String name,int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try { // db.rawQuery("delete from owner where FLATNO =?",new String[]{name});
            return db.delete(name, "_id = "+id, null);
            //return db.delete(TB_OWNER, FLATNO + "=" +name, null);
        } catch (Exception e) {
            System.out.println(e.toString());
            return 0;
        }

    }
//         tbpayin (_id ,personid ,transdate ,AMOUNT ,MODE ,CHECKNO ,BRANCH ,DATE ,TYPE ,receiptno );");

    //for details info
    public Cursor detpaymentin(int flatno, String from, String to,String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {

            Cursor res2 = db.rawQuery("select * from tbpayin WHERE personid= "+flatno+" AND TYPE = ? AND transdate BETWEEN ? AND ?  ORDER BY transdate", new String[]{type, "01-04-"+from, "31-03-"+to});
            System.out.println("flatno=" + flatno);
            System.out.println("query outog detpaymentin");
            return res2;
        } catch (Exception e) {
            throw e;
        }
    }
//tbpayout (id INTEGER PRIMARY KEY,companyid INTEGER,AMOUNT INTEGER,transdate TEXT,MODE TEXT,CHECKNO INTEGER,BRANCH TEXT,date TEXT);");

    public Cursor detpaymentout(int flatno, String from, String to) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {

            Cursor res2 = db.rawQuery("select * from tbpayout WHERE companyid= "+flatno+" AND transdate BETWEEN ? AND ?  ORDER BY DATE", new String[]{from + "-04-01", to + "-03-31"});
            System.out.println("flatno=" + flatno);
            System.out.println("query outog detpaymentout");
            return res2;
        } catch (Exception e) {
            throw e;
        }

    }
    //        db.execSQL("create table HelpLine (id INTEGER PRIMARY KEY ,desc TEXT,mobile TEXT,email TEXT); ");

    public boolean addhelp(String desc,String mobile,String email)
    {try{        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put("email",email);
        content.put("mobile",mobile);
        content.put("desc",desc);

        long result = db.insert("HelpLine", null, content);
        if (result == -1)
            return false;
        else
            return true;
    } catch (Exception e) {
        throw e;
    }

    }
}
//tbowner  tbpersonalinfo   tbpayin  tbtenant  tbpayout  tbcompanyinfo  HelpLine
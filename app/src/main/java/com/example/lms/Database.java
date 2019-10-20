package com.example.lms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database";
//tables
    private static final String TABLE_ADMIN = "Admins";
    private static final String TABLE_EMPLOYEE =  "Employees";
    private static final String TABLE_LEAVES =  "Leaves";
//for admin
    private static final String AID =  "id";
    private static final String AUSERNAME =  "username";
    private static final String APASS =  "pass";
 //for employee
    private static final String EID =  "id";
    private static final String ENAME =  "name";
    private static final String EUSERNAME =  "username";
    private static final String EPASS =  "pass";
    private static final String EEMAIL =  "email";
//for leaves
    private static final String LID =  "lid";
    private static final String EID2 =  "eid";
    private static final String LD1 =  "datefrom";
    private static final String LD2 =  "dateto";
    private static final String LSUBJECT =  "subject";
    private static final String LREASON =  "reason";
    private static final String LSTATUS =  "status";

    //create tables
    private static final String CRT_ADMIN = "CREATE TABLE "+TABLE_ADMIN+"("+AID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+AUSERNAME+" TEXT NOT NULL,"+APASS+" TEXT NOT NULL);";
    private static final String CRT_EMPLOY = "CREATE TABLE "+TABLE_EMPLOYEE+"("+EID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ENAME+" TEXT NOT NULL,"+EUSERNAME+" TEXT NOT NULL,"+EPASS+" TEXT NOT NULL,"+EEMAIL+" TEXT NOT NULL);";
    private static final String CRT_LEAVE = "CREATE TABLE "+TABLE_LEAVES+"("+LID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+EID2+" INTEGER NOT NULL,"+LD1+" TEXT NOT NULL,"+LD2+" TEXT NOT NULL,"+LSUBJECT+" TEXT ,"+LREASON+" TEXT NOT NULL,"+LSTATUS+" INTEGER NOT NULL);";

    public Database(Context context){
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CRT_ADMIN);
        db.execSQL(CRT_EMPLOY);
        db.execSQL(CRT_LEAVE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS '"+TABLE_ADMIN+"'");
        db.execSQL("DROP TABLE IF EXISTS '"+TABLE_EMPLOYEE+"'");
        db.execSQL("DROP TABLE IF EXISTS '"+TABLE_LEAVES+"'");
        onCreate(db);
    }

    public boolean addAdmin(AdminInfo ad){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(AUSERNAME,ad.getUsername());
        values.put(APASS,ad.getPass());

        long result = db.insert(TABLE_ADMIN, null, values);
        if (result == -1) {
            return false;
        }
        else
            return true;
    }

    public boolean addEmpl(EmpInfo em){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ENAME,em.getName());
        values.put(EUSERNAME,em.getUsername());
        values.put(EPASS,em.getPass());
        values.put(EEMAIL,em.getEmail());
;
        long result = db.insert(TABLE_EMPLOYEE, null, values);
        if (result == -1)
            return false;
        else
            return true;

    }

    public boolean addLeave(LeaveInfo lv){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EID2,lv.getEid());

        values.put(LD1,lv.getDatefrom());
        values.put(LD2,lv.getDateto());
        values.put(LREASON,lv.getReason());
        values.put(LSUBJECT,lv.getSubject());

        values.put(LSTATUS,0);

        long result = db.insert(TABLE_LEAVES, null, values);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean setStatusA(String flag,String leaveid){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues val = new ContentValues();

        val.put(LSTATUS,flag);

        long result = db.update(TABLE_LEAVES,val,LID+"=?",new String[]{leaveid});
        if (result == -1)
            return false;
        else
            return true;
//        String App = "UPDATE TABLE_LEAVE SET STATUS = 1 ";
//        db.execSQL(App);
    }

    public Cursor AgetData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_ADMIN, null);
        return res;
    }

    public Cursor EgetData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_EMPLOYEE, null);
        return res;
    }

    public Cursor EgetDataFromId(String id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_EMPLOYEE + " WHERE " + EID  + " LIKE " + id, null);
        return res;
    }

    public Cursor AgetDataFromId(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_ADMIN + " WHERE " + AID  + " LIKE " + id, null);
        return res;
    }

    public Cursor LgetData(String empid) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_LEAVES+" WHERE EID LIKE "+empid, null);
        return res;
    }
    public Cursor LAllgetData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_LEAVES, null);
        return res;
    }

}

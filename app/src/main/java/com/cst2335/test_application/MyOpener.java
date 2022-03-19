package com.cst2335.test_application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpener extends SQLiteOpenHelper {
    protected final static String Database_Name= "lab_5_database";
    protected final static int version =1;
    public final static String table_Name= "message_Table";
    public final static String col_Message= "message";
    public final static String col_Send_Rec="Send_Or_Receive";
    public final static String col_Id= "col_Id";
    public MyOpener(Context ctx){
        super(ctx, Database_Name, null,  version);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("Create Table " +table_Name+"(col_Id Integer Primary key autoincrement,"
        + col_Message + " text,"
        + col_Send_Rec + " integer);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_Ver, int new_Ver) {
        db.execSQL("Drop Table If Exists "+table_Name);
        onCreate(db);
    }
//    @Override
//    public void onDowngrade(SQLiteDatabase){
//
//    }

}

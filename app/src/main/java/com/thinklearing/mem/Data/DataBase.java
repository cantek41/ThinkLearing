package com.thinklearing.mem.Data;


import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBase extends SQLiteOpenHelper {


    public DataBase(Context context) {
        super(context, DataBaseArg.DATABASE_NAME, null, DataBaseArg.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("DROP TABLE IF EXISTS " + DataBaseArg.TBL_klm);
//        String qu="CREATE TABLE " + DataBaseArg.TBL_klm + "("
//                + DataBaseArg.Id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + DataBaseArg.Word + " TEXT NOT NULL, "
//                + DataBaseArg.Level + " INTEGER NOT NULL, "
//                + DataBaseArg.Eq + " TEXT NOT NULL)";
//        Log.d("Database",qu);
//        db.execSQL(qu);
//
//
//        db.execSQL("DROP TABLE IF EXISTS " + DataBaseArg.TBL_Fire);
//
//        qu="CREATE TABLE " + DataBaseArg.TBL_Fire + "("
//                + DataBaseArg.Id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + DataBaseArg.Level + " INTEGER NOT NULL, "
//                + DataBaseArg.Word + " TEXT NOT NULL, "
//                + DataBaseArg.Eq + " TEXT NOT NULL)";
//
//        db.execSQL(qu);
//
        Log.d("Database","TBL_Log");
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseArg.TBL_Log);
//
//        qu="CREATE TABLE " + DataBaseArg.TBL_Log + "("
//                + DataBaseArg.Id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + DataBaseArg.Date + " DATE NOT NULL, "
//                + DataBaseArg.CLASS + " TEXT NOT NULL, "
//                + DataBaseArg.Number + " INTEGER NOT NULL)";
//        db.execSQL(qu);
//        Log.d("Database",qu);
//
//        db.execSQL("DROP TABLE IF EXISTS " + DataBaseArg.TBL_Status);
//
//        qu="CREATE TABLE " + DataBaseArg.TBL_Status + "("
//                + DataBaseArg.Id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + DataBaseArg.Sta_Antoim + " INTEGER NOT NULL, "
//                + DataBaseArg.Sta_fire_level + " INTEGER NOT NULL, "
//                + DataBaseArg.Sta_Ing_Trk + " INTEGER NOT NULL, "
//                + DataBaseArg.Sta_klm + " INTEGER NOT NULL, "
//                + DataBaseArg.Sta_trk_ing + " INTEGER NOT NULL, "
//                + DataBaseArg.Sta_syn + " INTEGER NOT NULL)";
//        db.execSQL(qu);
//        Log.d("Database",qu);
//
//        db.execSQL("INSERT INTO " + DataBaseArg.TBL_Status
//                + " VALUES(1,0,0,0,0,0,0)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        for (String tbl : tablolar
//        ) {
//            db.execSQL("DROP TABLE IF EXISTS " + tbl);
//        }
//        onCreate(db);
    }


    public static void setDefaultDataBase(Activity context,String name) {
        try {
//            DataBase db=new DataBase(context);
//            db.getDatabaseName();
//            db.close();
            Log.d("Database","setDefaultDataBase");
            Log.d("Database",name);

            AssetManager am = context.getAssets();
//            InputStream myInput = am.open(DataBaseArg.DB_DEFAULT_NAME);
            InputStream myInput = am.open(name);

//            InputStream myInput = context.getAssets().open(DB_DEFAULT_NAME);
            // Path to the just created empty db
            String outFileName = DataBaseArg.DB_PATH + DataBaseArg.DATABASE_NAME;
            File newFile = new File(DataBaseArg.DB_PATH);
            newFile.mkdir();

            //Open the empty db as the output stream
            Log.i("outFileName",outFileName);
            OutputStream myOutput = new FileOutputStream(outFileName);
            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
            Log.i("DataBASE","KOPYALANDI");
        } catch (IOException e) {
            Log.e("DataBASE","Kopyalama HATASIII");
            e.printStackTrace();
        }
    }

}

package com.thinklearing.mem.Data;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.anychart.scales.DateTime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DataBaseHelper {
    DataBase DB;

    public DataBaseHelper(Context context) {
        String outFileName = DataBaseArg.DB_PATH + DataBaseArg.DATABASE_NAME;
        File f = new File(outFileName);
        if(f.exists())
        {
            DB = new DataBase(context);
            Log.i("DataBaseHelper","Data VAR");
        }else{
            Log.e("DataBaseHelper","Data yok");
        }
    }

    public void dbHazirla() {
        DB.onCreate(DB.getWritableDatabase());
    }

    public List<Integer> LevelGetir(String tbl) {
        List<Integer> veriler = new ArrayList<>();
        SQLiteDatabase db = DB.getReadableDatabase();
        Cursor cursor = null;
        try {
            String[] sutunlar = {DataBaseArg.Level};
            cursor = db.query(true, tbl, sutunlar, null,
                    null, "level", null, "level", null);

            while (cursor.moveToNext()) {
                veriler.add(Integer.valueOf(cursor.getInt(0)));
                Log.i("Leveller Çekiliyor", String.valueOf(cursor.getInt(0)));
            }

        } catch (Exception e) {
            Log.e("LevelGetir", e.getMessage());
        } finally {
            cursor.close();
            db.close();
        }

        return veriler;
    }

    public int getLevelStatus(String colum) {
        SQLiteDatabase db = DB.getReadableDatabase();
        Cursor cursor = null;
        int mx = -1;
        try {
            cursor = db.rawQuery("SELECT " + colum + " FROM " + DataBaseArg.TBL_Status, new String[]{});
            if (cursor != null)
                if (cursor.moveToFirst()) {
                    mx = cursor.getInt(0);
                }
        } catch (Exception e) {
        } finally {
            cursor.close();
            db.close();
        }
        return mx;
    }


    public List<DataModel> LevelDataGetir(String tbl, int level) {
        SQLiteDatabase db = DB.getReadableDatabase();
        List<DataModel> veriler = new ArrayList<>();
        DataModel model;
        Cursor cursor = null;
        try {
            String[] sutunlar = {DataBaseArg.Word, DataBaseArg.Eq};
            String cond = "level = " + level;
            cursor = db.query(tbl, sutunlar, "level =?",
                    new String[]{String.valueOf(level)}, null, null, null);

            while (cursor.moveToNext()) {
                model = new DataModel();
                model.Word = cursor.getString(0);
                model.Eq = cursor.getString(1);
                veriler.add(model);
                Log.i("Veriler Çekiliyor", model.Word + " " + model.Eq);
            }
        } catch (Exception e) {
            Log.e("LevelDataGetir", "level = " + level);
            Log.e("LevelDataGetir", e.getMessage());

        } finally {
            cursor.close();
            db.close();
        }

        return veriler;
    }


    public List<RaportList> RaporGetir() {
        SQLiteDatabase db = DB.getReadableDatabase();
        List<RaportList> veriler = new ArrayList<>();
        RaportList model;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(" select\n" +
                    "    CAST(strftime('%W', date_time) as INTEGER) WeekNumber,\n" +
                    "    max(date(date_time, 'weekday 1')) WeekStart,\n" +
                    "    max(date(date_time, 'weekday 1', '+6 day')) WeekEnd,\n" +
                    "    sum(rightnumber) as Total\n" +
                    "    from aa_Log\n" +
                    "    group by WeekNumber \n" +
                    "    ORDER by  date_time DESC;", null);

            while (cursor.moveToNext()) {
                model = new RaportList();
                model.WeekNumber = cursor.getInt(0);
                model.WeekStart = cursor.getString(1);
                model.WeekEnd = cursor.getString(2);
                model.Total = cursor.getInt(3);
                veriler.add(model);
                Log.i("Veriler Çekiliyor", model.WeekNumber + " " + model.Total);
            }
        } catch (Exception e) {
            Log.e("RaporGetir", e.getMessage());

        } finally {
            cursor.close();
            db.close();
        }

        return veriler;
    }


    public List<RaportWeek> HaftayaGoreRaporGetir(int hafta) {
        SQLiteDatabase db = DB.getReadableDatabase();
        List<RaportWeek> veriler = new ArrayList<>();
        RaportWeek model;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select \n" +
                    "cast (strftime('%w', date_time) as integer) as WeekNumber,\n" +
                    "cast (strftime('%w', date_time) as integer) as dayNumber,\n" +
                    "  case cast (strftime('%w', date_time) as integer)\n" +
                    "  when 0 then 'Sun'\n" +
                    "  when 1 then 'Mon'\n" +
                    "  when 2 then 'Tue'\n" +
                    "  when 3 then 'Wed'\n" +
                    "  when 4 then 'Thr'\n" +
                    "  when 5 then 'Fri'\n" +
                    "  else 'Str' end as servdayofweek,\n" +
                    "  sum(rightnumber) as total\n" +
                    "from aa_Log\n" +
                    "WHERE  WeekNumber=" + hafta + "\n" +
                    "ORDER By dayNumber;", null);

            while (cursor.moveToNext()) {
                model = new RaportWeek();
                model.WeekNumber = cursor.getInt(0);
                model.dayNumber = cursor.getInt(1);
                model.servdayofweek = cursor.getString(2);
                model.Total = cursor.getInt(3);
                veriler.add(model);
                Log.i("Veriler Çekiliyor", model.WeekNumber + " " + model.Total);
            }
        } catch (Exception e) {
            Log.e("RaporGetir", e.getMessage());

        } finally {
            cursor.close();
            db.close();
        }

        return veriler;
    }

//    public static void setDefaultDataBase(Activity context) {
//        try {
//
//            AssetManager am = context.getAssets();
//            InputStream myInput = am.open(DataBaseArg.DB_DEFAULT_NAME);
//
////            InputStream myInput = context.getAssets().open(DB_DEFAULT_NAME);
//            // Path to the just created empty db
//            String outFileName = DataBaseArg.DB_PATH + DataBaseArg.DATABASE_NAME;
//            //Open the empty db as the output stream
//            OutputStream myOutput = new FileOutputStream(outFileName);
//            //transfer bytes from the inputfile to the outputfile
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = myInput.read(buffer)) > 0) {
//                myOutput.write(buffer, 0, length);
//            }
//            //Close the streams
//            myOutput.flush();
//            myOutput.close();
//            myInput.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public int getId(String tbl) {
        SQLiteDatabase db = DB.getReadableDatabase();
        Cursor cursor = null;
        int mx = -1;
        try {
            cursor = db.rawQuery("SELECT MAX(" + DataBaseArg.Id + ") FROM " + tbl, new String[]{});
            if (cursor != null)
                if (cursor.moveToFirst()) {
                    mx = cursor.getInt(0);
                }
        } catch (Exception e) {
        } finally {
            cursor.close();
            db.close();
        }
        return mx;
    }

    public boolean getStsCheck(String colm) {
        SQLiteDatabase db = DB.getReadableDatabase();
        Cursor cursor = null;
        int mx = 0;
        try {
            cursor = db.rawQuery("SELECT Max(" + colm + ") FROM " + DataBaseArg.TBL_sts, new String[]{});
            if (cursor != null)
                if (cursor.moveToFirst()) {
                    mx = cursor.getInt(0);
                }
        } catch (Exception e) {
        } finally {
            cursor.close();
            db.close();
        }
        if (mx > 0)
            return false; //sınavı var
        return true; // sınavı yok
    }

    public List<StsDataModel> StsDataGetir(String typ) {
        SQLiteDatabase db = DB.getReadableDatabase();
        List<StsDataModel> veriler = new ArrayList<>();
        StsDataModel model;
        Cursor cursor = null;
        try {
            String[] sutunlar = {
                    DataBaseArg.question,
                    DataBaseArg.chA,
                    DataBaseArg.chB,
                    DataBaseArg.chC,
                    DataBaseArg.chD,
                    DataBaseArg.answer
            };
            cursor = db.query(DataBaseArg.TBL_exam, sutunlar, "typ =?",
                    new String[]{String.valueOf(typ)}, null, null, null);

            while (cursor.moveToNext()) {
                model = new StsDataModel();
                model.question = cursor.getString(0);
                model.Word = cursor.getString(0);
                model.chA = cursor.getString(1);
                model.chB = cursor.getString(2);
                model.chC = cursor.getString(3);
                model.chD = cursor.getString(4);
                model.Eq = cursor.getString(5);
                model.answer = cursor.getString(5);
                model.typ = typ;
                veriler.add(model);
                Log.i("Veriler Çekiliyor", model.Word + " " + model.Eq);
            }
        } catch (Exception e) {
            Log.e("StsDataGetir", "tipi = " + typ);
            Log.e("StsDataGetir", e.getMessage());

        } finally {
            cursor.close();
            db.close();
        }

        return veriler;
    }

    public void VeriEkle(String tbl, String[] col, String[] data) {
        SQLiteDatabase db = DB.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            for (int i = 0; i < col.length; i++) {
                cv.put(col[i], data[i]);

            }
            db.insert(tbl, null, cv);
        } catch (Exception e) {
        }
        db.close();
    }


    public void VeriGüncelle(String tbl, int Id, String[] col, String[] data) {
        SQLiteDatabase db = DB.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            for (int i = 0; i < col.length; i++) {
                cv.put(col[i], data[i]);
            }
            Log.e(col[0],cv.get(col[0])+" ");
            db.update(tbl, cv, DataBaseArg.Id + "=" + Id, null);
        } catch (Exception e) {
            Log.e("Veri güncelle",data[0]);
            e.printStackTrace();
        }
        db.close();
    }


    public boolean getControl(Date date) {
        try{
            SQLiteDatabase db = DB.getReadableDatabase();
            Cursor cursor = null;
            Date mx =null;
            try {
                String sql="SELECT " + DataBaseArg.sure + " FROM " + DataBaseArg.TBL_Status;
                Log.d("pre getControl sql",sql);
                cursor = db.rawQuery(sql, new String[]{});
                Log.d("getControl sql",sql);
                if (cursor != null)
                    if (cursor.moveToFirst()) {
                        mx = new Date(Long.parseLong(cursor.getString(0)));
                        Log.d("mx",mx.toString());
                    }
            } catch (Exception e) {
                Log.d("Exception SQL",e.getMessage());
                e.printStackTrace();
            } finally {
                cursor.close();
                db.close();
            }
            Calendar c = Calendar.getInstance();
            c.setTime(mx);
            c.add(Calendar.MONTH, 6);
            Log.d("control",c.getTime().getTime() +"-"+ date.getTime());
            if (c.getTime().getTime() > date.getTime())
                return false; //geçerli
            return true; // geçerli değil

        }catch (Exception e){
//            Log.d("getControl",e.getMessage());
//            e.printStackTrace();
            return true;

        }

    }

    public boolean getType() {
        SQLiteDatabase db = DB.getReadableDatabase();
        Cursor cursor = null;
        String mx = null;
        try {
            cursor = db.rawQuery("SELECT " + DataBaseArg.type + " FROM " + DataBaseArg.TBL_Status, new String[]{});
            if (cursor != null)
                if (cursor.moveToFirst()) {
                    mx = cursor.getString(0);
                }
        } catch (Exception e) {
        } finally {
            cursor.close();
            db.close();
        }
        Log.d("TYPE",mx+"");
        if (mx=="YDS")
            return true; //yds
        return false; // ilkokul
    }
}

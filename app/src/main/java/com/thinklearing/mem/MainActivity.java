package com.thinklearing.mem;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import com.thinklearing.mem.Data.DataBase;
import com.thinklearing.mem.Data.DataBaseHelper;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //       dataset asset ten kopyalanacak
//        DataBase.setDefaultDataBase(this);


////        AssetManager am = this.getAssets();
//        try {
////            InputStream myInput = am.open("data.db");
//            DataBase.setDefaultDataBase(this); //dataset kopyala
//
//            Log.i("myInput", "myInput");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        DataBaseHelper db=new DataBaseHelper(getBaseContext());
//        db.dbHazirla();

        initMenu();
        showFragment(new mainFragment());
    }


}

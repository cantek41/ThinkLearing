package com.thinklearing.mem;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.thinklearing.mem.Data.DataBase;
import com.thinklearing.mem.Data.DataBaseArg;
import com.thinklearing.mem.Data.DataBaseHelper;
import com.thinklearing.mem.Data.StsDataModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class lisanceFragment extends BaseFragment {

    DataBaseHelper dbHelper;

    public lisanceFragment() {
        LayoutId = R.layout.fragment_lisance;

    }

    @Override
    protected void initFragment() {
        super.initFragment();

        activity.setTitle("Seviye Tespit Sınavı");

        final Calendar c = Calendar.getInstance();
//        c.setTime(new Date());

        int day=c.get(Calendar.DAY_OF_MONTH);
        int month=c.get(Calendar.MONTH)+1;
        final int pass= day*month*78;
        final EditText txt=activity.findViewById(R.id.lisanceEdttxt);
        Log.d("Pass",day+"-"+month+"-"+pass);

        Button stsBtnTrkIng = (Button) activity.findViewById(R.id.linsanceBtnSekiz2);
        stsBtnTrkIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txt.getText().toString().equals(String.valueOf(pass))){
                    DataBase.setDefaultDataBase(getActivity(),DataBaseArg.DB_Sekiz);
                    dbHelper = new DataBaseHelper(getContext());
                    dbHelper.VeriGüncelle(DataBaseArg.TBL_Status,
                            1,
                            new String[]{DataBaseArg.sure,DataBaseArg.type},
                            new String[]{String.valueOf(c.getTime().getTime()),"ILKOKUL"});
                    getActivity().onBackPressed();
                }else{
                    ((BaseActivity)getActivity()).showMessage("Hatalı Şifre");
                }

            }
        });


        Button stsBtnTrkIngEsAnlam = (Button) activity.findViewById(R.id.linsanceBtnYDS2);
        stsBtnTrkIngEsAnlam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txt.getText().toString().equals(String.valueOf(pass))){
                    DataBase.setDefaultDataBase(getActivity(),DataBaseArg.DB_YDS_Name);
                    dbHelper = new DataBaseHelper(getContext());
                    dbHelper.VeriGüncelle(DataBaseArg.TBL_Status,
                            1,
                            new String[]{DataBaseArg.sure,DataBaseArg.type},
                            new String[]{String.valueOf(c.getTime().getTime()),"YDS"});
                    getActivity().onBackPressed();
                }else{
                    ((BaseActivity)getActivity()).showMessage("Hatalı Şifre");
                }
            }
        });


    }


}

package com.thinklearing.mem;

import android.view.View;
import android.widget.Button;

import com.thinklearing.mem.Data.DataBaseArg;
import com.thinklearing.mem.Data.DataBaseHelper;
import com.thinklearing.mem.Data.StaticData;

import java.util.Date;

public class mainFragment extends BaseFragment {
    DataBaseHelper dbHelper;

    public mainFragment() {
        LayoutId = R.layout.fragment_main;
    }



    @Override
    protected void initFragment() {
        super.initFragment();
        activity.setTitle("Ana Menü");
        dbHelper=new DataBaseHelper(getContext());
        boolean control=dbHelper.getControl(new Date());
        if(control){
            ((BaseActivity) getActivity()).showFragment(new lisanceFragment());

        }else{
            boolean ckBtn=dbHelper.getType();
            Button btn = (Button) activity.findViewById(R.id.mainBtnIngTrk);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(dbHelper.getStsCheck(DataBaseArg.ti)){
                        StaticData.Title = "Türkçe İng STS";
                        ((BaseActivity) getActivity()).showFragment(new stsFragment());
                        ((BaseActivity)(getActivity())).showMessage("Türk-İng Çözmelisin");
                    }else {
                        StaticData.Category = DataBaseArg.TBL_ing_trk;
                        StaticData.Title = StaticData.Title_ING_TRK;
                        ((BaseActivity) getActivity()).showFragment(new levelsFragment(DataBaseArg.TBL_ing_trk));

                    }
                }
            });


            Button mainBtnTrkIng = (Button) activity.findViewById(R.id.mainBtnTrkIng);
            mainBtnTrkIng.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(dbHelper.getStsCheck(DataBaseArg.ti)){
                        StaticData.Title = "Türkçe İng STS";
                        ((BaseActivity) getActivity()).showFragment(new stsFragment());
                        ((BaseActivity)(getActivity())).showMessage("Türk-İng Çözmelisin");
                    }else       {
                        StaticData.Title = StaticData.Title_TRK_ING;
                        StaticData.Category = DataBaseArg.TBL_trk_ing;
                        ((BaseActivity) getActivity()).showFragment(new levelsFragment(DataBaseArg.TBL_trk_ing));
                    }
                }
            });


            Button mainBtnZıt = activity.findViewById(R.id.mainBtnZit);
            mainBtnZıt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(dbHelper.getStsCheck(DataBaseArg.ant)){
                        StaticData.Title = "Zıt Anlam STS";
                        ((BaseActivity) getActivity()).showFragment(new stsFragment());
                        ((BaseActivity)(getActivity())).showMessage("Zıt Anlam Testisin Çözmelisin");
                    }else {
                        StaticData.Category = DataBaseArg.TBL_Antoim;
                        StaticData.Title = StaticData.Title_ZIT;
                        ((BaseActivity) getActivity()).showFragment(new levelsFragment(DataBaseArg.TBL_Antoim));
                    }
                }
            });

            Button mainBtnEs = activity.findViewById(R.id.mainBtnEs);
            mainBtnEs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(dbHelper.getStsCheck(DataBaseArg.syn)){
                        StaticData.Title = "Eş Anlam STS";
                        ((BaseActivity)(getActivity())).showMessage("Eş Anlam Testisin Çözmelisin");
                        ((BaseActivity) getActivity()).showFragment(new stsFragment());
                    }else {
                        StaticData.Category = DataBaseArg.TBL_syn;
                        StaticData.Title = StaticData.Title_ES;
                        ((BaseActivity) getActivity()).showFragment(new levelsFragment(DataBaseArg.TBL_syn));
                    }
                }
            });

            Button mainBtnPhr = activity.findViewById(R.id.mainBtnPhr);
            mainBtnPhr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StaticData.Category = DataBaseArg.TBL_phr;
                    StaticData.Title = StaticData.Title_Phr;
                    ((BaseActivity) getActivity()).showFragment(new levelsFragment(DataBaseArg.TBL_phr));
                }
            });

            Button mainBtnConj = activity.findViewById(R.id.mainBtnConj);
            mainBtnConj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StaticData.Category = DataBaseArg.TBL_conj;
                    StaticData.Title = StaticData.Title_Conj;
                    ((BaseActivity) getActivity()).showFragment(new levelsFragment(DataBaseArg.TBL_conj));
                }
            });
            Button mainBtnMButton = activity.findViewById(R.id.mainBtnMust);
            mainBtnMButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StaticData.Category = DataBaseArg.TBL_must;
                    StaticData.Title = StaticData.Title_Must;
                    ((BaseActivity) getActivity()).showFragment(new levelsFragment(DataBaseArg.TBL_must));
                }
            });

            if(ckBtn){//8sıfınsa bazı butunları gizle
                mainBtnZıt.setVisibility(View.INVISIBLE);
                mainBtnEs.setVisibility(View.INVISIBLE);
                mainBtnPhr.setVisibility(View.INVISIBLE);
                mainBtnConj.setVisibility(View.INVISIBLE);
                mainBtnMButton.setVisibility(View.INVISIBLE);
            }

        }




    }


}

package com.thinklearing.mem;

import android.view.View;
import android.widget.Button;

import com.thinklearing.mem.Data.DataBaseArg;
import com.thinklearing.mem.Data.DataBaseHelper;
import com.thinklearing.mem.Data.DataModel;
import com.thinklearing.mem.Data.StaticData;
import com.thinklearing.mem.Data.StsDataModel;

import java.util.List;

public class stsFragment extends BaseFragment {

    DataBaseHelper dbHelper;
    public stsFragment() {
       LayoutId=R.layout.fragment_sts;

    }

    @Override
    protected void initFragment() {
        super.initFragment();
        dbHelper=new DataBaseHelper(getContext());
        activity.setTitle(StaticData.Title);
        Button stsBtnTrkIng= (Button) activity.findViewById(R.id.stsBtnTrkIng);
        stsBtnTrkIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticData.Category = DataBaseArg.TBL_trk_ing;
                List<StsDataModel> data= dbHelper.StsDataGetir(DataBaseArg.ti);
                ((BaseActivity) getActivity()).showFragment(new examFragment(data));
                //activity.showMessage("Buraya Kadar");
                ///((BaseActivity)getActivity()).showFragment(new studyFragment());
            }
        });


        Button stsBtnTrkIngEsAnlam= (Button) activity.findViewById(R.id.stsBtnTrkIngEsAnlam);
        stsBtnTrkIngEsAnlam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticData.Category = DataBaseArg.TBL_syn;
                List<StsDataModel> data= dbHelper.StsDataGetir(DataBaseArg.syn);
                ((BaseActivity) getActivity()).showFragment(new examFragment(data));
            }
        });

        Button stsBtnZit= (Button) activity.findViewById(R.id.stsBtnTrkIngEsAnlam2);
        stsBtnZit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticData.Category = DataBaseArg.TBL_Antoim;
                List<StsDataModel> data= dbHelper.StsDataGetir(DataBaseArg.ant);
                ((BaseActivity) getActivity()).showFragment(new examFragment(data));
                //activity.showMessage("Buraya Kadar");
                ///((BaseActivity)getActivity()).showFragment(new studyFragment());
            }
        });

    }


}

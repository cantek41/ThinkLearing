package com.thinklearing.mem;

import android.view.View;
import android.widget.Button;

import com.thinklearing.mem.Data.DataModel;
import com.thinklearing.mem.Data.StaticData;

import java.util.List;

public class levelFragment extends BaseFragment {

    String tbl;
    List<DataModel> data;


    public levelFragment(String _tbl, int _level) {
        tbl = _tbl;
        StaticData.level = _level;
        LayoutId = R.layout.fragment_level;

    }

    @Override
    protected void initFragment() {
        super.initFragment();

        data = getDataBase().LevelDataGetir(tbl, StaticData.level);

        activity.setTitle(StaticData.Title + " Level " + StaticData.level);
        Button btn = (Button) activity.findViewById(R.id.lvlBtnStudy);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //activity.showMessage("Buraya Kadar");
                ((BaseActivity) getActivity()).showFragment(new studyFragment(data, StaticData.level));
            }
        });


        Button btnMatch = (Button) activity.findViewById(R.id.lvlBtnMatch);
        btnMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //activity.showMessage("Buraya Kadar");
                ((BaseActivity) getActivity()).showFragment(new matchFragment(data));
            }
        });

        Button btnLevel = (Button) activity.findViewById(R.id.mainBtnPhr);
        btnLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //activity.showMessage("Buraya Kadar");
                ((BaseActivity) getActivity()).showFragment(new testFragment(data));
            }
        });
    }


}

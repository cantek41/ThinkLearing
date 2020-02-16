package com.thinklearing.mem;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.thinklearing.mem.Data.DataBaseArg;
import com.thinklearing.mem.Data.DataModel;
import com.thinklearing.mem.Data.StaticData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class testResultFragment extends BaseFragment {
    List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
    private int totalSize;
    private List<DataModel> wrongs;

    public testResultFragment(int totalSize, List<DataModel> wrongs) {
        this.totalSize = totalSize;
        this.wrongs = wrongs;
        LayoutId = R.layout.fragment_test_result;

    }

    @Override
    protected void initFragment() {
        super.initFragment();
        activity.setTitle(StaticData.Title);
        ListView lst = getActivity().findViewById(R.id.ResultList);
        TextView rigthNumber = activity.findViewById(R.id.ResultTxtRightNumber);
        TextView wrongNumber = activity.findViewById(R.id.ResultTxtWrongNumber);
        TextView resultTxt = activity.findViewById(R.id.ResultTxt);

        resultTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity) getActivity()).showFragment(new levelsFragment(StaticData.Category));
            }
        });


        rigthNumber.setText(String.valueOf(totalSize - wrongs.size()));
        wrongNumber.setText(String.valueOf(wrongs.size()));

        double result = totalSize * 0.15;
        if (result > wrongs.size()) {
            resultTxt.setText("Başarılı");
            if(StatusGüncelle()){
                yanlislarıEkle();
                logEkle(totalSize - wrongs.size());
            }

        } else
            resultTxt.setText("Tekrar Dene :(");


        String[] from = {"value1", "value2"};

        int[] to = {R.id.rowTxt1, R.id.rowTxt2};


        for (DataModel model : wrongs)
            data.add(createRow(model.Word, model.Eq));
//        data.add(createRow("Apple", "Elma"));


        SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, R.layout.row_list_result, from, to);


        lst.setAdapter(adapter);

    }

    private void logEkle(int rightNumber) {

        Log.w("yanlislarıEkle", StaticData.Category + " " + rightNumber);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        getDataBase().VeriEkle(
                DataBaseArg.TBL_Log,
                new String[]{DataBaseArg.Date, DataBaseArg.CLASS, DataBaseArg.Number},
                new String[]{String.valueOf(dateFormat.format(date)), StaticData.Category, String.valueOf(rightNumber)}
        );


    }

    private boolean StatusGüncelle() {
        int id = 1;
        int level = getDataBase().getLevelStatus(StaticData.Category);
        Log.w("StatusGüncelle", StaticData.Category + "-" + StaticData.level);
        if (StaticData.level > level) {
            getDataBase().VeriGüncelle(
                    DataBaseArg.TBL_Status,
                    id,
                    new String[]{StaticData.Category},
                    new String[]{String.valueOf(StaticData.level)}

            );
            return true;
        }
        return false;

    }

    private void yanlislarıEkle() {
        int id;
        int level = 0;
        for (DataModel model : wrongs) {
            id = getDataBase().getId(DataBaseArg.TBL_Fire);
            level = 0;
            if (id != -1)
                level = (int) (Math.ceil(id / DataBaseArg.LevelSize));
            Log.w("yanlislarıEkle", id + " " + level);
            getDataBase().VeriEkle(
                    DataBaseArg.TBL_Fire,
                    new String[]{DataBaseArg.Word, DataBaseArg.Eq, DataBaseArg.Level},
                    new String[]{model.Word, model.Eq, String.valueOf(level)}
            );
        }

    }

    private Map<String, ?> createRow(String value1, String value2) {
        Map<String, String> row = new HashMap<String, String>();
        row.put("value1", value1);
        row.put("value2", value2);
        return row;
    }


}

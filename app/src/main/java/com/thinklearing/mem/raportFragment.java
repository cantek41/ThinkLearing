package com.thinklearing.mem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.thinklearing.mem.Data.RaportList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class raportFragment extends BaseFragment {
    List<Map<String, ?>> data;
    ListView lst;

    public raportFragment() {
        LayoutId = R.layout.fragment_raport;
    }

    @Override
    protected void initFragment() {
        super.initFragment();
//        activity.setTitle("Genel Rapor");
        lst = getActivity().findViewById(R.id.raportList);
        setList();


    }

    List<RaportList> listData;

    private void setList() {
        int total = 0;
        listData = getDataBase().RaporGetir();
        data = new ArrayList<>();

        for (RaportList r : listData) {
            data.add(createRow(r.WeekStart + " & " + r.WeekEnd
                    , "Toplam = " + r.Total + " Kelime"));
            total += r.Total;
        }

        TextView totalText = getActivity().findViewById(R.id.reportTxtLearn);
        totalText.setText("Ezberlenen Kelime : " + String.valueOf(total));
//        data.add(createRow("08-15 Ocak 2019", "L3-L4"));

        String[] from = {"value1", "value2"};
        int[] to = {R.id.rowTxt1, R.id.rowTxt2};

        SimpleAdapter adapter = new
                SimpleAdapter(getActivity(), data, R.layout.row_list_result, from, to);

        lst.setAdapter(adapter);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                //((MainActivity)getActivity()).showMessage(data.get(position).toString());
                ((MainActivity) getActivity()).showFragment(new raportWeekFragment(listData.get(position).WeekNumber));
            }
        });

    }

    private Map<String, ?> createRow(String value1, String value2) {
        Map<String, String> row = new HashMap<String, String>();
        row.put("value1", value1);
        row.put("value2", value2);
        return row;
    }


}

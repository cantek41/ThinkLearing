package com.thinklearing.mem;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.thinklearing.mem.Data.DataModel;
import com.thinklearing.mem.Data.StaticData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class matchFragment extends BaseFragment {

    myButton[] buttons = new myButton[8];
    private List<DataModel> data;
    private List<DataModel> tempdata;
    int sperate = 4;
    int pageSize, page;
    int openBox;//açık kutu sayısı dayfayı yenilemek için
    myButton choise;
    int i = 0;
    Random r = new Random();


    public matchFragment(List<DataModel> data) {
        this.data = data;
        LayoutId = R.layout.fragment_match;
    }

    @Override
    protected void initFragment() {
        super.initFragment();
        activity.setTitle(StaticData.Title);
        pageSize = data.size() / sperate;
        page = 0;

        for (i = 0; i < buttons.length; i++) {
            buttons[i] = new myButton();
        }


        buttons[0].btn = activity.findViewById(R.id.testBtnA);
        buttons[1].btn = activity.findViewById(R.id.testBtnB);
        buttons[2].btn = activity.findViewById(R.id.testBtnC);
        buttons[3].btn = activity.findViewById(R.id.testBtnD);
        buttons[4].btn = activity.findViewById(R.id.matchBtn5);
        buttons[5].btn = activity.findViewById(R.id.matchBtn6);
        buttons[6].btn = activity.findViewById(R.id.matchBtn7);
        buttons[7].btn = activity.findViewById(R.id.matchBtn8);

        pageble();


    }

    private void pageble() {
        openBox=0;
        if (data.size() == 0)
            getActivity().onBackPressed();

        Log.w("pageble", "sayfalama");
        Log.w("data.size", data.size() + " ");
        tempdata = new ArrayList<>();
        int row;
        int size = data.size();
        for (i = 0; i < 4 && i < size; i++) {
            row = r.nextInt(data.size());
            tempdata.add(data.get(row));
            data.remove(row);
        }

        for (i = 0; i < buttons.length; i++) {
            buttons[i].btn.setVisibility(View.INVISIBLE);
        }
        setBox();
    }


    private void setBox() {
        Log.w("setBox", tempdata.size() + "");

        List<Integer> _d = new ArrayList<>();
        for (i = 0; i < tempdata.size() * 2; i++) {
            _d.add(i);
        }
        Log.d("_d", i+" ");
        for (i = 0; i < tempdata.size(); i++) {

            int rr = r.nextInt(_d.size());
            int el = _d.get(rr);
            _d.remove(rr);
            rr = r.nextInt(_d.size());
            int el2 = _d.get(rr);
            _d.remove(rr);

            Log.i("el-el2", el + " - " + el2);

            buttons[el].model = tempdata.get(i);
            buttons[el].setWord();
            buttons[el].btn.setVisibility(View.VISIBLE);

            buttons[el2].model = tempdata.get(i);
            buttons[el2].setEq();
            buttons[el2].btn.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].set();
        }
    }

    class myButton {
        public Button btn;
        public DataModel model;

        myButton getThis() {
            return this;
        }

        public void set() {

            this.btn.setAllCaps(false);
            this.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (choise == null) {
                        choise = getThis();
                        choise.btn.setTextColor(Color.RED);

                    } else {
                        if (choise.model.Word.equals(model.Word))
                            right();
                        else
                            worng();
                    }

                }

                private void right() {
                    getThis().btn.setVisibility(View.INVISIBLE);
                    choise.btn.setVisibility(View.INVISIBLE);
                    choise.btn.setTextColor(getResources().getColor(R.color.colorSecond));
                    choise = null;
                    openBox++;
                    if (openBox == tempdata.size())
                        pageble();
                }

                private void worng() {
                    choise.btn.setTextColor(getResources().getColor(R.color.colorSecond));
                    choise = null;
                }
            });
        }


        public void setWord() {
            this.btn.setText(model.Word);
        }

        public void setEq() {
            this.btn.setText(model.Eq);
        }


    }


}

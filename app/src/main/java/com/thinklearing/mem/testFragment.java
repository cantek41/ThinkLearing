package com.thinklearing.mem;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thinklearing.mem.Data.DataModel;
import com.thinklearing.mem.Data.StaticData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class testFragment extends BaseFragment {


    private List<DataModel> data;
    private List<DataModel> temp;
    private List<DataModel> wrongs;
    myButton[] buttons = new myButton[4];
    DataModel choise;
    int i;
    int index;
    boolean globalWrong;

    Random r = new Random();

    TextView textIndex, textWord;


    public testFragment(List<DataModel> data) {
        this.data = data;
        LayoutId = R.layout.fragment_test;

    }

    @Override
    protected void initFragment() {
        super.initFragment();
        activity.setTitle(StaticData.Title);
        wrongs = new ArrayList<>();
        textIndex = activity.findViewById(R.id.ResultTxtRightNumber);
        textWord = activity.findViewById(R.id.ResultTxt);


        for (i = 0; i < buttons.length; i++) {
            buttons[i] = new myButton();
        }

        index = 0;
        buttons[0].btn = activity.findViewById(R.id.testBtnA);
        buttons[1].btn = activity.findViewById(R.id.testBtnB);
        buttons[2].btn = activity.findViewById(R.id.testBtnC);
        buttons[3].btn = activity.findViewById(R.id.testBtnD);
        setTest();
    }

    private void setTest() {
        globalWrong = false;

        if (index >= data.size())
            activity.showFragment(new testResultFragment(data.size(), wrongs));
        else {
            settingsChoise();
        }


    }

    void settingsChoise() {
        Log.i("setTest - data.size()", data.size() + " ");
        Log.i("setTest - index", index + " ");

        temp = new ArrayList<>();
        temp.addAll(data);
        choise = temp.get(index);
        temp.remove(index);

        textIndex.setText(index + " ");
        textWord.setText(choise.Word);


        List<DataModel> coo = new ArrayList<>();
        coo.add(choise);
        for (i = 0; i < 3 && i < temp.size(); i++) {
            int rr = r.nextInt(temp.size());
            coo.add(temp.get(rr));
            temp.remove(rr);
        }

        Collections.shuffle(coo);
        for (i = 0; i < coo.size(); i++) {
            buttons[i].model = coo.get(i);
            buttons[i].setEq();
            buttons[i].btn.setTextColor(getResources().getColor(R.color.colorSecond));
            buttons[i].set();
        }

        index++;
    }

    class myButton {
        public Button btn;
        public DataModel model;


        public void set() {
            this.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (choise.Word.equals(model.Word))
                        right();
                    else
                        worng();
                }

                private void right() {
                    setTest();
                }

                private void worng() {
                    btn.setTextColor(Color.RED);
                    if (!globalWrong) {
                        globalWrong = true;
                        wrongs.add(choise);
                    }
                }
            });
        }


        public void setWord() {
            this.btn.setText(model.Word);
        }

        public void setEq() {
            Log.i("Test",model.Eq);
            this.btn.setAllCaps(false);
            this.btn.setText(model.Eq);
        }


    }


}

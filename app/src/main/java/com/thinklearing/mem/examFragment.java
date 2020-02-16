package com.thinklearing.mem;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thinklearing.mem.Data.DataBaseArg;
import com.thinklearing.mem.Data.DataBaseHelper;
import com.thinklearing.mem.Data.DataModel;
import com.thinklearing.mem.Data.StaticData;
import com.thinklearing.mem.Data.StsDataModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class examFragment extends BaseFragment {


    private List<StsDataModel> data;
    private List<StsDataModel> temp;
    private List<StsDataModel> wrongs;
    myButton[] buttons = new myButton[4];
    StsDataModel choise;
    int i;
    int index;
    boolean globalWrong;

    Random r = new Random();

    TextView textIndex, textWord;

    public examFragment(List<StsDataModel> data) {
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
        if (index >= data.size()) {
            int rigthAnswerCount=data.size()-wrongs.size();
            int puan= Integer.valueOf(100/data.size())* rigthAnswerCount;
            DataBaseHelper dbHelper=new DataBaseHelper(getContext());
            dbHelper.VeriEkle(
                    DataBaseArg.TBL_sts,
                    new String[]{choise.typ},
                    new String[]{String.valueOf(puan)});

            //TODO: level kayıt
            int id=1;
            int level=0;
            if(rigthAnswerCount<20){
                level=0;
            }else  if(rigthAnswerCount<25){
                level=10;
            }else if(rigthAnswerCount<30){
                level=30;
            }else if(rigthAnswerCount<35){
                level=50;
            }else if(rigthAnswerCount<45){
                level=70;
            }else {
                level=80;
            }

            if(StaticData.Category==DataBaseArg.TBL_trk_ing){
            getDataBase().VeriGüncelle(
                    DataBaseArg.TBL_Status,
                    id,
                    new String[]{DataBaseArg.TBL_ing_trk},
                    new String[]{String.valueOf(level)}
            );
                getDataBase().VeriGüncelle(
                        DataBaseArg.TBL_Status,
                        id,
                        new String[]{DataBaseArg.TBL_trk_ing},
                        new String[]{String.valueOf(level)}
                );
            } else
                getDataBase().VeriGüncelle(
                        DataBaseArg.TBL_Status,
                        id,
                        new String[]{StaticData.Category},
                        new String[]{String.valueOf(level)}
                );

            getActivity().onBackPressed();
//            activity.showFragment(new testResultFragment(data.size(), wrongs));
        }
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


        buttons[0].txt = choise.chA;
        buttons[0].setEq();
        buttons[0].btn.setTextColor(getResources().getColor(R.color.colorSecond));
        buttons[0].set();

        buttons[1].txt = choise.chB;
        buttons[1].setEq();
        buttons[1].btn.setTextColor(getResources().getColor(R.color.colorSecond));
        buttons[1].set();


        buttons[2].txt = choise.chC;
        buttons[2].setEq();
        buttons[2].btn.setTextColor(getResources().getColor(R.color.colorSecond));
        buttons[2].set();

        buttons[3].txt = choise.chD;
        buttons[3].setEq();
        buttons[3].btn.setTextColor(getResources().getColor(R.color.colorSecond));
        buttons[3].set();

        index++;

//        List<StsDataModel> coo = new ArrayList<>();
//        coo.add(choise);
//        for (i = 0; i < 3 && i < temp.size(); i++) {
//            int rr = r.nextInt(temp.size());
//            coo.add(temp.get(rr));
//            temp.remove(rr);
//        }
//
//        Collections.shuffle(coo);
//        for (i = 0; i < coo.size(); i++) {
//            buttons[i].model = coo.get(i);
//            buttons[i].setEq();
//            buttons[i].btn.setTextColor(getResources().getColor(R.color.colorSecond));
//            buttons[i].set();
//        }

    }

    class myButton {
        public Button btn;
        public String txt;
        public void set() {
            this.btn.setAllCaps(false);
            this.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("myButton",choise.answer+"/"+txt);
                    if (choise.answer.equals(txt))
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
            this.btn.setText(txt);
        }

        public void setEq() {
            this.btn.setText(txt);
        }


    }


}

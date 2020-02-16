package com.thinklearing.mem;

import android.util.Log;
import android.view.Gravity;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinklearing.mem.Data.DataBase;
import com.thinklearing.mem.Data.StaticData;

import java.util.List;

public class levelsFragment extends BaseFragment {

    int levelSize = 0;
    //TODO: hangi levaılda olduğunu çek
    int levelState = 0;

    LinearLayout mainLayout;
    List<Integer> levels;
    String tbl;


    public levelsFragment(String _tbl) {
        tbl = _tbl;
        LayoutId = R.layout.fragment_levels;
    }

    @Override
    protected void initFragment() {
        super.initFragment();
        activity.setTitle(StaticData.Title);
        mainLayout = activity.findViewById(R.id.lvl_main_layout);
//        DataBase db_helper = new DataBase(getContext());
        levels = getDataBase().LevelGetir(tbl);
//        db_helper.LevelGetir(tbl);
        levelSize = levels.size();
        levelState = getDataBase().getLevelStatus(StaticData.Category);
        prepareLayout();
    }

    private void prepareLayout() {

        int rowSize = (int) (Math.ceil(levelSize / 4));
        if (rowSize == 0)
            rowSize++;
        int index = 0;


        LinearLayout layout_row;

        LinearLayout.LayoutParams paramsRow = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, 210, 1);

        LinearLayout layout_col;
        LinearLayout.LayoutParams paramsCol = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.2f);
        paramsCol.setMargins(10, 10, 10, 10);


        ImageView star;
        LinearLayout.LayoutParams paramsImg = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.2f);

        TextView textView;
        LinearLayout.LayoutParams paramsText = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.8f);


        for (int row = 0; row < rowSize; row++) {
            layout_row = new LinearLayout(getActivity());
            layout_row.setOrientation(LinearLayout.HORIZONTAL);
            layout_row.setLayoutParams(paramsRow);

            for (int col = 0; col < 4 && index < levelSize; col++) {

                layout_col = new LinearLayout(getActivity());
                layout_col.setOrientation(LinearLayout.VERTICAL);
                layout_col.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
                layout_col.setLayoutParams(paramsCol);

                final int finalIndex = index;

                if (levels.get(index) <= levelState + 1) {
                    Log.d("levelState-index", levelState + " " + levels.get(index) + " - " + index);
                    layout_col.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((BaseActivity) getActivity()).showFragment(new levelFragment(tbl, levels.get(finalIndex)));
                        }
                    });
                }

                star = new ImageView(getActivity());
                star.setScaleType(ImageView.ScaleType.FIT_CENTER);

                if (levelState >= levels.get(index))
                    star.setImageResource(R.drawable.level_star_complate);
                else
                    star.setImageResource(R.drawable.level_star_uncomplate);

                star.setLayoutParams(paramsImg);

                textView = new TextView(getActivity());
                textView.setText("Level " + levels.get(index));
                textView.setLayoutParams(paramsText);

                layout_col.addView(star);
                layout_col.addView(textView);
                layout_row.addView(layout_col);
                index++;
            }
            mainLayout.addView(layout_row);
        }

    }
}

package com.thinklearing.mem;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.thinklearing.mem.Data.RaportWeek;

import java.util.ArrayList;
import java.util.List;

public class raportWeekFragment extends BaseFragment {

    private int weekNumber;

    public raportWeekFragment(int weekNumber) {
        this.weekNumber = weekNumber;
        LayoutId = R.layout.fragment_raport_week;

    }

    @Override
    protected void initFragment() {
        super.initFragment();
        activity.setTitle("Haftalık Rapor");


        AnyChartView anyChartView = getActivity().findViewById(R.id.any_chart_view);

        List<RaportWeek> listData = getDataBase().HaftayaGoreRaporGetir(weekNumber);


        List<DataEntry> data = new ArrayList<>();
        for (RaportWeek r : listData) {
            data.add(new ValueDataEntry(r.servdayofweek, r.Total));
        }
//            data.add(new ValueDataEntry("Sal", 15));
//            data.add(new ValueDataEntry("Çrş", 35));
//            data.add(new ValueDataEntry("Prş", 5));
//            data.add(new ValueDataEntry("Cum", 25));
//            data.add(new ValueDataEntry("Cmt", 50));
//            data.add(new ValueDataEntry("Pzr", 30));


        Cartesian cartesian = AnyChart.column();
        Column column = cartesian.column(data);

        column.labels(true);
        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: }");

        cartesian.animation(true);
//        cartesian.title("Top 10 Cosmetic Products by Revenue");

        cartesian.yScale().minimum(0d);

//        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Günler");
        cartesian.yAxis(0).title("Kelimeler");

        anyChartView.setChart(cartesian);


    }


}

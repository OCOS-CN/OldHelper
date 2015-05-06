package com.qingluan.darkh.oldhelper.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.LineChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.TimerTask;

/**
 * Created by darkh on 5/1/15.
 */
public class CharterTools {

    private TimerTask task;
    private Handler handler;
    private String title = "Signal Strength";
    private XYSeries series;
    private XYMultipleSeriesDataset mDataset;
    private GraphicalView chart;
    private XYSeriesRenderer renderer;
    private XYMultipleSeriesRenderer renderers;
    private Context context;

    private String[] mMonth = new String[] {
            "Jan", "Feb" , "Mar", "Apr", "May", "Jun",
            "Jul", "Aug" , "Sep", "Oct", "Nov", "Dec"
    };

    public  CharterTools(Context context){


        this.context = context;


        mDataset = new XYMultipleSeriesDataset();
        renderers = new XYMultipleSeriesRenderer();
        renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.RED);
        renderer.setDisplayBoundingPoints(true);

        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setPointStrokeWidth(3);


        renderers.addSeriesRenderer(renderer);

        renderers.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
//        renderers.setPanEnabled(false,false);
//        renderers.setYAxisMax(35);
        renderers.setYAxisMin(0);
        renderers.setLegendTextSize(30);
        renderers.setLabelsTextSize(25);
        renderers.setShowGrid(true);



    }

    public Intent loadData(){
        series = new XYSeries("Data weekly");
        int[] x = { 0,1,2,3,4,5,6,7 };
        int[] income = { 20,25,27,30,28,35,37,38};
        int[] expense = {2200, 2700, 2900, 2800, 2600, 3000, 3300, 3400 };

        for (int i =0 ;i <income.length ; i++){
            series.add(i,income[i]);

        }
        mDataset.addSeries(series);

        Intent intent = ChartFactory.getBarChartIntent(context, mDataset, renderers, BarChart.Type.DEFAULT);//BarChart.Type.DEFAULT);
        return intent;
    }


    public GraphicalView loadDataView(){
        series = new XYSeries("Data weekly");
        int[] x = { 0,1,2,3,4,5,6,7 };
        int[] income = { 2000,2500,2700,3000,2800,3500,3700,3800};
        int[] expense = {2200, 2700, 2900, 2800, 2600, 3000, 3300, 3400 };

        for (int i =0 ;i <income.length ; i++){
            series.add(i,income[i]);

        }
        mDataset.addSeries(series);

        GraphicalView chartView = ChartFactory.getLineChartView(context, mDataset, renderers);
        return chartView;
    }
}

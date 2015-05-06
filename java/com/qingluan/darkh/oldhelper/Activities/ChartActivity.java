package com.qingluan.darkh.oldhelper.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.qingluan.darkh.oldhelper.R;
import com.qingluan.darkh.oldhelper.util.CharterTools;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;

public class ChartActivity extends Activity {
    private GraphicalView mChartView;
    private Context context;

    private LinearLayout chart_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        context = this;
        chart_layout = (LinearLayout)findViewById(R.id.chart_ll);

        openChart();


    }


    public void openChart(){
        CharterTools charterTools = new CharterTools(context);

//        Intent i = charterTools.loadData();
//        startActivity(i);
        mChartView = charterTools.loadDataView();

        chart_layout.removeAllViews();
        chart_layout.addView(mChartView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.qingluan.darkh.oldhelper.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.qingluan.darkh.oldhelper.R;
import com.qingluan.darkh.oldhelper.util.ProgressDialogTool;
import com.qingluan.darkh.oldhelper.widgets.HealthValueListVIewAdapter;

public class HealthInfoActivity extends Activity {
    ListView health_info_ll;

    HealthValueListVIewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_info);

        ProgressDialogTool.dis();

        this.init_ui();
    }

    private  void  init_ui(){
        health_info_ll = (ListView)findViewById(R.id.health_info_list);
        adapter = new HealthValueListVIewAdapter(HealthInfoActivity.this);
        health_info_ll.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_health_info, menu);
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

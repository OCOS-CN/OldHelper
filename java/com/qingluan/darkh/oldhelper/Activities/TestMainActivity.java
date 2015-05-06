package com.qingluan.darkh.oldhelper.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.qingluan.darkh.oldhelper.R;
import com.qingluan.darkh.oldhelper.components.MoudleView;

public class TestMainActivity extends Activity {
    MoudleView moudleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);
        moudleView = (MoudleView)findViewById(R.id.test_cc);
        moudleView.setOnEventListener(new MoudleView.onEventListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"test ok !",Toast.LENGTH_SHORT).show();
            }
        });

//        moudleView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_main, menu);
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

    public void clickEvent(View v){
        Toast.makeText(getApplicationContext(),"test ok !",Toast.LENGTH_SHORT).show();
    }
}

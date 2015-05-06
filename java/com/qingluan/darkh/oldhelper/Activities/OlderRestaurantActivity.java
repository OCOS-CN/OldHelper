package com.qingluan.darkh.oldhelper.Activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.qingluan.darkh.oldhelper.R;
import com.qingluan.darkh.oldhelper.widgets.RestaurantListViewAdapter;

public class OlderRestaurantActivity extends Activity {

    ListView restaurant_ll;
    RestaurantListViewAdapter adapter;
    String name_id =null;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_older_restaurant);
        if (getIntent().getExtras().containsKey("name_id")) {
            name_id = getIntent().getExtras().getString("name_id");
        }
        context = this;


        restaurant_ll = (ListView)findViewById(R.id.old_restaurant_lv);

        adapter = new RestaurantListViewAdapter(context);
        restaurant_ll.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_older_restaurant, menu);
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

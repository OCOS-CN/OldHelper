package com.qingluan.darkh.oldhelper.widgets;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qingluan.darkh.oldhelper.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by darkh on 4/27/15.
 */
public class DateListviewAdapter extends BaseAdapter {

    Map<String,Integer> data = new HashMap<String, Integer>();
    List<String> data_array = new ArrayList<String>();
    ArrayList<HashMap<String,String>> date;
    public static int RESERVED = 1;
    public static int RESERVE = 0;

    final Context external_context;
    TextView date_data_tv;
    String [] keys;
    onAlertListViewClickListener listener ;

    public DateListviewAdapter (Context context ,ArrayList<HashMap<String,String>> date){
        external_context = context;
        this.date = date;
        int count = 0;
        for(HashMap<String,String> one : this.date){
            data_array.add(count,one.get("date"));
        }
        keys = data_array.toArray(new String[]{});
    }



    public void setOnAlertListViewClickListener(onAlertListViewClickListener onAlertListViewClickListener){
        this.listener = onAlertListViewClickListener;
    }


    public Integer getData(String key){
        return  data.get(key);
    }

    @Override
    public int getCount() {
        return data_array.size();
    }

    @Override
    public String getItem(int position) {
        return keys[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater) external_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.widget_date_list_each,null);

        date_data_tv = (TextView) convertView.findViewById(R.id.date_each_tv);

        date_data_tv.setText(getItem(position));

        return convertView;
    }


    public interface onAlertListViewClickListener{
        public void afterClick(String [] exclusionKeys);
    }
}

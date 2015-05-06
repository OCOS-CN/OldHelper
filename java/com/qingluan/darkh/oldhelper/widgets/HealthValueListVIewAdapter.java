package com.qingluan.darkh.oldhelper.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qingluan.darkh.oldhelper.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by darkh on 4/20/15.
 */
public class HealthValueListVIewAdapter extends BaseAdapter {
    Map<String,Integer> data = new HashMap<String, Integer>();
    List<String> data_array = new ArrayList<String>();

    Context external_context;
    TextView value_type_tv;
    TextView value_tv;
    ProgressBar value_bar;

    String [] keys;
    onAlertListViewClickListener listener ;
    public HealthValueListVIewAdapter (Context context){
        external_context = context;

        data.put("心率",34);
        data.put("血压",39);
        data.put("身高",59);
        data.put("体重",79);
        data.put("血糖",12);
        data.put("血脂",80);
        data.put("红细胞",12);

        keys = data.keySet().toArray(new String[]{});
    }


    public void setOnAlertListViewClickListener(onAlertListViewClickListener onAlertListViewClickListener){
        this.listener = onAlertListViewClickListener;
    }


    public Integer getData(String key){
        return  data.get(key);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Integer getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater) external_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.widget_health_info_ll,null);

        value_tv = (TextView) convertView.findViewById(R.id.health_value_tv);
        value_bar = (ProgressBar) convertView.findViewById(R.id.health_value_pb);
        value_type_tv = (TextView) convertView.findViewById(R.id.health_value_type_tv);

        value_bar.setProgress(data.get(keys[position]));
        value_tv.setText(String.valueOf(data.get(keys[position])));
        value_type_tv.setText(keys[position]);
        value_type_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(external_context, keys[position], Toast.LENGTH_SHORT).show();
                /*
                    call phone

                 */



            }
        });
        return convertView;
    }

    public interface onAlertListViewClickListener{
        public void afterClick(String [] exclusionKeys);
    }
}

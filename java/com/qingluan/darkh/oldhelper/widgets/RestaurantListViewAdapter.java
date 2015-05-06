package com.qingluan.darkh.oldhelper.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qingluan.darkh.oldhelper.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by darkh on 4/24/15.
 */
public class RestaurantListViewAdapter extends BaseAdapter {
    Map<String,Integer> data = new HashMap<String, Integer>();
    List<String> data_array = new ArrayList<String>();
    public static int RESERVED = 1;
    public static int RESERVE = 0;

    final Context external_context;
    TextView restaurant_naem;
    Button reserver_bt;
    String [] keys;
    onAlertListViewClickListener listener ;
    public RestaurantListViewAdapter (Context context){
        external_context = context;
        data.put("小区餐厅",RESERVE);
        data.put("清真餐厅",RESERVE);
        data.put("重庆留一手",0);
        data.put("毛血旺",0);
        data.put("必胜客餐厅",0);
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
        convertView = inflater.inflate(R.layout.widget_restaurant_ll,null);

        restaurant_naem = (TextView) convertView.findViewById(R.id.name_restaurant_tv);
        reserver_bt = (Button) convertView.findViewById(R.id.reserver_bt);
        restaurant_naem.setText(keys[position]);
        reserver_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(external_context, keys[position], Toast.LENGTH_SHORT).show();
                /*
                    call phone

                 */
                if (data.get(keys[position]) == RESERVE) {
                    reserver_bt.setBackgroundResource(R.drawable.shape_corner_green);//(external_context.getResources().getDrawable(R.drawable.shape_corner_green));
                    reserver_bt.setText(R.string.reserver_seated);
                    data.put(keys[position],RESERVED);
                    notifyDataSetChanged();
                } else if (data.get(keys[position]) == RESERVED ){
                    reserver_bt.setBackgroundResource(R.drawable.shape_corner_black);//(external_context.getResources().getDrawable(R.drawable.shape_corner_green));
                    reserver_bt.setText(R.string.reserver_seat);
                    data.put(keys[position],RESERVED);
                    notifyDataSetChanged();
                }


            }
        });
        return convertView;
    }

    public interface onAlertListViewClickListener{
        public void afterClick(String [] exclusionKeys);
    }
}

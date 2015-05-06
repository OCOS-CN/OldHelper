package com.qingluan.darkh.oldhelper.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.qingluan.darkh.oldhelper.Activities.ItemDetailFragment;
import com.qingluan.darkh.oldhelper.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by darkh on 4/19/15.
 */
public class PersonInfoListViewAdapter extends BaseAdapter {
    Map<String,Integer> data = new HashMap<String, Integer>();
    List<String> data_array = new ArrayList<String>();

    Context external_context;
    TextView tv_item_resolution;

    String [] keys;
    onAlertListViewClickListener listener ;
    public PersonInfoListViewAdapter (Context context){
        external_context = context;
        data.put("李刘章",3);
        data.put("汉",5);
        data.put("67岁",1);
        data.put("慧家小区",2);
        data.put("xxx街道xx",7);
        data.put("无病史",8);
        data.put("血压：100kps",4);
        data.put("血糖：0.02 g/ml",6);

//        keys = data.keySet().toArray(new String[]{});
        data_array.add(0,"李刘章");
        data_array.add(1,"汉");
        data_array.add(2,"67岁");
        data_array.add(3,"慧家小区");
        data_array.add(4,"血压：100kps");
        data_array.add(5,"血糖：0.02 g/ml");
        data_array.add(6,"xxx街道xxx");
        data_array.add(7,"无病史");
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
        convertView = inflater.inflate(R.layout.widget_person_info_item,null);

        tv_item_resolution = (TextView) convertView.findViewById(R.id.person_info_item_tv);
        tv_item_resolution.setText(keys[position]);
        tv_item_resolution.setOnClickListener(new View.OnClickListener() {
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

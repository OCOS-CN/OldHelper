package com.qingluan.darkh.oldhelper.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qingluan.darkh.oldhelper.R;
import com.qingluan.darkh.oldhelper.arguments.ARGUMENTS;
import com.qingluan.darkh.oldhelper.util.FileTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by darkh on 4/21/15.
 */
public class FamilyChooseListViewAdapter extends BaseAdapter {
    Map<String,Integer> data = new HashMap<String, Integer>();
    List<String> data_array = new ArrayList<String>();

    Context external_context;
    TextView value_name_tv;
    ImageView name_image_iv;

    String [] keys;
    onAlertListViewClickListener listener ;
    public FamilyChooseListViewAdapter (Context context){
        external_context = context;

        data.put("李四",39);
        data.put("张三",34);

        data.put("张0",34);
        data.put("张3",34);
        data.put("张1",34);
        data.put("张4",34);
        data.put("张2",34);


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

    public String getKey(int position){
        return keys[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater) external_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.widget_item_each_ll,null);

        value_name_tv = (TextView)convertView.findViewById(R.id.family_name_tv);
        name_image_iv = (ImageView)convertView.findViewById(R.id.family_name_iv);


        value_name_tv.setText(this.getKey(position));

        name_image_iv.setImageBitmap(FileTools.getImageBitmapByName(external_context, ARGUMENTS.FAMILY_IMG_ASSEST_DIRECTORY,"oldwoman.png"));


        return convertView;
    }

    public interface onAlertListViewClickListener{
        public void afterClick(String [] exclusionKeys);
    }
}

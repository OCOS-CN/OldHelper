package com.qingluan.darkh.oldhelper.widgets;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qingluan.darkh.oldhelper.R;
import com.qingluan.darkh.oldhelper.util.Kits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by darkh on 4/28/15.
 */
public class TListviewAdapter extends BaseAdapter {

    Map<?,?> data ;//= new HashMap<String, String>();
    Vector<?> data_array ;

    Context external_context;
    TextView label;
    String [] keys;
    View.OnClickListener listener ;
    public TListviewAdapter (Context context,HashMap<String, String> data){
        external_context = context;
        this.data  = data;
        keys = data.keySet().toArray(new String[]{});
    }

    public TListviewAdapter (Context context,Vector<?> data){
        external_context = context;
        this.data_array = data;
        keys = data.toArray(new String[]{});
    }

    public void setEachClickListener(View.OnClickListener listener){
        this.listener = listener;
    }


    public String getData(String key){
        return  (String)data.get(key);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return (String)data.get(keys[position]);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater) external_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.widget_t,null);


        label = (TextView)convertView.findViewById(R.id.label_tv);
        label.setText(getItem(position));
        if (this.listener != null){
            label.setOnClickListener(this.listener);
        }


        return convertView;
    }




}

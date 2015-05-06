package com.qingluan.darkh.oldhelper.widgets;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qingluan.darkh.oldhelper.R;
import com.qingluan.darkh.oldhelper.util.ToastShow;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by darkh on 4/28/15.
 */
public class DishesGridViewAdapter extends BaseAdapter {
    HashMap<String,String> data ;//= new HashMap<String, Integer>();
    List<String> data_array ;//= new ArrayList<String>();
    public static int RESERVED = 1;
    public static int RESERVE = 0;

    final Context external_context;
    TextView dish_tv;
    Button dish_bt;
    ImageView dish_reserve_iv;
    String [] keys;
    public DishesGridViewAdapter (Context context ,HashMap<String,String> data){
        external_context = context;
        this.data = data;
        keys = this.data.keySet().toArray(new String[]{});
        assetManager = context.getAssets();
    }




    public String getData(String key){
        return  data.get(key);
    }


    private AssetManager assetManager;

    public Drawable getImageByName (String directoryName,String name ){
        Drawable da = null;

        try {
            InputStream is = assetManager.open(directoryName + "/"+name);
            da = Drawable.createFromStream(is,null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  da;
    }

    public Bitmap getImageBitmapByName (String directoryName,String name ){
        Bitmap da = null;

        try {
            InputStream is = assetManager.open(directoryName + "/"+name);
            da = BitmapFactory.decodeStream(is);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  da;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return keys[position];
    }

    public String getData(int position){
        return  data.get(keys[position]);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater) external_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.widget_each_dish,null);

        dish_tv = (TextView) convertView.findViewById(R.id.dish_name_tv);
        dish_reserve_iv = (ImageView) convertView.findViewById(R.id.dish_iv);
        dish_bt = (Button)convertView.findViewById(R.id.dish_reserve_bt);

        String img_name = getData(position);
        dish_reserve_iv.setImageDrawable(getImageByName("dishes",img_name));
        dish_tv.setText(getItem(position));
        dish_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(external_context,"order", Toast.LENGTH_SHORT).show();

            }
        });

        return convertView;
    }

}

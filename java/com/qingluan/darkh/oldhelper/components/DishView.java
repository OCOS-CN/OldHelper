package com.qingluan.darkh.oldhelper.components;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qingluan.darkh.oldhelper.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by darkh on 4/28/15.
 */
public class DishView extends LinearLayout {
    private  Context context;
    HashMap<String,String> data;
    TextView dish_tv;
    Button dish_bt;
    ImageView dish_reserve_iv;
    TextView line;
    LinearLayout.LayoutParams params;
    private AssetManager assetManager;
    public DishView(Context context,String dish_name,String img_name) {
        super(context);
        this.context = context;
        assetManager = context.getAssets();

        line = new TextView(context);

        dish_tv = new TextView(context);
        dish_tv.setText(R.string.dish_name);

        dish_reserve_iv = new ImageView(context);

        dish_bt = new Button(context);
        dish_bt.setText(R.string.reserver_seat);
//        dish_tv = (TextView) this.findViewById(R.id.dish_name_tv);
//        dish_reserve_iv = (ImageView) this.findViewById(R.id.dish_iv);
//        dish_bt = (Button)this.findViewById(R.id.dish_reserve_bt);
        this.setParms();
        loadSubViews();
        this.setContent(dish_name,img_name);

    }

    public  void  setParms(){
//        params = (LayoutParams) this.getLayoutParams();
        LayoutParams params = new LayoutParams(300,500);

        params.setMargins(R.dimen.activity_vertical_margin,
                R.dimen.activity_vertical_margin,
                R.dimen.activity_vertical_margin,
                R.dimen.activity_vertical_margin);
        this.setOrientation(LinearLayout.VERTICAL);
        this.setLayoutParams(params);
        this.setBackgroundResource(R.drawable.shape_corner);


        line.setBackgroundColor(getResources().getColor(R.color.gray));
        line.setWidth(params.MATCH_PARENT);
        line.setHeight(2);

        LayoutParams params_iv = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,6);
        params_iv.setMargins(20,10,20,0);
        params_iv.gravity = Gravity.CENTER;
        dish_reserve_iv.setLayoutParams(params_iv);

        LayoutParams params_tv = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,2);
        params_tv.setMargins(20,10,20,10);
        params_tv.gravity = Gravity.CENTER;
        dish_tv.setLayoutParams(params_tv);
        dish_tv.setBackgroundResource(R.drawable.shape_corner_squary);
        dish_tv.setHint(R.string.dish_name);
        dish_tv.setGravity(Gravity.CENTER);

        LayoutParams params_bt = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,2);
        params_bt.setMargins(0,0,0,10);
        params_bt.gravity = Gravity.CENTER;
        dish_bt.setBackgroundResource(R.drawable.shape_corner_black);
        dish_bt.setLayoutParams(params_bt);
        dish_bt.setTextColor(getResources().getColor(R.color.white));






    }

    private   void loadSubViews(){
        this.addView(line,0);
        this.addView(dish_reserve_iv,1);
        this.addView(dish_tv,2);
        this.addView(dish_bt,3);
    }


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


    public  void  setContent(String dish_name , String img_name){
        dish_reserve_iv.setImageDrawable(getImageByName("dishes",img_name));
        dish_tv.setText(dish_name);
        dish_bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "order", Toast.LENGTH_SHORT).show();

            }
        });
    }


}

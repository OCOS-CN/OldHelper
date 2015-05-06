package com.qingluan.darkh.oldhelper.Activities;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qingluan.darkh.oldhelper.R;
import com.qingluan.darkh.oldhelper.components.DishView;
import com.qingluan.darkh.oldhelper.widgets.DishesGridViewAdapter;

public class OldRestaurantTabActivity extends Activity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    Context context;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    private String name_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_restaurant);
        if (getIntent().getExtras().containsKey("name_id")) {
            name_id = getIntent().getExtras().getString("name_id");
        }
        context = OldRestaurantTabActivity.this;


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_old_restaurant_tab, menu);
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


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        HashMap<String,String> dishes_data;
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            dishes_data  = new HashMap<String, String>();

            dishes_data.put("茶叶蛋","chayedan.png");
            dishes_data.put("醋椒黑木耳","cujiaoheimuer.png");
            dishes_data.put("藕条炒木耳","outiaochaomuer.png");
            dishes_data.put("山药羊肉煲","shanyaoyangroubao.png");
            dishes_data.put("双菇羊肉煲","shuangguyangroubao.png");
            dishes_data.put("双色蛋卷","shuangsedanjuan.png");
            dishes_data.put("虾仁蒸豆腐","xiarenzhengdoufu.png");
            dishes_data.put("羊肉蒸粉条","yagnrouzhengfentiao.png");
            dishes_data.put("玉米之乡","yumizhixiang.png");
            dishes_data.put("胡萝卜炖牛肉","huluobudunniurou.png");

            setTitle(getPageTitle(position));
            return PlaceholderFragment.newInstance(position + 1,context,dishes_data);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "菜单1";
                case 1:
                    return "菜单2";
                case 2:
                    return "菜单3";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        GridLayout gridLayout ;


        DishesGridViewAdapter adapter;
        Context context;
        HashMap<String,String> data;
        TextView dish_tv;
        Button dish_bt;
        ImageView dish_reserve_iv;
        private AssetManager assetManager;
        String keys [];
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber,Context context,HashMap<String,String> dishes_data) {

            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);

            fragment.setContext(context);
            fragment.setData(dishes_data);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {

        }

        public void  setContext (Context context){
            this.context = context;
            assetManager = context.getAssets();
        }

        public void  setData(HashMap<String,String>data){
            this.data = data;
            keys = this.data.keySet().toArray(new String[]{});
        }

        public void  setData (String ...data){
            String key = null;
            String val =null;
            this.data = new HashMap<String,String>();
            try{
                if (data.length % 2  != 0) {
                    throw new Exception("must be even arguments");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            for (int i = 0  ; i< data.length;i++){
                if (i %2 == 0){
                    key = data[i];
                }else {
                    val = data[i];
                    this.data.put(key,val);
                }
            }

            keys = this.data.keySet().toArray(new String[]{});
        }

        public String getData(String key){
            return  data.get(key);
        }

        public String getData(int position){
            return  data.get(keys[position]);
        }

        public String getItem(int position) {
            return keys[position];
        }

//        public Drawable getImageByName (String directoryName,String name ){
//            Drawable da = null;
//
//            try {
//                InputStream is = assetManager.open(directoryName + "/"+name);
//                da = Drawable.createFromStream(is,null);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return  da;
//        }
//
//        public Bitmap getImageBitmapByName (String directoryName,String name ){
//            Bitmap da = null;
//
//            try {
//                InputStream is = assetManager.open(directoryName + "/"+name);
//                da = BitmapFactory.decodeStream(is);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return  da;
//        }
//

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            adapter = new DishesGridViewAdapter(context,this.data);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.widget_each_dishes, container, false);

            gridLayout = (GridLayout)rootView.findViewById(R.id.grid_layout);

//            View element_view = inflater.inflate(R.layout.widget_each_dish,null);



            gridLayout.removeAllViews();


            Log.d("some",String.valueOf(gridLayout.getChildCount()));

            for(int i =0 ; i < keys.length ; i++){
                View subView = getElementView(i);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 300;
                params.height = 500;
                params.setMargins(60,30,60,30);
                gridLayout.addView(subView,params);

            }







            return rootView;
        }
        public  View getElementView(int position){
            DishView dishView = new DishView(context,getItem(position),getData(position));

            return  dishView;

        }
    }






}

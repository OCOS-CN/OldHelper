package com.qingluan.darkh.oldhelper.Activities;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.qingluan.darkh.oldhelper.R;
import com.qingluan.darkh.oldhelper.util.ProgressDialogTool;
import com.qingluan.darkh.oldhelper.widgets.DateListviewAdapter;
import com.qingluan.darkh.oldhelper.widgets.HealthValueListVIewAdapter;

public class HistoryInfo extends Activity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    private  float DownloadPress ;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    Button historical_bt;
    ArrayList<HashMap<String,String>> history_info = new ArrayList<HashMap<String, String>>();
    Context context;
    String name_id = null;
    DateListviewAdapter dateListviewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_info);
        if (getIntent().getExtras().containsKey("name_id")) {
            name_id = getIntent().getExtras().getString("name_id");
        }
        context = HistoryInfo.this;



        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        data_load();
        mViewPager.setAdapter(mSectionsPagerAdapter);
        dateListviewAdapter  = new DateListviewAdapter(context,history_info);

        historical_bt = (Button ) findViewById(R.id.historical_ll_bt);
        historical_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.widget_date_ll,null);
                ListView date_lv = (ListView) view.findViewById(R.id.date_lv);
                date_lv.setAdapter(dateListviewAdapter);
                final AlertDialog dialog = new AlertDialog.Builder(context)
                        .setView(view)
                        .show();

                date_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mViewPager.setCurrentItem(position);
                        dialog.dismiss();
                    }
                });




            }
        });



    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(HistoryInfo.class.getName(),String.valueOf(event.getY()));
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            DownloadPress = event.getY();
        }else if (event.getAction() == MotionEvent.ACTION_UP){
            if (event.getY() - DownloadPress > 200){
//                DownloadPress = null;

                return true;

            }
        }

        return false;


    }

    public HashMap<?,?> addData(String ...data){
        HashMap<String,String> newData= new HashMap<String, String>();
        String key = null;
        String val = null;
        try{
            if (data.length % 2 != 0) {
                throw new Exception("len is not even");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        for (int i =0 ; i < data.length;i ++){
            if (i % 2 == 0){
                key = data[i];
            }else{
                val = data[i];
                newData.put(key,val);
            }
        }

        return  newData;
    }

    public void data_load(){
        HashMap<String,String> data_1 = (HashMap<String,String>)this.addData("date","2015 2/25");
        HashMap<String,String> data_2 = (HashMap<String,String>)this.addData("date","2015 3/26");
        HashMap<String,String> data_3 = (HashMap<String,String>)this.addData("date","2015 4/25");
        HashMap<String,String> data_4 = (HashMap<String,String>)this.addData("date","2015 5/25");
        HashMap<String,String> data_5 = (HashMap<String,String>)this.addData("date","2015 6/25");
        this.history_info.add(data_1);
        this.history_info.add(data_2);
        this.history_info.add(data_3);
        this.history_info.add(data_4);
        this.history_info.add(data_5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history_info, menu);
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

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            setTitle(this.getPageTitle(position));
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return history_info.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
//            switch (position) {
//                case 0:
//                    return ""
//                case 1:
//                    return getString(R.string.title_section2).toUpperCase(l);
//                case 2:
//                    return getString(R.string.title_section3).toUpperCase(l);
//            }
            return history_info.get(position).get("date");
//            return null;
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

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         *
         */
        ListView health_info_ll;
        HealthValueListVIewAdapter adapter;

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_health_info, container, false);
            health_info_ll = (ListView)rootView.findViewById(R.id.health_info_list);

            adapter = new HealthValueListVIewAdapter(getActivity().getApplicationContext());
            health_info_ll.setAdapter(adapter);
            return rootView;
        }
    }

    public void chartModeEvent(View v){
        Intent i  = new Intent(context,ChartActivity.class);
        startActivity(i);
    }

}

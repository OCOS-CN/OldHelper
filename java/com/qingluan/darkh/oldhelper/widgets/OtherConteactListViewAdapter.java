package com.qingluan.darkh.oldhelper.widgets;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.qingluan.darkh.oldhelper.Activities.ItemDetailFragment;
import com.qingluan.darkh.oldhelper.Activities.ItemListActivity;
import com.qingluan.darkh.oldhelper.R;
import com.qingluan.darkh.oldhelper.util.Kits;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by darkh on 4/18/15.
 */
public class OtherConteactListViewAdapter  extends BaseAdapter{
    Map<String,Integer> data = new HashMap<String, Integer>();
    List<String> data_array = new ArrayList<String>();

    Context external_context;
    Context baseContext;
    TextView tv_item_resolution;

    String [] keys;
    onAlertListViewClickListener listener ;
    public OtherConteactListViewAdapter (ItemDetailFragment context){
        external_context = context.getActivity().getApplicationContext();
        data_array.add("18812345678");
        data_array.add("2234354");
        data.put("18812345678",1);
        data.put("2234354",2);
        keys = data.keySet().toArray(new String[]{});
    }

    public void setBaseContext(Context context){
        baseContext = context;
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
        convertView = inflater.inflate(R.layout.widget_contact_click_ll,null);

        tv_item_resolution = (TextView) convertView.findViewById(R.id.other_contact_item_tv);
        tv_item_resolution.setText(keys[position]);
        tv_item_resolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(external_context,keys[position],Toast.LENGTH_SHORT).show();

                AlertDialog alertDialog = new AlertDialog.Builder(baseContext)
                        .setTitle("功能界面")
                        .setMessage(tv_item_resolution.getText().toString()+"\t"+"call ?")
                        .setPositiveButton("call", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Kits.callPhone(baseContext,tv_item_resolution.getText().toString().trim());
                            }
                        })
                        .show();
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

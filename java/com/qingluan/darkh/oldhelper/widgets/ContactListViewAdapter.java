package com.qingluan.darkh.oldhelper.widgets;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qingluan.darkh.oldhelper.R;
import com.qingluan.darkh.oldhelper.util.Kits;
import com.qingluan.darkh.oldhelper.util.ToastShow;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by darkh on 4/24/15.
 */
public class ContactListViewAdapter extends BaseAdapter {
    Map<String,String> data = new HashMap<String, String>();
    List<String> data_array = new ArrayList<String>();

    Context external_context;
    EditText phonenumber;
    Button call_bt;
    ImageView head_iv;
    TextView label;
    String [] keys;
    onAlertListViewClickListener listener ;
    public ContactListViewAdapter (Context context){
        external_context = context;
        data.put("1872314","服务台");
        data.put("120","急救");
        data.put("125124512","亲属");
        data.put("112321","其他");
        data.put("12421421","其他");
        keys = data.keySet().toArray(new String[]{});
    }


    public void setOnAlertListViewClickListener(onAlertListViewClickListener onAlertListViewClickListener){
        this.listener = onAlertListViewClickListener;
    }


    public String getData(String key){
        return  data.get(key);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(keys[position]);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater) external_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.widget_phonenumber,null);

        head_iv = (ImageView)convertView.findViewById(R.id.head_iv);
        phonenumber = (EditText) convertView.findViewById(R.id.call_et);
        call_bt = (Button) convertView.findViewById(R.id.call_bt);
        label = (TextView)convertView.findViewById(R.id.label_tv);
        String label_str = getItem(position);
//        ToastShow toastShow = new ToastShow( )
        Log.d(ContactListViewAdapter.class.getName(),label_str);
        label.setText(label_str);
        phonenumber.setText(keys[position]);
        phonenumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.d(ContactListViewAdapter.class.getName(),"press .");
                return false;
            }
        });
        call_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(external_context, keys[position], Toast.LENGTH_SHORT).show();
                Kits.callPhone(external_context,phonenumber.getText().toString());
            }
        });

        return convertView;
    }

    public interface onAlertListViewClickListener{
        public void afterClick(String [] exclusionKeys);
    }
}

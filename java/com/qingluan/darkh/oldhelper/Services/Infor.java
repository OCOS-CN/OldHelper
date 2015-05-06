package com.qingluan.darkh.oldhelper.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

/**
 * Created by darkh on 4/28/15.
 */
public class Infor {
    RecivedBroadcastReceiver broadReciver;
    IntentFilter intentFilter;
    Context context;
    public  Infor(Context context,String action,RecivedBroadcastReceiver.ReceivedListener listener){
        this.context = context;
        broadReciver = new RecivedBroadcastReceiver();
        broadReciver.setReceivedListener(listener);
        intentFilter = new IntentFilter();
        intentFilter.addAction(action);
        context.registerReceiver(broadReciver,intentFilter);

    }

    public BroadcastReceiver getReciver(){
        return  broadReciver;
    }

}

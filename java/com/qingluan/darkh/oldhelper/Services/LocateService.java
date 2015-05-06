package com.qingluan.darkh.oldhelper.Services;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.qingluan.darkh.oldhelper.arguments.ARGUMENTS;
import com.qingluan.darkh.oldhelper.network.NetworkHandler;

/**
 * Created by darkh on 4/28/15.
 */
public class LocateService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    private String Name  ;
    private BroadcastNotifer notifer;
    private Context context;
    private Context external_context;


    public LocateService(){
        super("LocateService");
    }

    public LocateService(String name,Context context) {
        super(name);
        Name = name;
        context = this;
        external_context = context;
    }

    public void init(){
        notifer = new BroadcastNotifer(this);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    public void  http() throws InterruptedException {
        Log.d("some","htis start");
        notifer = new BroadcastNotifer(getApplicationContext());
        Thread newT = new Thread(new Runnable() {
            @Override
            public void run() {

                    NetworkHandler.JsonHttp(ARGUMENTS.URL_LOCATE_POST, new NetworkHandler.afterPostListener() {


                        @Override
                        public void after(String json_data) {
//                            Toast.makeText(context, json_data, Toast.LENGTH_LONG).show();
                            notifer.sendIntent(ARGUMENTS.AFTER_GET_LOCATE_INFO_ACTION,json_data);
                        }
                    });

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

            }
        });
        newT.start();;
        Log.d("some","htis end");



    }

    @Override
    protected void onHandleIntent(Intent intent) {
        while (true){


            try {
                http();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

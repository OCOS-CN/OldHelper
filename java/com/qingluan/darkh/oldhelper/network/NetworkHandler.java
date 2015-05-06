package com.qingluan.darkh.oldhelper.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qingluan.darkh.oldhelper.Services.BroadcastNotifer;
import com.qingluan.darkh.oldhelper.arguments.ARGUMENTS;
import com.qingluan.darkh.oldhelper.database.FamilyDbHelper;
import com.qingluan.darkh.oldhelper.database.LoginDataBase;
import com.qingluan.darkh.oldhelper.util.FileTools;
import com.qingluan.darkh.oldhelper.util.JsonTools;
import com.qingluan.darkh.oldhelper.util.ProgressDialogTool;
import com.qingluan.darkh.oldhelper.util.ToastShow;
import com.qingluan.darkh.oldhelper.widgets.FamilyChooseListViewAdapter;

import org.apache.http.Header;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

/**
 * Created by darkh on 11/6/14.
 */
public class NetworkHandler {

    public String tag = NetworkHandler.class.getName();
    private WebSocketConnection connect_client;
    private Context context;
    private NetworkInteract interact_handler;
    private AsyncHttpClient httpClient;
    private AsyncHttpListener listener;
    BroadcastNotifer notifer;
    /*
        this is websocket
     */

    public NetworkHandler(final Context context,String url,String key ,String json_data,final String filename){
        httpClient  = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put(key,json_data);
        Log.d(tag, "download " + url);
        httpClient.post(url,params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                FileTools fileTools = new FileTools(context);
                fileTools.saveFile(responseBody,filename);
                listener.afterSave(ARGUMENTS.FILE_ROOT_PATH+"/"+filename);
            }


        });

    }

    public NetworkHandler(final Context context,String url,final String filename){
        httpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        httpClient.get("http://"+url,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d(tag, "download ok ..");
                FileTools fileTools = new FileTools(context);
                fileTools.saveFile(responseBody,filename);
                listener.afterSave(ARGUMENTS.FILE_ROOT_PATH+"/"+filename);
            }

            @Override
            public void onProgress(int bytesWritten, int totalSize) {
                Log.d(tag, "now " + String.valueOf(bytesWritten) + " total : " + String.valueOf(totalSize) + "\r");

            }

            @Override
            public void onFailure(Throwable error, String content) {
                Log.d(tag, content);
            }
        });
    }
    public NetworkHandler(final  Context context){
        this.context = context;
    }

    public boolean httpSend(String name_id,String url,String info){
        notifer = new BroadcastNotifer(context);
        httpClient = new AsyncHttpClient();
        httpClient.setTimeout(4);
        RequestParams params = new RequestParams();
        LoginDataBase f_db = new LoginDataBase(context);
        String version_id = f_db.search(name_id,"name_id","version_id");

        params.put("id",name_id);
        params.put("content",info);
        params.put("version_id",version_id);
        Log.d(tag, "sending .......|"+name_id+" | " + version_id);

        httpClient.post(url,params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, String content) {
                notifer.sendIntent(ARGUMENTS.AFTER_GET_FAMILY_INFO_ACTION,content);
                Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable error) {
                Toast.makeText(context,error.toString(),Toast.LENGTH_SHORT).show();
                ProgressDialogTool.dis();
            }
        });
        return  true;

    }

    public static void JsonHttp(String url, final afterPostListener afterPostListener){
        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("access_token",ARGUMENTS.TOKEN_LOCATE);
        params.put("action","monitor");
        params.put("imei","354188047789214");

        httpClient.post(url,params, new AsyncHttpResponseHandler(){
            @Override
            public void onProgress(int bytesWritten, int totalSize) {
                super.onProgress(bytesWritten, totalSize);
                Log.d("http","sending...");
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                Log.d("http", statusCode + error.toString());
            }

            @Override
            public void onSuccess(String content) {
                Log.d(NetworkHandler.class.getName(),content);
                afterPostListener.after(content);
            }
        });

    }

    public static void JsonHttp(String url, String json_data_key,String json_data,final afterPostListener afterPostListener){
        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("access_token",ARGUMENTS.TOKEN_LOCATE);
        params.put("action","monitor");
        params.put("imei","354188047789214");

        httpClient.post(url,params, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
                afterPostListener.after(content);
            }
        });

    }

    public boolean send(String info){
        if (this.connect_client.isConnected()){

            Log.d(tag, "sending .......");
            this.connect_client.sendTextMessage(info);
            return  true;

        }
        Log.d(tag, "can not sned ");
        return  false;
    }

    public boolean closeConnect(){
        if(this.connect_client != null){
            this.connect_client.disconnect();
            return true;
        }
        return false;
    }

    public void setAsyncHttpHandler(AsyncHttpListener asyncHttpListener){
        this.listener = asyncHttpListener;
    }

    public interface AsyncHttpListener{
        public void afterSave(String file_path);
    }

    public interface afterPostListener{
        public void after(String json_data);
    }

    public boolean isConnected(){
        return this.connect_client.isConnected();
    }
}

package com.qingluan.darkh.oldhelper.network;

import android.content.Context;
import android.util.Log;

import com.qingluan.darkh.oldhelper.arguments.ARGUMENTS;
import com.qingluan.darkh.oldhelper.database.FamilyDbHelper;
import com.qingluan.darkh.oldhelper.database.LoginDataBase;
import com.qingluan.darkh.oldhelper.util.JsonTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by darkh on 11/9/14.
 */
public class NetworkInteract {
    String tag = NetworkHandler.class.getName();
    private JsonTools jsonTools;
    private Context context;

    public NetworkInteract(Context context){
        jsonTools = new JsonTools();
        this.context = context;
    }

    private String BASE_REQUEST(String name_id,Collection<?> content){
        LoginDataBase l_db = new LoginDataBase(context);
        String version_id = l_db.search(name_id,"name_id","version_id");
        Log.d("eoor",version_id+"|"+name_id);

//        jsonTools.addData(ARGUMENTS.CONTETN,content);
//        JSONObject obj = jsonTools.getJsonObj();
        JSONArray content_json = new JSONArray(content);

        return content_json.toString();



//        return  jsonTools.toString();
    }

    public String GET_FAMILY_INFO(String name_id){

        List<String> keys = new ArrayList<String>();

        keys.add("name");
        keys.add("img_path");
        keys.add("corelate");
        keys.add("contact");
        keys.add("other_contact");

        String res =   BASE_REQUEST(name_id,keys);
        Log.d("JSonPostAData",res);
        return  res;


    }

}

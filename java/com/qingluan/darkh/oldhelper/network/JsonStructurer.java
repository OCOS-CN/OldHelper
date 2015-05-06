package com.qingluan.darkh.oldhelper.network;

import com.qingluan.darkh.oldhelper.arguments.ARGUMENTS;
import com.qingluan.darkh.oldhelper.util.JsonTools;

/**
 * Created by darkh on 4/28/15.
 */
public class JsonStructurer {
    public static String JSON_Locate(){
        JsonTools jsonTools = new JsonTools();
        jsonTools.addData("access_token", ARGUMENTS.TOKEN_LOCATE);
        jsonTools.addData("action","monitor");
        jsonTools.addData("imei","354188047789214");

        return  jsonTools.toString();
    }
}

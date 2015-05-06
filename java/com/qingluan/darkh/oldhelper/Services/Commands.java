package com.qingluan.darkh.oldhelper.Services;

import android.os.Bundle;

import com.qingluan.darkh.oldhelper.arguments.ARGUMENTS;

/**
 * Created by darkh on 4/21/15.
 */
public class Commands {

    public static Bundle BaseIntentBundle(int Signal,String name_id){
        Bundle bundle = new Bundle();
        bundle.putInt(ARGUMENTS.SIGNAL_KEY,Signal);
        bundle.putString(ARGUMENTS.NAME_ID,name_id);
        return  bundle;
    }

    public static Bundle get_family_command(String name_id){
        Bundle bundle = Commands.BaseIntentBundle(TalkService.GET_FAMILI_INFO,name_id);
        bundle.putString(ARGUMENTS.URL_KEY,ARGUMENTS.URL_FAMILY_POST);
        return  bundle;
    }

}

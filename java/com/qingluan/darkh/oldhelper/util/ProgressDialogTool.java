package com.qingluan.darkh.oldhelper.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by darkh on 4/20/15.
 */
public class ProgressDialogTool {
    public static ProgressDialog  dialog;
    public static void  show(Context context ,String title,String content){
        ProgressDialogTool.dialog  = ProgressDialog.show(context,title,content);
    }
    public static void dis(){
        if (ProgressDialogTool.dialog != null){
            ProgressDialogTool.dialog.dismiss();
            ProgressDialogTool.dialog = null;
        }
    }
}

package com.qingluan.darkh.oldhelper.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

import com.qingluan.darkh.oldhelper.R;
import com.qingluan.darkh.oldhelper.Services.BroadcastNotifer;
import com.qingluan.darkh.oldhelper.arguments.ARGUMENTS;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by darkh on 11/9/14.
 */
public class FileTools {
    private String filename;
    private BroadcastNotifer notifer;
    public String root_path;
    private Context context;
    public FileTools(Context context,String file_name){
        this.filename = file_name;
        this.context = context;
        notifer = new BroadcastNotifer(context);
        //get sd path
        root_path  = ARGUMENTS.FILE_ROOT_PATH;
    }



    private boolean CheckSDCard(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            notifer.sendIntent(ARGUMENTS.INFO_ACTION,"SD is ok");
            File file = new File(root_path);
            if (file.exists()){
                return true;
            }else{
                notifer.sendIntent(ARGUMENTS.INFO_ACTION,"no such directory ,creating ...");
                if (file.mkdirs()){
                    notifer.sendIntent(ARGUMENTS.INFO_ACTION,"file directory is ok");
                    return true;
                }else{
                    notifer.sendIntent(ARGUMENTS.INFO_ACTION,"Creating file is failure");
                    return false;
                }


            }


        }else{
            notifer.sendIntent(ARGUMENTS.INFO_ACTION,"no sd card found");
            return false;
        }
    }

    public FileTools(Context context){
        notifer = new BroadcastNotifer(context);
        this.context = context;
        //get sd  path
        root_path  = ARGUMENTS.FILE_ROOT_PATH;
    }
    public void saveFile(byte[] bytes ,String filename){
        this.filename = filename;
        if (CheckSDCard()){
            File file = new File(root_path+"/"+this.filename);
            DataOutputStream dopt;
            notifer.sendIntent(ARGUMENTS.INFO_ACTION,"Starting downloading  "+filename);
            try{
                dopt = new DataOutputStream(new FileOutputStream(file));
                dopt.write(bytes);
                notifer.sendIntent(ARGUMENTS.INFO_ACTION,"file "+ filename+"downloaded");
            }catch (IOException e ){
                e.printStackTrace();
            }
        }
    }

    public void fromRawToSD(int resourceID,String name){
        InputStream is=context.getResources().openRawResource(resourceID);
        try {
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            this.saveFile(bytes,name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] listFiles(){
        if(CheckSDCard()){
            File file = new File(this.root_path);
            return file.list();
        }
        return null;
    }


    public static Drawable getImageByName (Context context,String directoryName,String name ){
        Drawable da = null;
        AssetManager assetManager = context.getAssets();

        try {
            InputStream is = assetManager.open(directoryName + "/"+name);

//            .Options options = new BitmapFactory.Options();
//            options.inSampleSize = 2;
//            da = Drawable.decodeStream(is, null, options);
            da = Drawable.createFromStream(is,null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  da;
    }

    public static  Bitmap getImageBitmapByName (Context context,String directoryName,String name ){
        Bitmap da = null;
        AssetManager assetManager = context.getAssets();

        try {
            InputStream is = assetManager.open(directoryName + "/"+name);




            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = 2;
//            BitmapFactory.decodeResource(getResources(), R.id.myimage, options);
//            int imageHeight = options.outHeight;
//            int imageWidth = options.outWidth;
//            String imageType = options.outMimeType;
            da= BitmapFactory.decodeStream(is, null, options);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  da;
    }

}

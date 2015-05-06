package com.qingluan.darkh.oldhelper.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by darkh on 4/20/15.
 */
public class Kits {

    public static String Now(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate   =   new Date(System.currentTimeMillis());
        return  dateFormat.format(curDate);

    }

    public static String TurnTime(long timeMillis){
        Date date = new Date(timeMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return dateFormat.format(date);

    }

    public static String md5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);

        for (byte b : hash) {
            int i = (b & 0xFF);
            if (i < 0x10) hex.append('0');
            hex.append(Integer.toHexString(i));
        }

        return hex.toString();
    }

    public static  void simpleTurnActivity(Context fromactivity , Class<?> toActivity){
        Intent i = new Intent(fromactivity,toActivity);
        fromactivity.startActivity(i);
    }

    /**
     *
     * @param fromactivity
     * @param toActivity
     * @param options this is a map options ,odd is key , even is value
     */
    public static  void simpleTurnActivity(Context fromactivity , Class<?> toActivity ,String ... options){
        if (options .length %2 != 0){
            Exception e = new Exception("options's length must be even ");
            try {
                throw  e;
            } catch (Exception e1) {
                return;
            }
        }
        Intent i = new Intent(fromactivity,toActivity);
        Bundle bundle = new Bundle();
        String key = null;
        String val= null;
        for(int index =0 ; index < options.length ; index++){

            if (index %2 == 0){
                // key
                key = options[index];

            }else{
                val = options[index];
                bundle.putString(key,val);
            }
        }
        i.putExtras(bundle);
        fromactivity.startActivity(i);
    }

    public  static void callPhone(Context context,String number){
        number = number.trim();
        Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+number)) ;
//        i.putExtra(Intent.EXTRA_PHONE_NUMBER,number);
//        Intent.ACTION_NEW_OUTGOING_CALL
        context.startActivity(i);

    }
}

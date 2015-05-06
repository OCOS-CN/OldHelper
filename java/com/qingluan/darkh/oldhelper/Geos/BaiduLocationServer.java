package com.qingluan.darkh.oldhelper.Geos;


import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.BDNotifyListener;//假如用到位置提醒功能，需要import该类
//如果使用地理围栏功能，需要import如下类
import com.baidu.location.BDGeofence;
import com.baidu.location.BDLocationStatusCodes;
import com.baidu.location.GeofenceClient;
import com.baidu.location.GeofenceClient.OnAddBDGeofencesResultListener;
import com.baidu.location.GeofenceClient.OnGeofenceTriggerListener;
import com.baidu.location.GeofenceClient.OnRemoveBDGeofencesResultListener;
import com.baidu.location.LocationClientOption.LocationMode;

public class BaiduLocationServer {
	
	//Baidu API argu area
	public LocationClient qLocationClient = null;
	
	
	//context argu area
	private Context context ;
	//interface init
	private static String tag = BaiduLocationServer.class.getName(); 
	
	// on or off 
	private int ON = 1;
	private int OFF = -1;
	private int TAG = ON;
	public static int turn = 0;
	public BaiduLocationServer (Context context){
		this.context = context;
		qLocationClient = new LocationClient(this.context);
		
		
		
	}
	
	public void OFF(){
		if (qLocationClient.isStarted()){
			qLocationClient.stop();
		}
	}
	
	public void OFF(int max_turn){
		Log.d(tag, "turn :"+String.valueOf(turn));
		if (qLocationClient.isStarted()){
			if (BaiduLocationServer.turn >= max_turn){
				qLocationClient.stop();
				BaiduLocationServer.turn = 0;
			}
		}
		
	}
	/**
	 * BDLocationListener is a interface ... so ,you have to 
	 * implement 
	 * 
	 */
	public void RunLocationServer(final BDLocationListener bDLocationListener ){
		
		//register listener.
		qLocationClient.registerLocationListener(bDLocationListener); 
		
		//location option setting 
		LocationClientOption Option = new LocationClientOption();
		Option.setLocationMode(LocationMode.Hight_Accuracy);
		Option.setCoorType("bd09ll");//返回的定位结果是百度经纬度，默认值gcj02
		Option.setScanSpan(10000);
		
		Option.setIsNeedAddress(true);
		qLocationClient.setLocOption(Option);
		
		if (!qLocationClient.isStarted()){
			
			qLocationClient.start();
		}
		
		if (qLocationClient != null && qLocationClient.isStarted()){
			  qLocationClient.requestLocation();
		}else{ 
			 Toast.makeText(this.context, "locClient is null or not started"
					 						,Toast.LENGTH_SHORT).show();
		}
		
		
	}
		
	public void StopLocation(){
		if (qLocationClient.isStarted()){
			
			
			qLocationClient.stop();
		}
		if (!qLocationClient.isStarted()){
			Log.d(tag, "stop location");
		}
	}
	

}

package com.qingluan.darkh.oldhelper.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Projection;
import com.baidu.mapapi.model.LatLng;
import com.qingluan.darkh.oldhelper.Geos.BaiduLocationServer;
import com.qingluan.darkh.oldhelper.R;
import com.qingluan.darkh.oldhelper.Services.BroadReciver;
import com.qingluan.darkh.oldhelper.Services.Infor;
import com.qingluan.darkh.oldhelper.Services.LocateService;
import com.qingluan.darkh.oldhelper.Services.RecivedBroadcastReceiver;
import com.qingluan.darkh.oldhelper.arguments.ARGUMENTS;
import com.qingluan.darkh.oldhelper.util.JsonTools;

import java.util.ResourceBundle;

public class MapActivity extends Activity {

    MapView map ;
    BaiduLocationServer locationServer;
    Bitmap mark_bitmap ;
    BitmapDescriptor bitmap_description ;
    BaiduMap baiduMap;
    private Context context;
    private BDLocation old_location;
    private BroadReciver broadReciver;
    private IntentFilter intentFilter;
    private Infor infor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        context = this;
        locationServer = new BaiduLocationServer(context);

        init_map();
        Intent i = new Intent(context, LocateService.class);
        startService(i);
        init_BR();


    }

    private void  init_BR(){
        infor = new Infor(context,ARGUMENTS.AFTER_GET_LOCATE_INFO_ACTION,new RecivedBroadcastReceiver.ReceivedListener() {
            @Override
            public void recieved(String info) {
                JsonTools jsonTools = new JsonTools(info);
                Double lat = Double.valueOf( jsonTools.getString("lat"));
                Double lng = Double.valueOf(jsonTools.getString("lng"));
                BDLocation location = new BDLocation();
                location.setLatitude(lat);
                location.setLongitude(lng);




                if (old_location == null){
                    old_location = location;
                    drawMark(location);
                }else {

                    if (lng + lat> 0.000005){
                        drawMark(location);
                    }

                    old_location = location;
                }
            }
        });

//        broadReciver = new BroadReciver(context,new BroadReciver.BroadcastReceiverListener() {
//            @Override
//            public void onString(String data) {
//
//            }
//        });
//        intentFilter.addAction(ARGUMENTS.AFTER_GET_LOCATE_INFO_ACTION);
//        context.registerReceiver(broadReciver,intentFilter);

    }

    private void init_map(){
        map = (MapView)findViewById(R.id.map_display);
        baiduMap = map.getMap();

        mark_bitmap =  Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.mark),60,60,true);
        bitmap_description = BitmapDescriptorFactory.fromBitmap(mark_bitmap);

        locationServer.RunLocationServer(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (old_location == null){
                    old_location = bdLocation;
                    drawMark(bdLocation);
                }else {
                    Double lat_sub = bdLocation.getLatitude() - old_location.getLatitude();
                    Double lon_sub = bdLocation.getLongitude() - old_location.getLongitude();
                    if (lon_sub + lat_sub > 0.000005){
                        drawMark(bdLocation);
                    }
                }

            }
        });




    }

    private  void  drawMark(BDLocation bdLocation){
        LatLng point = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap_description);
        baiduMap.clear();
        baiduMap.addOverlay(option);
        Projection p =  baiduMap.getProjection();
//        map.setZoomControlsPosition();

        setCenter(point);
    }

    public void setCenter(LatLng point){
        MapStatus new_status =new MapStatus.Builder()
                .target(point)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(new_status);
        baiduMap.setMapStatus(mMapStatusUpdate);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.onDestroy();
        unregisterReceiver(infor.getReciver());
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

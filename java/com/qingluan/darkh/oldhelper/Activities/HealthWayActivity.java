package com.qingluan.darkh.oldhelper.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.MediaController;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.VideoView;

import com.qingluan.darkh.oldhelper.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class HealthWayActivity extends Activity {
    private VideoView video_play_v;
    MediaPlayer mediaPlayer;
    MediaController mediaco;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_way);
        context = this;
        init_UI();
    }


    private void init_UI(){
        video_play_v = (VideoView)findViewById(R.id.video_play_v);
//        video_play_v.setVideoURI(Uri.parse("android.resource://com.telecom.activities/"+R.raw.health_info));
//        MediaController mediaController = new MediaController(this);
//        video_play_v.setMediaController(mediaController);
//        video_play_v.start();

//        video_play_v.setVideoURI(Uri.parse());
//        mediaPlayer = MediaPlayer.create(this, R.drawable.health_info);
//        mediaPlayer.start();

//        video_play_v.setVideoPath();

        video_play_v.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.health));
        video_play_v.start();
        //播放结束侦听
        video_play_v.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                video_play_v.setVisibility(View.INVISIBLE);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_health_way, menu);
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
